package jkugiya.awstools.signer.v4.hash

import java.nio.charset.Charset

import org.scalatest.FlatSpec

class Sha256Test extends FlatSpec {

  val TEST = "PUT\n" +
    "/-/vaults/examplevault\n" +
    "\n" +
    "host:glacier.us-east-1.amazonaws.com\n" +
    "x-amz-date:20120525T002453Z\n" +
    "x-amz-glacier-version:2012-06-01\n" +
    "\n" +
    "host;x-amz-date;x-amz-glacier-version\n" +
    "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

  "Sha256#encode" should "get sha256" in {
    assert(Sha256.encode(TEST, Charset.forName("UTF-8")) ==
      "5f1da1a2d0feb614dd03d71e87928b8e449ac87614479332aced3a701f916743")
  }
}
