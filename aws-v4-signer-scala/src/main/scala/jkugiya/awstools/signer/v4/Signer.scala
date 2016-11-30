package jkugiya.awstools.signer.v4

import java.nio.charset.Charset

import jkugiya.awstools.signer.v4.credentials.{AwsCredentials, AwsCredentialsProviderChain}
import jkugiya.awstools.signer.v4.hash.{Base16, HmacSha256, Sha256}

case class Signer(
  region: String,
  service: String,
  credentials: AwsCredentials = AwsCredentialsProviderChain.credentials) {

  import Signer._

  def sign(request: HttpRequest, contentSha256: String, headers: Header*): String = {
    val canonicalHeaders = CanonicalHeaders(headers: _*)
    val canonicalRequest = CanonicalRequest(request, canonicalHeaders, contentSha256)
    signInternal(region, service, canonicalRequest, credentials)
  }
}

object Signer {
  private[this] val AuthTag = "AWS4"
  private[this] val HeaderAmzDate = "X-Amz-Date"
  private[this] val TerminationString = "aws4_request"
  private[this] val Algorithm = AuthTag + "-HMAC-SHA256"

  private def signInternal(
    region: String,
    service: String,
    request: CanonicalRequest,
    credentials: AwsCredentials): String = {
    val headers = request.headers

    val date =
      headers
        .get(HeaderAmzDate)
        .getOrElse(throw new SigningException("headers missing '" + HeaderAmzDate + "' header"))
        .head
    val dateWithoutTimestamp = formatDateWithoutTimestamp(date)
    val credentialScope = buildCredentialScope(dateWithoutTimestamp, service, region)
    val hashedCanonicalRequest = Sha256.encode(request.mkString, Charset.forName("UTF-8"))
    val stringToSign = buildStringToSign(date, credentialScope, hashedCanonicalRequest)
    val signature = buildSignature(credentials.secretKey, dateWithoutTimestamp, stringToSign, service, region)
    buildAuthHeader(credentials.accessKey, credentialScope, request.headers.names, signature)
  }

  private[this] def formatDateWithoutTimestamp(date: String) = date.substring(0, 8)

  private[this] def buildCredentialScope(dateWithoutTimestamp: String, service: String, region: String) = {
    dateWithoutTimestamp + "/" + region + "/" + service + "/" + TerminationString
  }

  private[this] def buildStringToSign(date: String, credentialScope: String, hashedCanonicalRequest: String) = {
    Algorithm + "\n" + date + "\n" + credentialScope + "\n" + hashedCanonicalRequest
  }

  private[this] def buildSignature(
    secretKey: String,
    dateWithoutTimestamp: String,
    stringToSign: String,
    service: String,
    region: String) = {
    val kSecret = (AuthTag + secretKey).getBytes("UTF-8")
    val kDate = HmacSha256.encode(kSecret, dateWithoutTimestamp)
    val kRegion = HmacSha256.encode(kDate, region)
    val kService = HmacSha256.encode(kRegion, service)
    val kSigning = HmacSha256.encode(kService, TerminationString)
    Base16.encode(HmacSha256.encode(kSigning, stringToSign)).toLowerCase
  }

  private[this] def buildAuthHeader(accessKey: String, credentialScope: String, signedHeaders: String, signature: String) = {
    Algorithm + " " + "Credential=" + accessKey + "/" + credentialScope + ", " + "SignedHeaders=" + signedHeaders +
      ", " + "Signature=" + signature
  }
}