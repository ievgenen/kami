package kami.admindataservice.service

import akka.actor.ActorSystem
import akka.stream.Materializer
import scala.concurrent.ExecutionContextExecutor

trait SystemContext {
  implicit def system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit def materializer: Materializer
}
