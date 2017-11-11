package io.github.dimpac.pipeliner.core.step.transformation.single

import io.github.dimpac.pipeliner.core.engine.{ETLLineageNode, ETLPayload, Transform}
import io.github.dimpac.pipeliner.core.step.transformation.{DataFrameLookupStepKey, DataFrameLookupStepValue}
import org.apache.spark.sql.DataFrame
import scalax.collection.GraphPredef.HyperEdgeAssoc
import scala.util.Try

trait SingleDataframeLookupStep extends Transform[ETLPayload, ETLPayload] {

  val lookupDF: DataFrame
  val lookupKey: DataFrameLookupStepKey
  val returnField: DataFrameLookupStepValue
  val outputFields: Seq[String]

  def apply(input: Try[ETLPayload]) = {
    for {
      payload <- input
    } yield ETLPayload(lookup(payload.df), payload.params, payload.lineageNode ~> ETLLineageNode("Single DF Lookup", outputFields))
  }

  /**
    * Extracts a value from a lookup dataframe
    *
    * @param df
    * @return
    */
  private def lookup(df: DataFrame): DataFrame = {

    //val returnColumns = Seq("*", s"${lookupKey.field2} as ${lookupKey.field1}", s"${returnField.field} as ${returnField.newName}")

    val renamedCol = s"${lookupKey.field1}_1"
    val df2 = lookupDF.withColumnRenamed(lookupKey.field2, renamedCol)

    df
      .join(df2, df(lookupKey.field1) === df2(renamedCol),"left_outer")
      .selectExpr(outputFields:_*)
  }
}
