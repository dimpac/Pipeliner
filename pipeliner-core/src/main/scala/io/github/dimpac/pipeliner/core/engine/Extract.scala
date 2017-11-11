package io.github.dimpac.pipeliner.core.engine

import io.github.dimpac.pipeliner.core.Try

trait Extract[A,B] extends (A => Try[B]) {
  def apply(a: A): Try[B]
}
