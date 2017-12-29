import sbt.Keys._
import sbt._

object Settings {

  lazy val commonSettings = scalaSettings ++ lintingSettings

  lazy val scalaSettings = Seq(
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-target:jvm-1.8",
      "-feature",
      "-language:_",
      "-Yno-adapted-args",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture",
      "-Ywarn-unused",
      "-Ypartial-unification")
  )

  lazy val lintingSettings = Seq(
    scalacOptions ++= Seq("-Xlint"),
    scalacOptions in IntegrationTest ~= (_ filterNot (_ == "-Xlint"))
  )

  lazy val buildSettings = Seq(
    organization := "workingstats.io",
    organizationName := "WorkingStats",
    mainClass in Compile := Some("workingstats.collector.bootstrap")
  )
}



