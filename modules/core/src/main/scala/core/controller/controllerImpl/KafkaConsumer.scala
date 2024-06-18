package core.controller.controllerImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.*
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.*

import scala.io.StdIn
import scala.concurrent.ExecutionContext
import akka.http.scaladsl.server.Route

import scala.util.{Failure, Success}
import play.api.libs.json.Json
import akka.stream.UniformFanInShape
import akka.stream.UniformFanOutShape
import akka.stream.scaladsl.Broadcast
import akka.stream.scaladsl.GraphDSL
import akka.stream.scaladsl.Merge
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.FlowShape
import akka.stream.scaladsl.GraphDSL.Builder
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, Sink, Source}
import akka.stream.{FlowShape, Materializer, UniformFanInShape, UniformFanOutShape}
import akka.NotUsed

import scala.util.*
import scala.collection.immutable.LazyList.cons
import akka.kafka.ConsumerSettings
import org.apache.kafka.common.serialization.StringDeserializer
import akka.kafka.scaladsl.Consumer
import akka.kafka.Subscriptions
import akka.kafka.ConsumerMessage.CommittableMessage
import akka.kafka.ConsumerMessage.CommittableOffset
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import akka.kafka.ProducerSettings
import org.apache.kafka.common.serialization.StringSerializer
import akka.kafka.scaladsl.Producer
import core.controller.ControllerInterface
import core.util.{GameState, Observer}
import core.util.GameState.*

class KafkaConsumer(controller: ControllerInterface) {


  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "Testing" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val consumerSettings =
    ConsumerSettings( system, new StringDeserializer, new StringDeserializer )
      .withBootstrapServers( "localhost:9092" )
      .withGroupId( "group1" )

  val source = Consumer.plainSource(
    consumerSettings,
    Subscriptions.topics( "battleship-tui" )
  )

  val producerSettings =
    ProducerSettings( system, new StringSerializer, new StringSerializer )
      .withBootstrapServers( "localhost:9092" )

  val flow = Flow[ConsumerRecord[String, String]].map { record =>
    val command = Json.parse( record.value() )

    val msg: String = ( command \ "msg" ).as[String]
    var answer: String = ""

    msg match {
      case "quit" =>
        System.exit(0)
      case "save" =>
        controller.saveGame()
      case "new" =>
        controller.resetGame()
      case "load" =>
        controller.loadGame()
      case "set" =>
        val input = ( command \ "data" ).as[String]
        controller.gameState match
          case PLAYER_CREATE1 | PLAYER_CREATE2 => addPlayer(input)
          case SHIP_PLAYER1 | SHIP_PLAYER2 =>
            input match
              case "auto" => controller.autoShips()
              case "undo" => controller.undo()
              case "redo" => controller.redo()
              case _ =>
                val cords = input.split(" ")
                if (cords.length == 2) {
                  val str1 = cords(0)
                  val str2 = cords(1)
                  answer = addShipInput(str1, str2)
                } else {
                  answer = "Format error! Format example: <a5 a7>"
                }
          case SHOTS =>
            val shot = addShotInput(input)
            if (shot._1 == 0) {
              answer = shot._2
              answer = answer + controller.toString
              if (controller.isLost) {
                controller.gameState = END
              }
              if (!controller.state.grid.ships.isHit(controller.state.grid.shots.getLatestX.getOrElse(0), controller.state.grid.shots.getLatestY.getOrElse(0))) {
                controller.changeState()
              } else answer = answer + "\n\nHit! You can fire again."
            }
          case END =>
            answer = "end"
      case _ =>
        answer = "Error: No input detected."
    }
    answer = answer + "\n" + controller.GameStateText
    Json.obj( "msg" -> "success", "data" -> answer )
  }

  val kafkaSink = Producer.plainSink( producerSettings )

  def start(): Unit = {
    println( "Kafka service started." )
    println(controller.GameStateText)

    source
      .via( flow )
      .map( result =>
        new ProducerRecord[String, String](
          "tui-response-topic",
          Json.stringify( result )
        )
      )
      .runWith( kafkaSink )
  }

  private def addPlayer(input: String): Unit = {
    controller.gameState match
      case PLAYER_CREATE1 =>
        controller.state = controller.player1
        controller.setPlayerName(input)
        controller.state = controller.player2
        controller.gameState = PLAYER_CREATE2
      case PLAYER_CREATE2 =>
        controller.state = controller.player2
        controller.setPlayerName(input)
        controller.state = controller.player1
        controller.gameState = SHIP_PLAYER1
  }

  private def addShipInput(start: String, ende: String): String = {
    val e = Try(
      if (!controller.isValid(start) || !controller.isValid(ende)) {
        "Invalid ship input"
      } else if (!controller.checkShip(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))) {
        "invalid Ship Position"
      } else {
        controller.set(controller.getX(start), controller.getY(start), controller.getX(ende), controller.getY(ende))
        //GameState
        if (!controller.state.grid.ships.shipCountValid()) {
          controller.gameState match
            case SHIP_PLAYER1 =>
              controller.state = controller.player2
              controller.gameState = SHIP_PLAYER2
            case SHIP_PLAYER2 =>
              controller.state = controller.player1
              controller.gameState = SHOTS
        }
      }
    )
    if (e.isFailure) "invalid"
    else {
      if (!controller.state.grid.ships.shipPosition()) {
        controller.undo()
        return "You already place a ship at this position!"
      }
      if (!controller.state.grid.ships.shipSingleCountValid()) {
        controller.undo()
        return "Too many ships with this size"
      }
      "Ship implemented"
    }
  }

  private def checkFired(input: String): Boolean = controller.alreadyFired(controller.getX(input), controller.getY(input))


  private def addShotInput(input: String): (Int, String) = {
    if (!controller.isValid(input)) {
      val ans = "Wrong input: " + input + "\nFormat example: <h6>"
      (1, ans)
    } else {
      val check = checkFired(input)
      if (check) {
        val ans = "You already fired there!"
        (1, ans)
      } else {
        controller.addShot(controller.getX(input), controller.getY(input))
        (0, "")
      }
    }
  }

}
