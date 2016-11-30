package jkugiya.awstools.signer.v4.credentials

import jkugiya.awstools.signer.v4.SigningException

private[v4] object AwsCredentialsProviderChain {
  val AccessKeyEnvVar = "AWS_ACCESS_KEY"
  val SecretKeyEnvVar = "AWS_SECRET_KEY"
  val AccessKeySystemProperty = "aws.accessKeyId"
  val SecretKeySystemProperty = "aws.secretKeyId"

  private[this] def createCredentials(maybeKey: Option[String], maybeValue: Option[String]) = {
    for {
      key <- maybeKey
      value <- maybeValue
    } yield AwsCredentials(key, value)
  }

  private[this] def environmentCredential(implicit resolver: EnvironmentVarResolver): Option[AwsCredentials] = {
    createCredentials(resolver.get(AccessKeyEnvVar), resolver.get(SecretKeyEnvVar))
  }

  private[this] def systemPropertyCredential: Option[AwsCredentials] = {
    createCredentials(sys.props.get(AccessKeySystemProperty), sys.props.get(SecretKeySystemProperty))
  }

  def credentials(implicit resolver: EnvironmentVarResolver) =
    environmentCredential
      .orElse(systemPropertyCredential)
      .getOrElse(throw new SigningException("no AWS credentials were provided"))
}

trait EnvironmentVarResolver {
  def get(key: String): Option[String]
}

object EnvironmentVarResolver {
  implicit val resolver: EnvironmentVarResolver = new EnvironmentVarResolver {
    override def get(key: String): Option[String] = sys.env.get(key)
  }
}