package workingstats.collector.service.websocket

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import workingstats.commons.Logging

trait ControlRoute extends ControlHandlers with Logging {
  
  val controlRoute: Route =
    pathPrefix("collector"){
      path("control"){
        handleWebSocketMessages(requestProcessor)
      }
    }
}

case class D(
  id: String
)