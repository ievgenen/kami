import java.util.Properties

import com.typesafe.sbt.SbtNativePackager.autoImport.{maintainer, packageDescription, packageSummary}
import sbt.Keys._
import sbt._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, ReleaseTransformations}
import sbtrelease.Version.Bump.Next
import sbtbuildinfo.BuildInfoPlugin.autoImport.{BuildInfoKey, buildInfoKeys, buildInfoPackage}
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.{dockerBaseImage, dockerUpdateLatest}

object Settings {

  lazy val workingStatsRepoUrl = "http://192.168.1.101/"
  lazy val commonSettings = scalaSettings ++ lintingSettings
  lazy val settingsGatling = scalaSettings ++ buildSettings
  lazy val publishSettings = packagerSettings ++ dockerSettings
  lazy val doNotPublishSettings = Seq(publish := {}, publishLocal := {})

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

  import ReleaseTransformations._
  lazy val buildSettings = Seq(
    organization := "workingstats",
    organizationName := "Workingstats",
    buildInfoPackage := "collector",
    maintainer := "Ievgen Liashchenko <ievgenen@gmail.com>",
    packageDescription := "Data Collecting Application",
    packageSummary := "Doing Business Index World Statistics",
    mainClass in Compile := Some("workingstats.collector.bootstrap"),
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    publishMavenStyle := true,
    showSuccess := true,
    ivyLoggingLevel := UpdateLogging.Full,
    releaseVersionBump := Next,
    releaseIgnoreUntrackedFiles := true,
    releaseVersionFile := file(".") / "version.sbt",
    publishTo := {
      val properties = new Properties()
      IO.load(properties, new File("repository.properties"))
      val repo = properties.getProperty("url")
      val path = isSnapshot.value match {
        case true => properties.getProperty("snapshot")
        case false => properties.getProperty("release")
      }
      Some("Nexus Realm" at repo + path)
    },

    credentials in ThisBuild ++= Seq(
      Credentials(Path.userHome / ".ivy2" / ".credentials")
    ),
    publishArtifact in(Compile, packageSrc) := false,
    resolvers ++= Seq(
      "collectorReleasesRepo" at s"$workingStatsRepoUrl/nexus/content/repositories/workingstats/",
      Resolver.typesafeRepo("releases").copy("typesafe-releases-custom"),
      Resolver.sonatypeRepo("snapshots"),
      Resolver.sonatypeRepo("releases")
    )) ++
    Seq(releaseProcess := releaseSteps)

  lazy val packagerSettings = Seq(
    mappings in Universal += {
    val conf = (resourceDirectory in Compile).value / "remote" / "application.conf"
    conf -> "conf/application.conf"
  },
    mappings in Universal += {
    val logback = (resourceDirectory in Compile).value / "remote" / "logback.xml"
    logback → "conf/logback.xml"
  },
    javaOptions in Universal ++= Seq(
    "-Dconfig.file=conf/application.conf",
    "-Dlogback.configurationFile=conf/logback.xml"
    )
  )

  lazy val dockerSettings = Seq(
    dockerBaseImage := "workingstats/openjdk8:141",
    dockerUpdateLatest := true
  )

  val releaseSteps = Seq[ReleaseStep](
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommand("universal:publish"),
    setNextVersion,
    commitNextVersion,
    pushChanges)

}



