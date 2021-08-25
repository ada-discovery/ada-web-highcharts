import PlayKeys._
import com.typesafe.config._
import com.typesafe.sbt.pgp.PgpKeys._

organization := "org.adada"

name := "ada-web-highcharts"

version := "0.9.0"

isSnapshot := false

scalaVersion := "2.11.12"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

resolvers ++= Seq(
  Resolver.mavenLocal
)

routesImport ++= Seq(
  "reactivemongo.bson.BSONObjectID",
  "org.ada.web.controllers.PathBindables._",
  "org.ada.web.controllers.QueryStringBinders._"
)

val adaVersion = "0.9.0"

libraryDependencies ++= Seq(
  "org.adada" %% "ada-web" % adaVersion,
  "org.adada" %% "ada-web" % adaVersion classifier "assets",
  "org.webjars" % "highcharts" % "6.2.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
).map(_.exclude("org.slf4j", "slf4j-log4j12"))

val jacksonVersion = "2.8.8"

// Because of Spark
dependencyOverrides ++= Set(
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % jacksonVersion,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion
)

packagedArtifacts in publishLocal := {
  val artifacts: Map[sbt.Artifact, java.io.File] = (packagedArtifacts in publishLocal).value
  val assets: java.io.File = (playPackageAssets in Compile).value
  artifacts + (Artifact(moduleName.value, "jar", "jar", "assets") -> assets)
}

signedArtifacts := {
  val artifacts: Map[sbt.Artifact, java.io.File] = signedArtifacts.value
  val assets: java.io.File = (playPackageAssets in Compile).value
  artifacts ++ Seq(
    Artifact(moduleName.value, "jar", "jar",     "assets") -> assets
    //    Artifact(moduleName.value, "jar", "jar.asc", "assets") -> new java.io.File(assets.getAbsolutePath + ".asc")  // manually sign assets.jar, uncomment, and republish
  )
}

// remove the custom conf from the produced jar
mappings in (Compile, packageBin) ~= { _.filter(!_._1.getName.endsWith("custom.conf")) }

// Asset stages
pipelineStages in Assets := Seq(uglify, digest, gzip)

includeFilter in digest := (includeFilter in digest).value && new SimpleFileFilter(f => f.getPath.contains("public/"))

excludeFilter in gzip := (excludeFilter in gzip).value || new SimpleFileFilter(file => new File(file.getAbsolutePath + ".gz").exists)

// POM settings for Sonatype
homepage := Some(url("https://ada-discovery.github.io"))

publishMavenStyle := true

scmInfo := Some(ScmInfo(url("https://github.com/ada-discovery/ada-web-highcharts"), "scm:git@github.com:ada-discovery/ada-web-highcharts.git"))

developers := List(
  Developer("bnd", "Peter Banda", "peter.banda@protonmail.com", url("https://peterbanda.net"))
)

licenses ++= Seq(
  "Creative Commons Attribution-NonCommercial 3.0" -> url("http://creativecommons.org/licenses/by-nc/3.0"),
  "Highcharts" -> url("https://shop.highsoft.com")
)

publishTo  := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)