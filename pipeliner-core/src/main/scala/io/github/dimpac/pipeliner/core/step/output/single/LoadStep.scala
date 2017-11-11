package io.github.dimpac.pipeliner.core.step.output.single

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLPayload, Load, ParameterNotFound}
import io.github.dimpac.pipeliner.util.Logging
import org.apache.spark.sql.SaveMode
import scalaz.{-\/, \/-}
import io.github.dimpac.pipeliner.util.implicits._

trait LoadStep extends Load[ETLPayload, Unit] with Logging {

  val destinationPathIdentifier: String
  val saveMode: SaveMode = SaveMode.Overwrite

  def apply(input: Try[ETLPayload]):Unit = {

    input match {
      case \/-(v) => v.params.getAs[String](destinationPathIdentifier)
        .fold(apply(-\/(ParameterNotFound(destinationPathIdentifier)))){
        strPath => v.df.write.mode(saveMode).parquet(strPath)
      }
      case -\/(e) => logger.error(e.toString)
    }
  }
}