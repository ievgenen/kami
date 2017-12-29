import sbt.ModuleID
import sbt.Keys._
import sbt._

object Dependencies {

  object Version {
    val akka = "2.5.6"
    val akka_http = "10.0.10"
    val akka_spray_json = "2.4.4"
    val cats = "1.0.0-RC1"
    val ficus = "1.4.3"
    val gatling = "2.3.0"
    val kafkaClients = "0.9.0.0"
    val logback = "1.2.3"
    val logbackLogsash = "4.11"
    val logbackKafkaAppender = "0.1.0"
    val monocle = "1.4.0"
    val paradiseVersion = "2.1.0"
    val playJson = "2.6.3"
    val playJsonExtra = "0.5.0"
    val scalaLogging = "3.7.2"
    val scalamock = "3.6.0"
    val scalatest = "3.0.1"
    val shapeless = "2.3.2"
    val typesafeConfig = "1.3.1"
    val url = "0.4.16"
  }

  object akka {
    val actor = "com.typesafe.akka" %% "akka-actor" % Version.akka
    val slf4j = "com.typesafe.akka" %% "akka-slf4j" % Version.akka

    val cluster = "com.typesafe.akka" %% "akka-cluster" % Version.akka
    val clusterTools = "com.typesafe.akka" %% "akka-cluster-tools" % Version.akka

    val http = "com.typesafe.akka" %% "akka-http-core" % Version.akka_http
    val httpDsl = "com.typesafe.akka" %% "akka-http" % Version.akka_http
    val httpJackson = "com.typesafe.akka" %% "akka-http-jackson" % Version.akka_http
    val httpXml = "com.typesafe.akka" %% "akka-http-xml" % Version.akka_http

    val stream = "com.typesafe.akka" %% "akka-stream" % Version.akka
    val persistence = "com.typesafe.akka" %% "akka-persistence" % Version.akka
    val contrlib = "com.typesafe.akka" %% "akka-contrib" % Version.akka
    val sprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % Version.akka_http

    val testKit = "com.typesafe.akka" %% "akka-testkit" % Version.akka
    val streamTest = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka
    val httpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akka_http
    val multinodeTest = "com.typesafe.akka" %% "akka-multi-node-testkit" % Version.akka

    val modules = Seq(libraryDependencies ++=
      (compile(actor, slf4j, cluster, clusterTools,
        http, httpDsl, httpJackson, httpXml, stream, persistence,
        contrlib, sprayJson) ++
      test(httpTestkit, testKit, streamTest, multinodeTest)))
  }

  object gatlingTest {
    val highCharts = "io.gatling.highcharts" % "gatling-charts-highcharts" % Version.gatling
    val framework = "io.gatling" % "gatling-test-framework" %  Version.gatling
    val modules = Seq(libraryDependencies ++= test(framework, highCharts))
  }

  object scalatest {
    val scalatest = "org.scalatest" %% "scalatest" % Version.scalatest
    val scalamock = "org.scalamock" %% "scalamock-scalatest-support" % Version.scalamock
    val modules = Seq(libraryDependencies ++= test(scalatest, scalamock))
    val itModules = Seq(libraryDependencies ++= it(scalatest, scalamock))
  }

  object confs {
    val typesafe =  "com.typesafe" % "config" % Version.typesafeConfig
    val ficus = "com.iheart" %% "ficus" % Version.ficus
    val modules = Seq(libraryDependencies ++=
      compile(typesafe, ficus) ++ test(typesafe, ficus)
    )
  }

  object logging {
    val logbackCore = "ch.qos.logback" % "logback-classic" % Version.logback
    val logbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % Version.logbackLogsash
    val kafkaEncoder = "com.github.danielwegener" % "logback-kafka-appender" % Version.logbackKafkaAppender
    val kafkaClients = "org.apache.kafka" % "kafka-clients" % Version.kafkaClients
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging
    val modules = Seq(libraryDependencies ++=
      compile(scalaLogging, logbackCore, logbackEncoder, kafkaEncoder, kafkaClients)
    )
  }

  object fp {
    object monocle {
      val core = "com.github.julien-truffaut" %%  "monocle-core"  % Version.monocle
      val mmacro = "com.github.julien-truffaut" %%  "monocle-macro" % Version.monocle
      val law = "com.github.julien-truffaut" %%  "monocle-law"   % Version.monocle
      val modules = Seq(libraryDependencies ++=
        compile(core, mmacro, law) ++
          test(core, mmacro, law))
    }
    object cats {
      val core = "org.typelevel" %% "cats-core" % Version.cats
      val macros = "org.typelevel" %% "cats-macros" % Version.cats
      val kernel = "org.typelevel" %% "cats-kernel" % Version.cats
      val modules = Seq(libraryDependencies ++=
        compile(core, macros, kernel) ++
          test(core, macros, kernel)
      )
    }

    val shapeless = "com.chuusai" %% "shapeless" % Version.shapeless

    val modules = Seq(
      libraryDependencies ++=
        compile(shapeless)
          ++ test(shapeless)) ++ monocle.modules ++ cats.modules
  }

  object json {
    val paradise =  "org.scalamacros" % "paradise_2.12.1" % Version.paradiseVersion
    val playJson = "com.typesafe.play" %% "play-json" % Version.playJson
    val playJsonExtra = "com.github.xuwei-k" %% "play-json-extra" % Version.playJsonExtra
    val modules = Seq(libraryDependencies ++= compile(paradise))
  }

  def it(m: ModuleID*): Seq[ModuleID] = m map (_ % "it,test")
  def test(m: ModuleID*) = m map (_ % "test")
  def compile(m: ModuleID*) = m map (_ % "compile")

}