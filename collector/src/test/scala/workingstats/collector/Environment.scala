package workingstats.collector

import net.ceedubs.ficus.Ficus._
import com.typesafe.config.{ Config, ConfigFactory }

final class Environment(config: Config) {
  object interface {
    object ws {
      val host = config.as[String]("ws.host")
      val port = config.as[Int]("ws.port")
    }
  }
}

object Environment {
  lazy val instance = new Environment(ConfigFactory.load())
}
