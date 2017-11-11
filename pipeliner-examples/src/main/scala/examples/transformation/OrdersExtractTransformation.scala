package examples.transformation


import org.apache.spark.sql.{Column, SparkSession}
import io.github.dimpac.pipeliner.core.engine.Transformation
import io.github.dimpac.pipeliner.core.step.input.single.FileInputStep
import io.github.dimpac.pipeliner.core.step.input.single.FormatType.csv
import io.github.dimpac.pipeliner.util.SparkUtil

object OrdersExtractTransformation extends SparkUtil {

  val loadOrders = new FileInputStep {
    override val filePath: String = "pipeliner-examples/src/main/resources/orders.csv"
    override val formatType = csv
    override val sparkSession: SparkSession = spark
    override val options = Some(Map("header" -> "true", "inferSchema" -> "true"))
  }

  val transform = Transformation(loadOrders)

}
