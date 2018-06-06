name := """scala-jooq-tables"""

organization := "com.harrys.core"

version := "1.6.7-SNAPSHOT"

scalaVersion := "2.12.5"

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
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.typesafe" % "config" % "1.3.3",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test,
  "com.harrys.core" %% "scala-postgres-utils" % "1.0.1-SNAPSHOT"
)

publishTo := {
  if (isSnapshot.value){
    Some("Artifactory Realm" at "https://harrys.artifactoryonline.com/harrys/harrys-snapshots;build.timestamp=" + java.time.Instant.now().toEpochMilli)
  } else {
    Some("Artifactory Realm" at "https://harrys.artifactoryonline.com/harrys/harrys-releases")
  }
}
