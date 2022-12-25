name := "aws-v4-signer-scala"
organization := "com.github.jkugiya"

version := "0.14"

scalaVersion := "2.13.10"
crossScalaVersions := Seq("2.11.8", "2.12.17", "2.13.10", "3.2.1")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.14" % "test",
  "org.mockito" % "mockito-core" % "4.10.0" % "test"
)
