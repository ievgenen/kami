import Settings._
import Dependencies._

scalaVersion in ThisBuild := "2.12.4"
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
fork in run := true
cancelable in Global := true

lazy val workingstats = (project in file("."))
  .aggregate(collector)

lazy val collector = (project in file("collector"))
  .settings(name := "collector")
  .settings(commonSettings, buildSettings)
  .settings(akka.modules, scalatest.modules, confs.modules, logging.modules, fp.modules)

