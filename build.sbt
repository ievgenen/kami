import Settings._
import Dependencies._
import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
import com.typesafe.sbt.packager.SettingsHelper._

scalaVersion in ThisBuild := "2.12.4"
maintainer in Docker := "Ievgen Liashchenko"
shellPrompt in ThisBuild := { state => Project.extract(state).currentRef.project + "> " }
fork in run := true
cancelable in Global := true

lazy val kami = (project in file("."))
  .settings(doNotPublishSettings)
  .aggregate(dataloader, adminDataService)

lazy val admin = project.enablePlugins(PlayScala)
  .enablePlugins(PlayScala)
  .settings(commonSettings)
  .settings(libraryDependencies += guice)
  .settings(play.modules)
  .settings(PlayKeys.playRunHooks += Webpack.apply(baseDirectory.value))


lazy val dataloader = (project in file("data-loader"))
  .settings(name := "dataloader")
  .settings(Seq(mainClass in Compile := Some("kami.dataloader.bootstrap")))
  .enablePlugins(JavaAppPackaging, BuildInfoPlugin, GitVersioning)
  .settings(makeDeploymentSettings(Universal, packageZipTarball in Universal, "tar.gz"))
  .settings(commonSettings, buildSettings, publishSettings)
  .settings(akka.modules, database.modules, scalatest.modules, confs.modules, logging.modules, fp.modules)
  .dependsOn(commons)

lazy val adminDataService = (project in file("admin-data-service"))
  .settings(name := "AdminDataService")
  .settings(Seq(mainClass in Compile := Some("kami.admindataservice.bootstrap")))
  .enablePlugins(JavaAppPackaging, BuildInfoPlugin, GitVersioning)
  .settings(makeDeploymentSettings(Universal, packageZipTarball in Universal, "tar.gz"))
  .settings(commonSettings, buildSettings, publishSettings)
  .settings(akka.modules, database.modules, scalatest.modules, confs.modules, logging.modules, fp.modules)
  .dependsOn(commons)

lazy val commons = (project in file("commons"))
  .settings(name := "commons")
  .settings(commonSettings)
  .settings(logging.modules)

  git.useGitDescribe := true
  git.baseVersion := "0.0.0"
  val VersionRegex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r

  git.gitTagToVersionNumber := {
    case VersionRegex(v, "SNAPSHOT") => Some(s"$v-SNAPSHOT")
    case VersionRegex(v, "") => Some(v)
    case VersionRegex(v, s) => Some(s"$v-$s-SNAPSHOT")
    case _ => None
  }