package core.controller.controllerImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._
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
import akka.stream.{
  FlowShape,
  Materializer,
  UniformFanInShape,
  UniformFanOutShape
}
import akka.NotUsed
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

class KafkaConsumer(controller: ControllerInterface) {


  implicit val system: ActorSystem[?] =
    ActorSystem( Behaviors.empty, "SprayExample" )
  implicit val executionContext: ExecutionContext = system.executionContext

  val consumerSettings =
    ConsumerSettings( system, new StringDeserializer, new StringDeserializer )
      .withBootstrapServers( "localhost:9092" )
      .withGroupId( "group1" )

  val source = Consumer.plainSource(
    consumerSettings,
    Subscriptions.topics( "tui-topic" )
  )

  val producerSettings =
    ProducerSettings( system, new StringSerializer, new StringSerializer )
      .withBootstrapServers( "localhost:9092" )

  val flow = Flow[ConsumerRecord[String, String]].map { record =>
    val input = Json.parse( record.value() )

    val msg: String = ( input \ "msg" ).as[String]

    msg match {
      case "quit" =>
        System.exit(0)
      case "undo" =>
        controller.undo()
      case "redo" =>
        controller.redo()
      case "auto" =>
        controller.autoShips()
      case "save" =>
        controller.saveGame()
      case "load" =>
        controller.loadGame()
//      case "addPokemons" =>
//        controller.doAndPublish(
//          controller.addPokemons,
//          ( input \ "data" ).as[String]
//        )
      case _ =>
        println("Schuss")
    }
    Json.obj( "msg" -> "success", "data" -> "" ) // TODO: json einfügen
  }

  val kafkaSink = Producer.plainSink( producerSettings )

  def start(): Unit = {
    println( "Kafka service started." )

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

}
