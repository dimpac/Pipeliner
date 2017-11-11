package io.github.dimpac.pipeliner.core.step.transformation

case class DataFrameLookupStepKey(field1: String, field2: String)

case class DataFrameLookupStepValue(field: String, newName: Option[String])
