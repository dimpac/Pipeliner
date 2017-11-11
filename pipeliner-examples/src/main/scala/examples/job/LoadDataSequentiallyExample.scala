package examples.job

import examples.transformation.{OrdersExtractTransformation, ProductExtractTransformation}
import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLParameters, ETLPayload}
import io.github.dimpac.pipeliner.core.step.output.multi.SequentialLoadStep
import scalaz._
import Scalaz._

object LoadDataSequentiallyExample {

    def main(args: Array[String]) {

      val params1 = new ETLParameters {
        override val params: Map[String, Any] = Map(
          "destinationPath" -> "tmp/product.parquet"
        )
      }

      val params2 = new ETLParameters {
        override val params: Map[String, Any] = Map(
          "destinationPath" -> "tmp/orders.parquet"
        )
      }

      Seq(params1,params2) |> extractTransform |> loadFile
    }

    def extractTransform(params: Seq[ETLParameters]): Try[Seq[ETLPayload]] = {
      for {
        dfs1 <- OrdersExtractTransformation.transform(params(0))
        dfs2 <- ProductExtractTransformation.transform(params(1))
      } yield Seq(dfs1, dfs2)
    }

    /**
      * Runs the Load in the ETL
      */
    val loadFile = new SequentialLoadStep {
      override val destinationPathIdentifier: String = "destinationPath"
    }

}
