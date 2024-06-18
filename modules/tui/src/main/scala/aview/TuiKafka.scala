package aview

import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer
import akka.actor.typed.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.OverflowStrategy
import akka.stream.javadsl.Keep
import akka.stream.scaladsl.Flow
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import scala.concurrent.ExecutionContext
import akka.kafka.scaladsl.Producer
import org.apache.kafka.common.serialization.StringSerializer
import akka.kafka.ProducerSettings
import play.api.libs.json.Json
import org.apache.kafka.clients.producer.ProducerRecord
import akka.kafka.ConsumerSettings
import org.apache.kafka.common.serialization.StringDeserializer
import akka.kafka.scaladsl.Consumer
import akka.kafka.Subscriptions
import play.api.libs.json.JsValue


class TuiKafka {

  implicit val system: ActorSystem[?] =
    ActorSystem(Behaviors.empty, "Test")
  implicit val executionContext: ExecutionContext = system.executionContext

  val producerSettings =
    ProducerSettings(system, new StringSerializer, new StringSerializer)
      .withBootstrapServers("localhost:9092")

  val kafkaSink = Producer.plainSink(producerSettings)

  val flow = Flow[String].map { input =>
    if input.isEmpty then Json.obj("msg" -> "Error: No input detected.")
    else if input == "undo" then Json.obj("msg" -> "undo")
    else if input == "redo" then Json.obj("msg" -> "redo")
    else if input == "auto" then Json.obj("msg" -> "auto")
    else if input == "save" then Json.obj("msg" -> "save")
    else if input == "load" then Json.obj("msg" -> "load")
    else if input == "new" then Json.obj("msg" -> "new")
    else if input == "q" then
      println("quiting...")
      Json.obj("msg" -> "quit")
    else
      Json.obj("msg" -> "test")
  }

  def processInputLine(): Unit = {
    //Eingabe
    val input = readLine()
    Source
      .single(input)
      .via(flow)
      .map(result =>
        new ProducerRecord[String, String](
          "tui-topic",
          Json.stringify(result)
        )
      )
      .runWith(kafkaSink)
  }

  val consumerSettings =
    ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")

  val source = Consumer.plainSource(
    consumerSettings,
    Subscriptions.topics("tui-response-topic")
  )

  source.runForeach { record =>
    val input = Json.parse(record.value())

    val msg: String = (input \ "msg").as[String]

    msg match
      case "success" =>
//        game = Game.fromJson((input \ "data").as[JsValue])
//        update(msg)

  }

}
