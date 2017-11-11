package examples.job

import examples.transformation.DataFrameJoinTransformation
import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLParameters, ETLPayload}
import io.github.dimpac.pipeliner.core.step.output.single.LoadStep

object DenormaliseFileExample {

  def main(args: Array[String]) {
    import scalaz._
    import Scalaz._

    val params = new ETLParameters {
      override val params: Map[String, Any] = Map(
        "destinationPath" -> "tmp/denorm.parquet"
      )
    }
    params |> extractTransform |> loadFile
  }

  def extractTransform(params: ETLParameters): Try[ETLPayload] = {
    for {
      denormalisedDFs <- DataFrameJoinTransformation.transform(params)
    } yield denormalisedDFs
  }

  /**
    * Runs the Load in the ETL
    */
  val loadFile = new LoadStep {
    override val destinationPathIdentifier: String = "destinationPath"
  }
}
