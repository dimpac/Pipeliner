package io.github.dimpac.pipeliner.core.engine

sealed trait ETLError

case class ParameterNotFound(param: String) extends ETLError {
  override def toString = s"Parameter [$param] has not been found."
}