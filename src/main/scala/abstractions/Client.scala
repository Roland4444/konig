package abstractions

import abstraction.Message
import com.softwaremill.sttp._

class Client(val port: Int)  extends ClientProto {
  def getPort: Int = {
    this.port
  }
  def sendAsync(message: Array[Byte], uri: String): Id[Response[String]] = {
    val msg = new Message(message, this.port)
    implicit val backend = HttpURLConnectionBackend()
    val firstRequest = sttp
      .post(com.softwaremill.sttp.Uri(uri))
      .body(msg.saveMessagetoByte(msg))
    firstRequest.send()
  }


}