package io.github.dimpac.pipeliner.core.engine

import io.github.dimpac.pipeliner.core.Try

trait Transform[A,B] extends (Try[A] => Try[B]) {
  def apply(a: Try[A]): Try[B]

}
