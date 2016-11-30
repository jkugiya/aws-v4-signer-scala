package jkugiya.awstools.signer.v4

private[v4] final case class CanonicalRequest(
    httpRequest: HttpRequest,
    headers: CanonicalHeaders,
    contentSha256: String
) {

  def mkString: String = {
    Array(
      httpRequest.method,
      httpRequest.path,
      httpRequest.query,
      headers.mkString,
      headers.names,
      contentSha256
    ).mkString("\n")
  }

}
