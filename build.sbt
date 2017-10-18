name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.6.5"

scalaVersion := "2.12.3"

exportJars := true

scalacOptions ++= Seq("-feature", "-unchecked", "-Xlint", "-deprecation", "-Xfatal-warnings", "-target:jvm-1.8")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint", "-Werror")

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.10.1",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
  "com.typesafe" % "config" % "1.3.0"
)

lazy val `scala-postgres-utils` = RootProject(uri("ssh://git@github.com/harrystech/scala-postgres-utils.git#v0.1.5_2_12"))

lazy val `scala-jooq-tables` = (project in file(".")).dependsOn(
  `scala-postgres-utils`
)

// This forces the scala version used between these to match
scalaVersion in `scala-postgres-utils` := (scalaVersion in `scala-jooq-tables`).value

// --
//  Test Setup
// --

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.13.5" % Test,
  "org.scalatest" % "scalatest_2.12" % "3.2.0-SNAP9" % Test
)
