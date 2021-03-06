package io.github.dimpac.pipeliner.core.step.output.multi

import io.github.dimpac.pipeliner.core.Try
import io.github.dimpac.pipeliner.core.engine.{ETLPayload, Load, ParameterNotFound}
import io.github.dimpac.pipeliner.util.Logging
import scala.collection.parallel.ParSeq
import scalaz.{-\/, \/-}
import io.github.dimpac.pipeliner.util.implicits._

trait ParallelLoadStep extends Load[ParSeq[ETLPayload], Unit] with Logging {

  val destinationPathIdentifier: String

  def apply(input: Try[ParSeq[ETLPayload]]):Unit = {
    input match {
      case \/-(v) => v.foreach{
        payload => payload.params.getAs[String](destinationPathIdentifier)
          .fold(apply(-\/(ParameterNotFound(destinationPathIdentifier)))){
          strPath => payload.df.write.parquet(strPath)
        }
      }
      case -\/(e) => logger.error(e.toString)
    }
  }
}
