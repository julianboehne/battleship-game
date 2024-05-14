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
  "ch.qos.logback" % "logback-classic" % "1.5.6" % Runtime,
  ("com.typesafe.slick" %% "slick" % "3.5.0-M3").cross(CrossVersion.for3Use2_13),
  "org.postgresql" % "postgresql" % "42.7.3",
)

