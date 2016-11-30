package jkugiya.awstools.signer.v4

private[v4] class SigningException(msg: String = null, cause: Throwable = null)
  extends RuntimeException(SigningException.message(msg, cause), cause) {

  def this(msg: String) = this(msg, null)

  def this(cause: Throwable) = this(null, cause)

}

private object SigningException {
  private def message(msg: String, cause: Throwable) =
    if (msg != null) msg
    else if (cause != null) cause.getMessage
    else null
}