name := """scala-jooq-tables"""

organization := "com.harrys"

version := "1.6.5"

scalaVersion := "2.11.8"

exportJars := true

scalacOptions ++= Seq("-feature", "-unchecked", "-Xlint", "-deprecation", "-Xfatal-warnings", "-target:jvm-1.8")

publishArtifact in (Compile, packageDoc) in ThisBuild := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint", "-Werror")

resolvers += "Harry's Releases" at "https://harrys.jfrog.io/harrys/harrys-releases/"
resolvers += "JCenter Cache" at "https://harrys.jfrog.io/harrys/jcenter-cache/"

credentials += {
  try {
    // If credentials are provided in env vars, use those
    Credentials("Artifactory Realm", "harrys.jfrog.io", sys.env("JFROG_USERNAME"), sys.env("JFROG_PASSWORD"))
  } catch {
    // Otherwise try to load them from the current user's environment
    case e: java.util.NoSuchElementException =>
      Credentials(Path.userHome / ".sbt" / "harrys-artifactory.credentials")
  }
}

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.6.2",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
  "com.harrys" % "scala-postgres-utils_2.10" % "0.1.5",
  "com.typesafe" % "config" % "1.3.0"
)


// --
//  Test Setup
// --

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.5" % Test,
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)

publishTo := Some("Artifactory Realm" at "https://harrys.jfrog.io/harrys/harrys-releases/")
