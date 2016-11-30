package jkugiya.awstools.signer.v4

import org.scalatest.FlatSpec

class CanonicalHeadersTest extends FlatSpec {

  "it" should "build canonical headers" in {
    val headers = CanonicalHeaders(
      Header("test", "one"),
        Header("test", "two"),
        Header("hello", "world")
    )
    assert(headers.names == "hello;test")
    assert(headers.mkString == "hello:world\ntest:one\ntest:two\n")
  }

  "apply.head" should "get first value" in {
    val headers = CanonicalHeaders(
      Header("test", "one"),
      Header("test", "two"),
      Header("hello", "world")
    )
    assert(headers("test").head == "one")
  }

  "get" should "return None if value not found" in {
    val headers = CanonicalHeaders(
      Header("test", "one"),
      Header("test", "two"),
      Header("hello", "world")
    )
    assert(headers.get("bad").isEmpty)
  }
}
