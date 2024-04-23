import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

lazy val root = project
  .in(file("."))
  .dependsOn(controller, gui, model, persistency, tui, util)
  .settings(
    name := "battleship-game",
    importSettings,
    jacocoSettings
  )
  .aggregate(controller, gui, model, persistency, tui, util)
  .enablePlugins(JacocoCoverallsPlugin)


lazy val controller = project
  .in(file("controller"))
  .dependsOn(model, util, persistency)
  .settings(
    name := "controller",
    importSettings
  )

lazy val gui = project
  .in(file("gui"))
  .dependsOn(tui, controller, util)
  .settings(
    name := "gui",
    importSettings
  )

lazy val model = project
  .in(file("model"))
  .settings(
    name := "model",
    importSettings
  )


lazy val persistency = project
  .in(file("persistency"))
  .dependsOn(util, model)
  .settings(
    name := "persistency",
    importSettings
  )

lazy val tui = project
  .in(file("tui"))
  .dependsOn(controller, util)
  .settings(
    name := "tui",
    importSettings
  )

lazy val util = project
  .in(file("util"))
  .dependsOn(model)
  .settings(
    name := "util",
    importSettings
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
    "ch.qos.logback" % "logback-classic" % "1.5.6" % Runtime
  )
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
    Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML), // note XML formatter
    "utf-8")


)


//coverageEnabled := true





