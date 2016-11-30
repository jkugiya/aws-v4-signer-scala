package jkugiya.awstools.signer.v4

import java.net.URI

import jkugiya.awstools.signer.v4.credentials.AwsCredentials
import org.scalatest.FlatSpec

class SignerTest extends FlatSpec {
  val ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE"
  val SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"

  "it" should "sign request" in {
    val hash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

    val signer = Signer(
      region = "us-east-1",
      service = "glacier",
      credentials = AwsCredentials(ACCESS_KEY, SECRET_KEY)
    )

    val request = HttpRequest("PUT", new URI("https://glacier.us-east-1.amazonaws.com/-/vaults/examplevault"))

    val signature = signer.sign(request, hash,
      Header("Host", "glacier.us-east-1.amazonaws.com"),
      Header("x-amz-date", "20120525T002453Z"),
      Header("x-amz-glacier-version", "2012-06-01"))

    val expected = "AWS4-HMAC-SHA256 Credential=AKIAIOSFODNN7EXAMPLE/20120525/us-east-1/glacier/aws4_request, " +
      "SignedHeaders=host;x-amz-date;x-amz-glacier-version, Signature=3ce5b2f2fffac9262b4da9256f8d086b4aaf42eba5f111c21681a65a127b7c2a"
    assert(signature == expected)
  }

  "it" should "sign request with query param" in {
    val hash = "79da47e784b181ae04e5b5119fcc953d944acad5e0583fa0899d554a79eb77eb"

    val signer = Signer(
      region = "us-east-1",
      service = "glacier",
      credentials = AwsCredentials(ACCESS_KEY, SECRET_KEY)
    )

    val request = HttpRequest("PUT", new URI("https://glacier.us-east-1.amazonaws.com/-/vaults/dev2/multipart-uploads/j3eqysOZoNF3UiEoN3k_b6bdRGGdzgEfsLoUyZhMIwKRMuDLEYRw2nlCh8QXQ_dzqQMxrgFtmZjatxbFIZ9HpnIUi93B"))

    val signature = signer.sign(request, hash,
      Header("Accept", "application/json"),
      Header("Content-Length", "1049350"),
      Header("Content-Range", "bytes 0-1049349/*"),
      Header("Content-Type", "binary/octet-stream"),
      Header("Host", "glacier.us-east-1.amazonaws.com"),
      Header("user-agent", "aws-sdk-java/1.9.26 Mac_OS_X/10.10.3 Java_HotSpot(TM)_64-Bit_Server_VM/25.0-b70/1.8.0"),
      Header("x-amz-content-sha256", hash),
      Header("X-Amz-Date", "20150424T222200Z"),
      Header("x-amz-glacier-version", "2012-06-01"),
      Header("x-amz-sha256-tree-hash", "05c734c3f16b23358bb49c959d1420edac9f28ee844bf9b0580754c0f540acd8"),
      Header("X-Amz-Target", "Glacier.UploadMultipartPart"))

    val expected = "AWS4-HMAC-SHA256 Credential=AKIAIOSFODNN7EXAMPLE/20150424/us-east-1/glacier/aws4_request, " +
      "SignedHeaders=accept;content-length;content-range;content-type;host;user-agent;x-amz-content-sha256;x-amz-date;x-amz-glacier-version;x-amz-sha256-tree-hash;x-amz-target, " +
      "Signature=3ee337a197d3b15e719fd21acf378ef2d62f73159ff3c47fc0204e27e5ee9fb1"

    assert(signature == expected)
  }
}
