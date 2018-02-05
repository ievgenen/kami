package kami.dataloader

import net.ceedubs.ficus.Ficus._
import com.typesafe.config.{ Config, ConfigFactory }


final class Environment(config: Config) {
  object interface {
    object http {
      val host = config.as[String]("http.host")
      val port = config.as[Int]("http.port")
    }
    object ws {
      val host = config.as[String]("ws.host")
      val port = config.as[Int]("ws.port")
    }
  }
}

object Environment {
  lazy val instance = new Environment(ConfigFactory.load())
}
