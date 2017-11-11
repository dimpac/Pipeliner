package io.github.dimpac.pipeliner.util

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkUtil {

  lazy val config = ConfigFactory.load()

  lazy val sparkConf = new SparkConf().setAll(configToMap(config.getConfig("svc.spark.config")))

  lazy val spark = SparkSession.builder.config(sparkConf).getOrCreate()

  def configToMap(config: Config): Map[String, String] = {
    import scala.collection.JavaConversions._
    config.entrySet.map {
      case entry => (entry.getKey, entry.getValue.unwrapped.toString)
    }.toMap
  }
}
