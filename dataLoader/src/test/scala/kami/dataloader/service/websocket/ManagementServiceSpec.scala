package kami.dataloader.service.websocket

import akka.http.scaladsl.Http
import akka.http.scaladsl.testkit.{ScalatestRouteTest, WSProbe}
import org.scalatest.{Matchers, WordSpec}
import spray.json._
import kami.dataloader.Environment.{instance => config}


class ManagementServiceSpec extends WordSpec
                                    with Matchers
                                    with ScalatestRouteTest
                                    with ControlRoute {
  
  val wsConfig = config.interface.ws
  val bindingFuture = Http().bindAndHandle(
    controlRoute, wsConfig.host, wsConfig.port)
  
  "Controller service" should {
    "handle 'start' command" in {
      val wsClient = WSProbe()
      WS(s"http://${wsConfig.host}/collector/control", wsClient.flow) ~> controlRoute ~> check{
        isWebSocketUpgrade shouldEqual true
        val startCommandRequest =
          """
             {"command":"start"}
          """
        val startCommandResponse =
          """
            |{
            |  "ok": true,
            |  "commandName": "start_ack"
            |}
          """.stripMargin.parseJson.prettyPrint
        
        wsClient.sendMessage(startCommandRequest)
        wsClient.expectMessage(startCommandResponse.parseJson.prettyPrint)
      }
    }
    "handle 'stop' command" in {
      val wsClient = WSProbe()
      WS(s"http://${wsConfig.host}/collector/control", wsClient.flow) ~> controlRoute ~> check{
        isWebSocketUpgrade shouldEqual true
        val startCommandRequest =
          """
             {"command":"stop"}
          """
        val startCommandResponse =
          """
            |{
            |  "ok": true,
            |  "commandName": "stop_ack"
            |}
          """.stripMargin.parseJson.prettyPrint
        
        wsClient.sendMessage(startCommandRequest)
        wsClient.expectMessage(startCommandResponse.parseJson.prettyPrint)
      }
    }
    
    "handle unknown command correctly" in {
      val wsClient = WSProbe()
      WS(s"http://${wsConfig.host}/collector/control", wsClient.flow) ~> controlRoute ~> check{
        isWebSocketUpgrade shouldEqual true
        val startCommandRequest =
          """
             {"command":"unknown"}
          """
        val startCommandResponse =
          """
            |{
            |  "ok": false,
            |  "error": {
            |    "message": "Unknown command: 'unknown'"
            |  }
            |}
          """.stripMargin.parseJson.prettyPrint
        
        wsClient.sendMessage(startCommandRequest)
        wsClient.expectMessage(startCommandResponse.parseJson.prettyPrint)
      }
    }
    "handle unknown message format correctly" in {
      val wsClient = WSProbe()
      WS(s"http://${wsConfig.host}/collector/control", wsClient.flow) ~> controlRoute ~> check{
        isWebSocketUpgrade shouldEqual true
        val startCommandRequest =
          """
             {"unknownCommand":"someValue"}
          """
        val startCommandResponse =
          """
            |{
            |  "ok": false,
            |  "error": {
            |    "message": "Unknown request format. Received: {\"unknownCommand\":\"someValue\"}"
            |  }
            |}
          """.stripMargin.parseJson.prettyPrint
        
        wsClient.sendMessage(startCommandRequest)
        wsClient.expectMessage(startCommandResponse.parseJson.prettyPrint)
      }
    }
  }
}