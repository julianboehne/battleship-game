package gatling

import io.gatling.core.Predef.{RawFileBody, *}
import io.gatling.http.Predef.*
import concurrent.duration.*

class PersistenceStressItSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://127.0.0.1:8081")
    .inferHtmlResources()
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .contentTypeHeader("multipart/form-data; boundary=--------------------------944947219471705469647858")
    .userAgentHeader("PostmanRuntime/7.39.0")

  private val headers_0 = Map(
    "Content-Type" -> "multipart/form-data; boundary=--------------------------629492938524978549001141",
    "Postman-Token" -> "1dbe5025-023b-498d-bf1d-a4bcf96bb723"
  )

  private val headers_1 = Map(
    "Content-Type" -> "multipart/form-data; boundary=--------------------------110212994201228689264194",
    "Postman-Token" -> "64afab28-aca7-4460-b05e-9f65badfe081"
  )

  private val headers_2 = Map("Postman-Token" -> "b6a48709-41a6-4ab3-9b10-a39469319cb7")

  private val headers_3 = Map(
    "Content-Type" -> "multipart/form-data; boundary=--------------------------527461942360939135685003",
    "Postman-Token" -> "2ab97a22-c5db-4d68-80a6-4343fb9f2455"
  )


  private val scn = scenario("RecordedSimulation")
    .exec(
      http("mongo-save")
        .post("/persistence/save")
        .headers(headers_0)
        .body(RawFileBody("persistence/0000_request.txt"))
        .check(bodyBytes.is(RawFileBody("persistence/0000_response.txt"))),
      pause(2),
      http("mongo-load")
        .post("/persistence/load")
        .headers(headers_1)
        .body(RawFileBody("persistence/0001_request.json"))
        .check(bodyBytes.is(RawFileBody("persistence/0001_response.json"))),
      pause(2),
      http("slick-save")
        .post("/persistence/save")
        .headers(headers_2)
        .body(RawFileBody("persistence/0002_request.txt"))
        .check(bodyBytes.is(RawFileBody("persistence/0002_response.txt"))),
      pause(2),
      http("slick-load")
        .post("/persistence/load")
        .headers(headers_3)
        .body(RawFileBody("persistence/0003_request.json"))
        .check(bodyBytes.is(RawFileBody("persistence/0003_response.json")))
    )

  setUp(scn.inject(stressPeakUsers(800).during(20.seconds))).protocols(httpProtocol)
}
