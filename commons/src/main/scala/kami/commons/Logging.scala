package kami.commons

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait Logging {
  protected lazy val logger = Logger(LoggerFactory.getLogger(getClass.getName))
}
