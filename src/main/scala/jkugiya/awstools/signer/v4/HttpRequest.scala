package jkugiya.awstools.signer.v4

import java.net.{ URI, URLEncoder }

case class HttpRequest(method: String, uri: URI) {

  val path =
    if (uri.getPath.isEmpty) "/"
    else {
      val path =
        uri.getPath.substring(1, uri.getPath.length)
          .split("/")
          .map(URLEncoder.encode(_, "UTF-8").replaceAll("\\*", "%2A"))
          .mkString("/")
      "/" + path
    }

  val query =
    Option(uri.getQuery).getOrElse("")
}
