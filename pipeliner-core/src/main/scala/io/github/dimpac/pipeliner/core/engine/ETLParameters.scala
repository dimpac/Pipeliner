package io.github.dimpac.pipeliner.core.engine

/**
  * Trait used to encapsulate parameters used during the ETL
  */
trait ETLParameters {

  val params: Map[String, Any]

  def updated(key: String, value: Any, parms: Map[String,Any]) = new ETLParameters {
    val params = parms.updated(key, value)
  }

}
