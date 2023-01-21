import com.github.sbt.jacoco.JacocoPlugin.autoImport.jacocoExcludes
import sbt.Keys.libraryDependencies

import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

lazy val root = (project in file("."))
  .settings(
    name := "battleship-game",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += "com.google.inject" % "guice" % "5.1.0",
    libraryDependencies += ("net.codingwell" %% "scala-guice" % "5.0.2").cross(CrossVersion.for3Use2_13),
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
    libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.10.0-RC5"),





  )


jacocoReportSettings := JacocoReportSettings(
  "Jacoco Coverage Report",
  None,
  JacocoThresholds(),
  Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML), // note XML formatter
  "utf-8")

coverageEnabled := true


jacocoExcludes := Seq(
  "de.htwg.se.battleship.Battleship*",
  "de.htwg.se.battleship.util.Observable*",
  "de.htwg.se.battleship.controller.controllerImpl.SetCommand*",
  "de.htwg.se.battleship.aview.gui*",
  "de.htwg.se.battleship.model.fileIOImpl*"
)