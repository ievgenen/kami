package kami.dataloader

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ ActorMaterializer, Materializer }
import kami.dataloader.service.SystemContext
import kami.dataloader.service.websocket.ControlRoute
import kami.commons.Logging
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import Environment.{ instance => config }

import scala.util.{ Failure, Success }

object bootstrap extends App
  with SystemContext
  with ControlRoute
  with Logging {
  
  implicit val system: ActorSystem = ActorSystem("collector-system")
  implicit def executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()
  
  val wsConfig = config.interface.ws
  val bindingFuture = Http().bindAndHandle(
    controlRoute, wsConfig.host, wsConfig.port)
  
  bindingFuture.onComplete{
    case Success(_) ⇒
      logger.info(s"Kami DataLoader WS Server started at ${ wsConfig.host }:${ wsConfig.port }")
    case Failure(e) ⇒
      println(s"Service binding failed with ${ e.getMessage }")
      system.terminate()
  }
  
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}