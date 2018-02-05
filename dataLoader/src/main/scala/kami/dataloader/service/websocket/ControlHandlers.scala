package kami.dataloader.service.websocket

import akka.http.scaladsl.model.ws.{ Message, TextMessage }
import akka.stream.FlowShape
import akka.stream.scaladsl.{ Flow, GraphDSL, Merge, Partition }
import cats.syntax.either._
import spray.json._
import kami.dataloader.protocol.Errors._
import kami.dataloader.protocol._
import kami.dataloader.service.SystemContext
import kami.commons.Logging

trait ControlHandlers extends SystemContext with Logging {
  
  import kami.dataloader.protocol.JsonSupport._
  
  type RequestProcessingResult = Either[ServerError, BaseRequest]
  
  val requestHandler = {
    Flow[Message].collect{
      case TextMessage.Strict(request) =>
        logger.info(s"Received request: $request")
        request
    }.map(unmarshall)
  }
  
  val requestProcessor = Flow.fromGraph(GraphDSL.create(){ implicit builder =>
    import GraphDSL.Implicits._
    
    val partitioner = builder.add(Partition[RequestProcessingResult](2, {
      case Right(_) => 0
      case Left(_) => 1
    }))
    
    val merge = builder.add(Merge[Message](2))
    val source = builder.add(requestHandler)
    
    source.out ~> partitioner
    partitioner ~> successFlow ~> merge
    partitioner ~> errorFlow ~> merge
    
    FlowShape(source.in, merge.out)
  })
  
  def successFlow = Flow[RequestProcessingResult].mapConcat{
    case Right(command) =>
      command.command match {
        case Command.Start =>
          logger.info(s"Start command received.")
          TextMessage.Strict(
            Response(ok = true, Some(Command.Start.response)
            ).toJson.prettyPrint) :: Nil
        
        case Command.Stop =>
          logger.info(s"Stop command received.")
          TextMessage.Strict(
            Response(ok = true, Some(Command.Stop.response)
            ).toJson.prettyPrint) :: Nil
      }
  }
  
  def errorFlow = Flow[RequestProcessingResult].mapConcat{
    case Left(error) => error :: Nil
    case Right(_) => Nil
  }.map{ error =>
    TextMessage.Strict(
      Response(ok = false, None, Option(error)
      ).toJson.prettyPrint)
  }
  
  def unmarshall(request: String): RequestProcessingResult = {
    try {
      Either.right(request.parseJson.convertTo[Request])
    }
    catch {
      case ex: DeserializationException =>
        logger.error(s"Deserialization failed: ${ ex.getMessage }")
        Either.left(ServerError(message = ex.getMessage))
      case ex: Exception =>
        logger.error(s"General server error: ${ ex.getCause }")
        Either.left(ServerError(
          message = ex.getMessage,
          cause = Option(ex.getCause.toString)))
    }
  }
}