package kami.admindataservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import Environment.{instance => config}
import kami.admindataservice.service.SystemContext
import kami.admindataservice.service.rest.CommonDataRoute
import kami.commons.Logging

import scala.util.{Failure, Success}

object bootstrap extends App
  with SystemContext
  with CommonDataRoute
  with Logging {
  
  implicit val system: ActorSystem = ActorSystem("admin-data-service")
  implicit def executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()
  
  val httpConfig = config.interface.http
  val http1 = Http().bind(interface = "", port = 4444)
  
  val httpEndpoint = Http().bindAndHandle(
    controlRoute, httpConfig.host, httpConfig.port)
  
  httpEndpoint.onComplete{
    case Success(_) ⇒
      logger.info(s"WorkingStats Admin Data Service started at ${httpConfig.host}:${httpConfig.port}")
    case Failure(e) ⇒
      println(s"Service binding failed with ${ e.getMessage }")
      system.terminate()
  }
  
  StdIn.readLine()
  httpEndpoint
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}