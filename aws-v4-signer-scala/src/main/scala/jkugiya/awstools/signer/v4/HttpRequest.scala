package jkugiya.awstools.signer.v4

import java.net.URI

case class HttpRequest(method: String, uri: URI) {

  val path =
    if (uri.getPath.isEmpty) "/"
    else uri.getPath

  val query =
    Option(uri.getQuery).getOrElse("")
}
