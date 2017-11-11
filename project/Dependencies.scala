import sbt._

object Dependencies {

  val typesafeConfigVersion = sys.env.getOrElse("TSCONFIG_VERSION", "1.3.1")
  val typesageLoggingVersion = sys.env.getOrElse("TSLOGGING_VERSION", "3.5.0")
  val scalaTestVersion = sys.env.getOrElse("SCALATEST_VERSION", "3.0.1")
  val logbackVersion = sys.env.getOrElse("LOGBACK_VERSION", "1.1.7")
  val sparkVersion = sys.env.getOrElse("SPARK_VERSION", "2.2.0")
  val sparkCsvVersion = sys.env.getOrElse("SPARK_CSV_VERSION", "1.5.0")
  val scalazVersion = sys.env.getOrElse("SCALAZ_VERSION", "7.2.12")

  lazy val sparkDeps = Seq (
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-mllib" % sparkVersion
  )

  lazy val sparkCsvDep = Seq(
    "com.databricks" %% "spark-csv" % sparkCsvVersion
  )

  lazy val typesafeDeps = Seq (
    "com.typesafe" % "config" % typesafeConfigVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % typesageLoggingVersion
  )

  lazy val testDeps = Seq (
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )

  lazy val logbackDep = Seq (
    "ch.qos.logback" % "logback-classic" % logbackVersion
  )

  lazy val scalaz = Seq (
    "org.scalaz" %% "scalaz-core" % scalazVersion
  )

  lazy val sgraph = Seq (
    "org.scala-graph" %% "graph-core" % "1.11.5",
    "org.scala-graph" %% "graph-json" % "1.11.0"
  )

  lazy val coreDeps = typesafeDeps ++ logbackDep ++ sparkDeps ++ logbackDep ++ sparkCsvDep ++ scalaz ++ sgraph ++ testDeps
}