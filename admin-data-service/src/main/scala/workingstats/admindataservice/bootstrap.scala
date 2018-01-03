package workingstats.admindataservice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import Environment.{instance => config}
import workingstats.admindataservice.service.SystemContext
import workingstats.admindataservice.service.websocket.CommonDataRoute
import workingstats.commons.Logging

import scala.util.{Failure, Success}

object bootstrap extends App
  with SystemContext
  with CommonDataRoute
  with Logging {
  
  implicit val system: ActorSystem = ActorSystem("admin-data-service")
  implicit def executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()
  
  val httpConfig = config.interface.http
  val bindingFuture = Http().bindAndHandle(
    controlRoute, httpConfig.host, httpConfig.port)
  
  bindingFuture.onComplete{
    case Success(_) ⇒
      logger.info(s"WorkingStats Admin Data Service started at ${ httpConfig.host }:${ httpConfig.port }")
    case Failure(e) ⇒
      println(s"Service binding failed with ${ e.getMessage }")
      system.terminate()
  }
  
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}