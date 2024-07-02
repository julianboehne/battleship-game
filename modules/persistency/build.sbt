version := "0.1.0-SNAPSHOT"

scalaVersion := "3.2.0"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.14",
  "org.scalatest" %% "scalatest" % "3.2.14" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
  ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13),
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  ("com.typesafe.play" %% "play-json" % "2.10.0-RC5"),
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "com.typesafe.akka" %% "akka-http-core" % "10.5.3",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
  "com.typesafe.akka" %% "akka-stream" % "2.8.5",
  "com.typesafe.akka" %% "akka-actor" % "2.8.5",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "13.1.0",
  ("com.typesafe.slick" %% "slick" % "3.5.0-M3").cross(CrossVersion.for3Use2_13),
  "org.postgresql" % "postgresql" % "42.7.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.8.0" cross CrossVersion.for3Use2_13

)

lazy val jacocoSettings = Seq(

  jacocoExcludes := Seq(
    "de.htwg.se.battleship.Battleship*",
    "de.htwg.se.battleship.util.Observable*",
    "de.htwg.se.battleship.controller.controllerImpl.SetCommand*",
    "de.htwg.se.battleship.aview.gui*",
    "de.htwg.se.battleship.model.fileIOImpl*",
    "de.htwg.se.battleship.controller.controllerImpl.Controller*",
    "de.htwg.se.battleship.aview.TUI*",

  ),
  jacocoReportSettings := JacocoReportSettings(
    "Jacoco Coverage Report",
    None,
    JacocoThresholds(),
    Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML),
    "utf-8")


)