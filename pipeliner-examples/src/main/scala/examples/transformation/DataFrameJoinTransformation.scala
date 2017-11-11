package examples.transformation

import examples.datasource.DenormalisationDataSource
import io.github.dimpac.pipeliner.core.Try
import org.apache.spark.sql. SparkSession
import io.github.dimpac.pipeliner.core.engine.{ETLParameters, ETLPayloadPair, Transformation}
import io.github.dimpac.pipeliner.core.step.input.single.FileInputStep
import io.github.dimpac.pipeliner.core.step.input.single.FormatType.parquet
import io.github.dimpac.pipeliner.core.step.transformation.pair.{DataframeJoinStep, JoinKeys}
import io.github.dimpac.pipeliner.core.step.transformation.pair.JoinType.{JoinType, inner}
import scalaz._
import Scalaz._

object DataFrameJoinTransformation extends DenormalisationDataSource {

  val loadDimProduct = new FileInputStep {
    override val filePath: String = "tmp/product.parquet"
    override val formatType = parquet
    override val sparkSession: SparkSession = spark
    override val outputFields: Seq[String] = Seq("a", "b")
  }

  val loadSample = new FileInputStep {
    override val filePath: String = "tmp/orders.parquet"
    override val formatType = parquet
    override val sparkSession: SparkSession = spark
    override val outputFields: Seq[String] = Seq("a", "b")
  }

  val joinProduct = new DataframeJoinStep {
    override val joinType: JoinType = inner
    override val joinKeys: Seq[JoinKeys] = Seq(JoinKeys("productID", "productID"))
    override val outputFields: Seq[String] = Seq("a", "b")
  }

  val f: (ETLParameters) => Try[ETLPayloadPair] = loadDimProduct tup loadSample

  val transform = Transformation(f >>> joinProduct)

}