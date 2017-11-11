package io.github.dimpac.pipeliner

import io.github.dimpac.pipeliner.core.engine.{ETLError, ETLParameters, ETLPayload, ETLPayloadPair}
import scalaz.\/

package object core {

  type Try[A] = \/[ETLError, A]

  implicit def tuplify[A, B](f: ETLParameters => Try[ETLPayload]) = new {
    def tup(g: ETLParameters => Try[ETLPayload]) = {
      (params: ETLParameters) =>  for {
        e1 <- f(params)
        e2 <- g(params)
      } yield {
        ETLPayloadPair(e1,e2)
      }
    }
  }

  implicit def sequencify[A, B](f: ETLParameters => Try[ETLPayload]) = new {
    def seq(g: ETLParameters => Try[ETLPayload]) = {
      (params: ETLParameters) => for {
        e1 <- f(params)
        e2 <- g(params)
      } yield {
        Seq(e1, e2)
      }
    }
  }
}
