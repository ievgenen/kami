package workingstats.collector.protocol

import Command.Command
import spray.json.JsValue

sealed trait BaseRequest extends Product with Serializable {
  def command: Command
  def data: JsValue
}

case class Request(
  command: Command,
  data: JsValue) extends BaseRequest

case class Response(
  ok: Boolean,
  commandName: Option[String],
  error: Option[Errors.ServerError] = None)

object Errors {
  
  case class ServerError(message: String,
    cause: Option[String] = None)
}

object Command extends Enumeration {
  type Command = Value
  
  protected case class Val(
    request: String,
    response: String) extends super.Val
  
  implicit def valueToVal(v: Value): Val = v.asInstanceOf[Val]
  
  val Start = Val("start", "start_ack")
  val Stop = Val("stop", "stop_ack")
}
