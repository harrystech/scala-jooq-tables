name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.6.2"

scalaVersion := "2.11.7"

exportJars := true

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.6.2",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
  "com.typesafe" % "config" % "1.3.0"
)

lazy val `scala-postgres-utils` = RootProject(uri("ssh://git@github.com/harrystech/scala-postgres-utils.git#v0.1.0"))

lazy val `scala-jooq-tables` = (project in file(".")).dependsOn(
  `scala-postgres-utils`
)

// --
//  Test Setup
// --

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)
