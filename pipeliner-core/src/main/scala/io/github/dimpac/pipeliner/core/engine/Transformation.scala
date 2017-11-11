package io.github.dimpac.pipeliner.core.engine

import io.github.dimpac.pipeliner.core.Try
import scalaz.{Kleisli, ReaderT}

/**
  * Object helper that will transform ETL functions into monads.
  */
object Transformation {
  def apply[A,B](f: A => Try[B]):ReaderT[Try, A, B] = {
    Kleisli.kleisli(f)
  }
}
