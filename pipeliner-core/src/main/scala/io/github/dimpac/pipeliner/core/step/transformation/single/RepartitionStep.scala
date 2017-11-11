package io.github.dimpac.pipeliner.core.step.transformation.single

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLLineageNode, ETLPayload, Transform}
import org.apache.spark.sql.Column

trait RepartitionStep extends Transform[ETLPayload, ETLPayload] {
  val outputFields: Seq[String] = Seq()
  val numPartitions: Int
  val partitionExpression: Option[Seq[Column]]

  def apply(input: Try[ETLPayload]) = {
    for {
      payload <- input
    } yield {
      val x = payload.df
      val y = partitionExpression.fold(x.repartition(numPartitions))(pe => x.repartition(numPartitions, pe:_*))
      ETLPayload(y, payload.params, payload.lineageNode ~> ETLLineageNode("Repartition", outputFields))
    }
  }
}
