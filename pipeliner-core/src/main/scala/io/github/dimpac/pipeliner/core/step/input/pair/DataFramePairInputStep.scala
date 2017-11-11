package io.github.dimpac.pipeliner.core.step.input.pair

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine._
import org.apache.spark.sql.SparkSession
import scalax.collection.GraphPredef._
import scalaz.\/-

trait DataFramePairInputStep extends Extract[ETLParameters, ETLPayloadPair] {
  val filePathLeft: String
  val filePathRight: String
  val sparkSession: SparkSession
  val leftOutputFields: Seq[String]
  val rightOutputFields: Seq[String]

  def apply(params: ETLParameters): Try[ETLPayloadPair] = {
    val dfLeft = sparkSession.read.parquet(filePathLeft)
    val dfRight = sparkSession.read.parquet(filePathRight)
    \/-(ETLPayloadPair(ETLPayload(dfLeft,params,
      ETLLineageNode("Start", Seq()) ~> ETLLineageNode("Start", Seq()) ~> ETLLineageNode("DF Single Left Input Step", leftOutputFields)),
      ETLPayload(dfRight,params, ETLLineageNode("Start", Seq()) ~> ETLLineageNode("Start", Seq()) ~> ETLLineageNode("DF Single Left Input Step", rightOutputFields))
      )
    )
  }
}
