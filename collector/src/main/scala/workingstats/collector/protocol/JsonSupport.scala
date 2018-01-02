package workingstats.collector.protocol

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat }
import workingstats.collector.protocol.Errors.ServerError

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  
  implicit val serverError = jsonFormat2(ServerError)
  implicit val response = jsonFormat3(Response)
  
  implicit object ControlRequestJsonFormat extends RootJsonFormat[Request] {
    
    def read(value: JsValue): Request = {
      val command = value.asJsObject.getFields("command") match {
        case Seq(JsString(action)) =>
          action match {
            case "start" => Command.Start
            case "stop" => Command.Stop
            case "load_all_commons" => Command.LoadAllCommons
            case "load_countries" => Command.LoadCountries
            case "load_lending_types" => Command.LoadLendingTypes
            case "load_income_levels" => Command.LoadIncomeLevels
            case cmd =>
              throw DeserializationException(s"Unknown command: '$cmd'")
          }
        case _ =>
          throw DeserializationException(s"Unknown request format. Received: $value")
      }
      val data = value.asJsObject.getFields("data").headOption match {
        case Some(jsValue) => jsValue
        case None => JsString("{}")
      }
      Request(command, data)
    }
    
    def write(c: Request): JsValue = ???
  }
}
