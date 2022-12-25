package jkugiya.awstools.signer.v4.hash

private[v4] object Base16 {
  final private[this] val EncTab =
    (('0' to '9') ++ ('A' to 'F')).toArray

  def encode(data: Array[Byte]): String = {
    val sb = StringBuilder.newBuilder
    data foreach { d =>
      sb.append(EncTab((d & 0xf0) >> 4))
      sb.append(EncTab(d & 0x0f))
    }
    sb.toString()
  }

  def encode(data: String): String = encode(data.getBytes("UTF-8"))
}
