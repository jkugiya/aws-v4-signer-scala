package jkugiya.awstools.signer.v4.hash

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import jkugiya.awstools.signer.v4.SigningException

import scala.util.{Failure, Success, Try}

private[v4] object HmacSha256 {

  def encode(key: Array[Byte], value: String): Array[Byte] = Try {
    val algorithm = "HmacSHA256"
    val mac = Mac.getInstance(algorithm)
    val signingKey = new SecretKeySpec(key, algorithm)
    mac.init(signingKey)
    mac.doFinal(value.getBytes("UTF-8"))
  } match {
    case Success(hash) => hash
    case Failure(t) => throw new SigningException(t)
  }
}
