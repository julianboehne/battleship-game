import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

lazy val root = project
  .in(file("."))
  .dependsOn(core, gui, persistency, tui)
  .settings(
    name := "battleship-game",
    importSettings,
    jacocoSettings
  )
  .aggregate(core, gui, persistency, tui)
  .enablePlugins(JacocoCoverallsPlugin)


lazy val core = project
  .in(file("./modules/core"))
  .dependsOn(persistency)
  .settings(
    name := "core",
    importSettings,
    jacocoSettings
  )

lazy val gui = project
  .in(file("./modules/gui"))
  .dependsOn(tui, core)
  .settings(
    name := "gui",
    importSettings,
    jacocoSettings
  )


lazy val persistency = project
  .in(file("./modules/persistency"))
  .settings(
    name := "persistency",
    importSettings,
    jacocoSettings
  )

lazy val tui = project
  .in(file("./modules/tui"))
  .dependsOn(core)
  .settings(
    name := "tui",
    importSettings,
    jacocoSettings
  )


lazy val importSettings: Seq[Def.Setting[?]] = Seq(
  libraryDependencies ++= Seq(
    "org.scalactic" %% "scalactic" % "3.2.14",
    "org.scalatest" %% "scalatest" % "3.2.14" % "test",
    "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    "com.google.inject" % "guice" % "5.1.0",
    ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13),
    "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
    ("com.typesafe.play" %% "play-json" % "2.10.0-RC5"),
    "com.typesafe.akka" %% "akka-http" % "10.5.3",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
    "com.typesafe.akka" %% "akka-http-core" % "10.5.3",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.8.5",
    "com.typesafe.akka" %% "akka-stream" % "2.8.5",
    "com.typesafe.akka" %% "akka-actor" % "2.8.5",
    "com.typesafe.akka" %% "akka-stream-kafka" % "4.0.2",
    "org.apache.logging.log4j" %% "log4j-api-scala" % "13.1.0",
    ("com.typesafe.slick" %% "slick" % "3.5.0-M3").cross(CrossVersion.for3Use2_13),
    "org.postgresql" % "postgresql" % "42.7.3",
    "org.mongodb.scala" %% "mongo-scala-driver" % "4.8.0" cross CrossVersion.for3Use2_13

  )
)

lazy val jacocoSettings = Seq(

  jacocoExcludes := Seq(
    "modules/persistency*",
    "modules/gui*",
    "modules/core/src/main/scala/core/controller/ControllerAPI.scala",
    "modules/core/src/main/scala/core/controller/controllerImpl/KafkaConsumer.scala"
  ),
  jacocoReportSettings := JacocoReportSettings(
    "Jacoco Coverage Report",
    None,
    JacocoThresholds(),
    Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML),
    "utf-8")
)




