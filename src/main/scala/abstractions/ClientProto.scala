package abstractions

trait ClientProto {
  val port: Int
  def getPort: Int
  def sendAsync(message: Array[Byte], uri: String): Any

}
