package examples.datasource

import io.github.dimpac.pipeliner.util.SparkUtil

trait DenormalisationDataSource extends SparkUtil {

  final val productDF = spark.read.parquet("tmp/product.parquet")

  //final val storeDF = spark.read.parquet("tmp/store.parquet")

  final val factSampleDF = spark.read.parquet("tmp/orders.parquet")

}
