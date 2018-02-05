package kami.dataloader.service.websocket

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import kami.commons.Logging

trait ControlRoute extends ControlHandlers with Logging {
  
  val controlRoute: Route =
    pathPrefix("collector"){
      path("control"){
        handleWebSocketMessages(requestProcessor)
      }
    }
}