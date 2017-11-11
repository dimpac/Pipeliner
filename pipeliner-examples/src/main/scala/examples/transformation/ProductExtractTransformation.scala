package examples.transformation

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLParameters, ETLPayload, Transformation}
import io.github.dimpac.pipeliner.core.step.input.single.{FileInputStep, FormatType}
import io.github.dimpac.pipeliner.core.step.input.single.FormatType.csv
import io.github.dimpac.pipeliner.core.step.transformation.single.RepartitionStep
import io.github.dimpac.pipeliner.util.SparkUtil
import org.apache.spark.sql.{Column, SparkSession}

import scalaz._
import Scalaz._

object ProductExtractTransformation extends SparkUtil {

  val loadDimProduct: ETLParameters => Try[ETLPayload] = new FileInputStep {
    override val filePath: String = "pipeliner-examples/src/main/resources/products.csv"
    override val formatType = csv
    override val sparkSession: SparkSession = spark
    override val options = Some(Map("header" -> "true", "inferSchema" -> "true"))
  }

  val repartitionProduct = new RepartitionStep {
    override val partitionExpression: Option[Seq[Column]] = None
    override val numPartitions: Int = 1
  }

  val transform = Transformation(loadDimProduct >>> repartitionProduct)

}
