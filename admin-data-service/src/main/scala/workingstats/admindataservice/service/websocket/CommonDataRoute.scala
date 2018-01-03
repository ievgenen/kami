package workingstats.admindataservice.service.websocket

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import workingstats.commons.Logging

trait CommonDataRoute extends Logging {
  
  val controlRoute: Route =
    pathPrefix("admindata"){
      get {
        path("countries") {
          complete("Admin Data Service Country List!")
        }
        path("lendingtypes") {
          complete("Admin Data Service Country List!")
        }
      }
    }
}