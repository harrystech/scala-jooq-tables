name := """scala-jooq-tables"""

organization := "com.harrys.core"

version := "1.6.6"

scalaVersion := "2.12.4"

exportJars := true

publishArtifact in Test := false

scalacOptions ++= Seq("-feature",
  "-unchecked",
  "-deprecation",
  "-explaintypes",
  "-target:jvm-1.8",
  "-Xlint",
  "-Ywarn-extra-implicit",
  // Expand macros before declaring imports / vals "unused"
  "-Ywarn-macros:after",
  // TODO: Slowly enable more unused warnings as appropriate
  "-Ywarn-unused:_,-imports",
  "-Xfatal-warnings",
  "-Ypartial-unification",
  "-opt:l:method" // Enable optimizer in methods, no inlining without -opt-inline-from
)

scalacOptions in Test += "-Yrangepos"

scalacOptions in (Compile, doc) := DefaultOptions.scalac

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint", "-Werror")

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.10.1",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc41",
  "com.typesafe" % "config" % "1.3.0",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP9" % Test,
  "com.harrys.core" %% "scala-postgres-utils" % "1.0.0"
)

publishTo := {
  if (isSnapshot.value){
    Some("Artifactory Realm" at "https://harrys.artifactoryonline.com/harrys/harrys-snapshots;build.timestamp=" + java.time.Instant.now().toEpochMilli)
  } else {
    Some("Artifactory Realm" at "https://harrys.artifactoryonline.com/harrys/harrys-releases")
  }
}
