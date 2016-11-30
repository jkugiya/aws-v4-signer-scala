package jkugiya.awstools.signer.v4.credentials

import org.mockito.{ArgumentMatchers, Matchers, Mockito}
import org.scalatest.{BeforeAndAfterEach, FlatSpec}
import org.scalatest.mockito.MockitoSugar

class AwsCredentialsProviderChainTest
  extends FlatSpec
    with BeforeAndAfterEach
    with MockitoSugar {

  implicit var environment: EnvironmentVarResolver = _

  override def beforeEach(): Unit = {
    environment = mock[EnvironmentVarResolver]
    Mockito
      .when(environment.get(ArgumentMatchers.any()))
      .thenReturn(None)
  }

  override def afterEach(): Unit = {
    System.clearProperty(AwsCredentialsProviderChain.AccessKeySystemProperty)
    System.clearProperty(AwsCredentialsProviderChain.SecretKeySystemProperty)
  }

  "it" should "get credential using system properties" in {
    System.setProperty(AwsCredentialsProviderChain.AccessKeySystemProperty, "access")
    System.setProperty(AwsCredentialsProviderChain.SecretKeySystemProperty, "secret")

    assert(AwsCredentialsProviderChain.credentials == AwsCredentials("access", "secret"))
  }

  "it" should "get credential using environment variables" in {
    Mockito
      .when(environment.get(AwsCredentialsProviderChain.AccessKeyEnvVar))
      .thenReturn(Some("env-access"))
    Mockito
      .when(environment.get(AwsCredentialsProviderChain.SecretKeyEnvVar))
      .thenReturn(Some("env-secret"))

    assert(AwsCredentialsProviderChain.credentials == AwsCredentials("env-access", "env-secret"))
  }
}
