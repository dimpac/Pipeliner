package io.github.dimpac.pipeliner.core.engine

import io.github.dimpac.pipeliner.core.Try

trait Load[A,B] extends (Try[A] => B) {
  def apply(a: Try[A]): B
}
