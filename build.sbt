import Dependencies._

name := "Pipeliner"

version := "1.0"

scalaVersion := "2.11.11"

organization := "io.github.dimpac"

resolvers += Resolver.sonatypeRepo("releases")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

val makeVersionProperties = taskKey[Seq[File]]("Creates a version.properties file we can find at runtime.")

val paradiseVersion = "2.1.0"

lazy val commonSettings = Seq(
  organization := "io.github.dimpac",
  version := "0.0.0-SNAPSHOT",
  scalaVersion := "2.11.11",
  libraryDependencies := coreDeps,
  addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)
)

lazy val pipeliner_macros = (project in file("pipeliner-macros"))
  .settings(commonSettings)

lazy val pipeliner_core = (project in file("pipeliner-core"))
  .settings(commonSettings)
  .aggregate(pipeliner_macros)
  .dependsOn(pipeliner_macros)

lazy val pipeliner_examples = (project in file("pipeliner-examples"))
  .settings(commonSettings)
  .aggregate(pipeliner_core)
  .dependsOn(pipeliner_core)

releaseVersionBump := sbtrelease.Version.Bump.Next

releaseSnapshotDependencies := Nil