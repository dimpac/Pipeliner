package io.github.dimpac.pipeliner.core.engine

import org.scalatest.FunSuite
import io.github.dimpac.pipeliner.util.implicits._

class ETLParametesTest extends FunSuite {

  val params = new ETLParameters {
    override val params: Map[String, Any] = Map(
      "stringParam" -> "value",
      "booleanParam" -> true,
      "intParam" -> 3
    )
  }

  test("When a string parameter does not exist, getAs should return None") {
    val param: Option[String] = params.getAs[String]("thisParameterDoesNotExist")
    assert(param.isEmpty)
  }

  test("When a string parameter exists, getAs should return some value") {
    val param: Option[String] = params.getAs[String]("stringParam")
    assert(param.contains("value"))
  }

  test("When a boolean parameter does not exist, getAs should return None") {
    val param: Option[Boolean] = params.getAs[Boolean]("thisParameterDoesNotExist")
    assert(param.isEmpty)
  }

  test("When a boolean parameter exists, getAs should return true") {
    val param: Option[Boolean] = params.getAs[Boolean]("booleanParam")
    assert(param.contains(true))
  }

  test("When an integer parameter does not exist, getAs should return None") {
    val param: Option[Int] = params.getAs[Int]("thisParameterDoesNotExist")
    assert(param.isEmpty)
  }

  test("When an integer parameter exists, getAs should return 3") {
    val param: Option[Int] = params.getAs[Int]("intParam")
    assert(param.contains(3))
  }
}
