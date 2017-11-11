package io.github.dimpac.pipeliner.core.engine

import io.github.dimpac.pipeliner.core.step.input.single.FileInputStep
import io.github.dimpac.pipeliner.core.step.input.single.FormatType._
import io.github.dimpac.pipeliner.util.SparkUtilTest
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite
import scalaz.\/-

class FileInputTest extends FunSuite with SparkUtilTest {

  val params = new ETLParameters {
    override val params: Map[String, Any] = Map(
      "stringParam" -> "value",
      "booleanParam" -> true,
      "intParam" -> 3
    )
  }

  test("When the products csv file is read, five lines should be counted"){

    val loadOrders = new FileInputStep {
      override val filePath: String = "pipeliner-examples/src/main/resources/products.csv"
      override val formatType = csv
      override val sparkSession: SparkSession = spark
      override val options = Some(Map("header" -> "true", "inferSchema" -> "true"))
    }

    val payload = loadOrders(params)

    payload match {
      case \/-(a) => assert(a.df.count == 5)
      case _ => throw new Exception("Failed test")
    }
  }
}
