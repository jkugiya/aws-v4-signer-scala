package jkugiya.awstools.signer.v4

import scala.collection.immutable.TreeMap
import scala.collection.mutable

private[v4] final case class CanonicalHeaders private (private val headers: TreeMap[String, Vector[String]]) {

  val names: String = {
    val sb =
      headers.keySet
      .foldLeft(mutable.StringBuilder.newBuilder)((acc, v) => acc.append(v.toLowerCase()).append(';'))
    sb.deleteCharAt(sb.lastIndexOf(";"))
    sb.mkString
  }
  val mkString: String = {
    val sb = mutable.StringBuilder.newBuilder
    for {
      (key, values) <- headers
      value <- values
    } yield {
      sb.append(key.toLowerCase())
        .append(':')
        .append(value)
        .append('\n')
    }
    sb.toString()
  }

  def apply(key: String): Vector[String] = headers(key)

  def get(key: String): Option[Vector[String]] = headers.get(key)

}

object CanonicalHeaders {

  def apply(headers: Header*): CanonicalHeaders = {
    // HTTP header keys are case insensitive
    implicit val orderingForKey = new Ordering[String] {
      override def compare(x: String, y: String): Int =
        x.toLowerCase().compareTo(y.toLowerCase())
    }
    val internalMap =
    headers.foldLeft(TreeMap.empty[String, Vector[String]]) {
      case (acc, Header(k, v)) =>
        acc.get(k).fold {
          acc + (k -> Vector(v))
        } { values =>
          acc + (k -> (values :+ v))
        }
    }
    CanonicalHeaders(internalMap)
  }

}
