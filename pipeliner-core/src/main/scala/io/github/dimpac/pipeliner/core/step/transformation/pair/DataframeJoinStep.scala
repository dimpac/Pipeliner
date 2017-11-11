package io.github.dimpac.pipeliner.core.step.transformation.pair

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLLineageNode, ETLPayload, ETLPayloadPair, Transform}
import io.github.dimpac.pipeliner.core.step.transformation.pair.JoinType.JoinType
import org.apache.spark.sql.DataFrame

import scalax.collection.GraphPredef._

case class JoinKeys(leftCol: String, rightCol: String)

object JoinType extends Enumeration {
  type JoinType = Value
  val inner, left, right = Value
}

trait DataframeJoinStep extends Transform[ETLPayloadPair, ETLPayload] {

  val joinType: JoinType
  val joinKeys: Seq[JoinKeys]
  val outputFields: Seq[String]

  def apply(input: Try[ETLPayloadPair]): Try[ETLPayload] = {
    for {
      i <- input
    } yield ETLPayload(joinDataFrame(i.left.df, i.right.df), i.left.params, i.left.lineageNode ~> ETLLineageNode("Pair DF Join", outputFields))
  }

  def generateJoinExpr(leftDF: DataFrame, rightDF: DataFrame) = {
    joinKeys
      .map(p => leftDF(p.leftCol) === rightDF(p.rightCol))
      .reduce((a,b) => a && b)
  }

  def joinDataFrame(v1: DataFrame, v2: DataFrame) = {
      val dropColumns = joinKeys.map(p => p.rightCol)

      dropColumns.foldLeft(v1.join(v2, generateJoinExpr(v1,v2)))((a,b) => a.drop(v2(b)))
  }
}
