package jkugiya.awstools.signer.v4.hash

import org.scalatest.{FeatureSpec, FlatSpec}

class Base16Test extends FlatSpec {

  "Base16#encode" should "encode utf-8 string" in {
    val data = "0123456abcdef23abc"
    assert(Base16.encode(data) == "303132333435366162636465663233616263")
  }

  "Base16#encode" should "encode bytes" in {
    val data = "0123456abcdef23abc".getBytes("UTF-8")
    assert(Base16.encode(data) == "303132333435366162636465663233616263")
  }
}
