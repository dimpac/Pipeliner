package io.github.dimpac.pipeliner.util

import org.apache.spark.sql.SparkSession

trait SparkUtilTest {
  lazy val spark = SparkSession.builder.appName("Local spark session for tests").master("local[*]").getOrCreate()
}
