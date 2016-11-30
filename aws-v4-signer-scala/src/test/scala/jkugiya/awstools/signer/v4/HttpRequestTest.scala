package jkugiya.awstools.signer.v4

import java.net.URI

import org.scalatest.FlatSpec

class HttpRequestTest extends FlatSpec {

  "path" should "return slash if uri is empty" in {
    val request = HttpRequest("GET", new URI(""))
    assert(request.path == "/")
  }
  "path" should "return proper path" in {
    val request = HttpRequest("GET", new URI("http://localhost/test"))
    assert(request.path == "/test")
  }
  "query" should "return empty if query is not given" in {
    val request = HttpRequest("GET", new URI("http://localhost/test"))
    assert(request.query == "")
  }
  "query" should "return query parameters" in {
    val request = HttpRequest("GET", new URI("http://localhost/test?test=one&hello=world"))
    assert(request.query == "test=one&hello=world")
  }
}
