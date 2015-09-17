name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.6.2"
)

exportJars := true

libraryDependencies <+= (scalaVersion){ "org.scala-lang" % "scala-reflect" % _ }