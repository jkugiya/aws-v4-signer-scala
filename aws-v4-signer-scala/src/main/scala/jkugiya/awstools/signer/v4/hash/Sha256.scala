package jkugiya.awstools.signer.v4.hash

import java.nio.charset.Charset
import java.security.MessageDigest

import jkugiya.awstools.signer.v4.SigningException

import scala.util.{ Failure, Success, Try }

private[v4] object Sha256 {

  final private[this] val HexDigits =
    (('0' to '9') ++ ('a' to 'f')).toArray

  private[this] def byte2Hex(bytes: Array[Byte]): String = {
    val sb = StringBuilder.newBuilder
    bytes.foreach { b =>
      sb.append(HexDigits((b >> 4) & 0xf))
        .append(HexDigits(b & 0xf))
    }
    sb.toString()
  }

  def encode(value: String, charset: Charset): String = Try {
    val md = MessageDigest.getInstance("SHA-256")
    val bytes = value.getBytes(charset)
    md.update(bytes)
    val length = md.getDigestLength // TODO is this required?
    byte2Hex(md.digest())
  } match {
    case Success(digest) => digest
    case Failure(t) => throw new SigningException(t)
  }
}
