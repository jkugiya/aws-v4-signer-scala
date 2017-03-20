name := "aws-v4-signer-scala"
organization := "com.github.jkugiya"

version := "0.12"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8", "2.12.0")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.mockito" % "mockito-core" % "2.2.26" % "test"
)
