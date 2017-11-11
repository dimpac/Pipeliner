package io.github.dimpac.pipeliner.util

import io.github.dimpac.pipeliner.core.engine.ETLParameters

object implicits {
  implicit class ParamGetter(etlParams: ETLParameters){
    def getAs[T](paramName: String)(implicit toT: (ETLParameters, String) => Option[T]) = toT(etlParams, paramName)
  }

  implicit val getInt = (etlParams: ETLParameters, paramName: String) => etlParams.params.get(paramName).map(_.asInstanceOf[Int])
  implicit val getString = (etlParams: ETLParameters, paramName: String) => etlParams.params.get(paramName).map(_.asInstanceOf[String])
  implicit val getBoolean = (etlParams: ETLParameters, paramName: String) => etlParams.params.get(paramName).map(_.asInstanceOf[Boolean])
}