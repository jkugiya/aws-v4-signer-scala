package jkugiya.awstools.signer.v4

import java.net.URI

import org.scalatest.FlatSpec

class CanonicalRequestTest extends FlatSpec {
  val EXPECTED = "PUT\n" +
    "/-/vaults/examplevault\n" +
    "\n" +
    "host:glacier.us-east-1.amazonaws.com\n" +
    "x-amz-date:20120525T002453Z\n" +
    "x-amz-glacier-version:2012-06-01\n" +
    "\n" +
    "host;x-amz-date;x-amz-glacier-version\n" +
    "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

  "it" should "get canonical request" in {
    val request = HttpRequest("PUT", new URI("https://glacier.us-east-1.amazonaws.com/-/vaults/examplevault"))
    val headers = CanonicalHeaders(
      Header("Host", "glacier.us-east-1.amazonaws.com"),
      Header("x-amz-date", "20120525T002453Z"),
      Header("x-amz-glacier-version", "2012-06-01")
    )
    val hash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    assert(CanonicalRequest(request, headers, hash).mkString == EXPECTED)
  }
}
