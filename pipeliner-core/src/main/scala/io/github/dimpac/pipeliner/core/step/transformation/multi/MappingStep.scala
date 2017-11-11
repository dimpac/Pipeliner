package io.github.dimpac.pipeliner.core.step.transformation.multi

import io.github.dimpac.pipeliner.core.engine.{ETLPayload, Transform}
import scala.util.Try

/**
  * Step for simple mapping functions
  */
trait MappingStep extends Transform[Seq[ETLPayload], Seq[ETLPayload]] {

  def f(a: Seq[ETLPayload]): Seq[ETLPayload]

  def apply(input: Try[Seq[ETLPayload]]) = {
    for {
      payloadSeq <- input
    } yield f(payloadSeq)
  }
}
