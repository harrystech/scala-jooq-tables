name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.6.0"

scalaVersion := "2.11.7"

exportJars := true

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.6.2",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "com.typesafe" % "config" % "1.3.0"
)

// --
//  Test Setup
// --

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)


