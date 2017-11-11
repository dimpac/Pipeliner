package io.github.dimpac.pipeliner.core.step.input.single

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLLineageNode, ETLParameters, ETLPayload, Extract}
import io.github.dimpac.pipeliner.core.step.input.single.FormatType.FormatType
import org.apache.spark.sql._
import scalax.collection.GraphPredef._
import scalaz.\/-

object FormatType extends Enumeration {
  type FormatType = Value
  val parquet, csv = Value
}

trait FileInputStep extends Extract[ETLParameters, ETLPayload] {
  val filePath: String
  val sparkSession: SparkSession
  val inputFields: Seq[String] = Seq()
  val outputFields: Seq[String] = Seq()
  val formatType: FormatType
  val options: Option[Map[String,String]] = None

  def apply(params: ETLParameters): Try[ETLPayload] = {
    val format = formatType match {
      case FormatType.parquet => "parquet"
      case FormatType.csv => "com.databricks.spark.csv"
      case _ => throw new Exception("Format Type not valid")
    }

    val sparkReader = sparkSession.read
    val sparkReaderWithOptions = options.fold(sparkReader)(readerOptions => sparkReader.options(readerOptions))
    val df2 = sparkReaderWithOptions.format(format).load(filePath)

    \/-(ETLPayload(df2,params, ETLLineageNode("Start", Seq()) ~> ETLLineageNode("Start", Seq()) ~> ETLLineageNode("DF Single Input Step", outputFields)))
  }

}
