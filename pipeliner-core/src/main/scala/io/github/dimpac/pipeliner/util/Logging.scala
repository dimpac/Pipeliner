package io.github.dimpac.pipeliner.util

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait Logging {
  @transient val logger = Logger(LoggerFactory.getLogger(this.getClass))
}