name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.6.4"

scalaVersion := "2.11.8"

exportJars := true

scalacOptions ++= Seq("-feature", "-unchecked", "-Xlint", "-deprecation", "-Xfatal-warnings", "-target:jvm-1.8")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint", "-Werror")

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.6.2",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
  "com.typesafe" % "config" % "1.3.0"
)

// Removed git based libraries in favor of pre-compiled libs.
// Please refer to the README in https://github.com/harrystech/ingestion-utils
// Depends on:
//   * https://github.com/harrystech/scala-postgres-utils

// --
//  Test Setup
// --

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)
