package io.github.dimpac.pipeliner.core.engine

import org.apache.spark.sql.DataFrame

import scalax.collection.GraphEdge

/**
  * The actual ETL payload that will be pipelined through the data flow
 *
  * @param df
  * @param params
  */
case class ETLPayload(df: DataFrame, params: ETLParameters, lineageNode: GraphEdge.DiHyperEdge[ETLLineageNode])
