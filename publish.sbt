publishMavenStyle := true

sonatypeProfileName := "com.github.jkugiya"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/jkugiya/aws-v4-signer-scala</url>
  <licenses>
    <license>
    <name>Apatch 2.0</name>
    <url>https://www.apache.org/licenses/LICENSE-2.0</url>
    <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/jkugiya/aws-v4-signer-scala.git</url>
    <connection>scm:git:git@github.com:jkugiya/aws-v4-signer-scala.git</connection>
  </scm>
  <developers>
    <developer>
    <id>jkugiya</id>
    <name>Jiro Kugiya</name>
    <url>https://github.com/jkugiya</url>
  </developer>
</developers>)
