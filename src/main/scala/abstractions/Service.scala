package abstractions

import java.io.IOException

import abstraction.Message

trait Service {
  val URL: String

  var Client: ClientProto
  def setupClient(cp: ClientProto): Unit

  var thSrv:ServerThread
  protected def setupServer(): Unit

  def getPortFromURL: Int = this.URL.substring(this.URL.lastIndexOf(":") + 1, this.URL.length - 1).toInt
  def getPortFromURL(url: String): Int = url.substring(url.lastIndexOf(":") + 1, url.length - 1).toInt

  def receive(input: Any): Unit

}

trait  ServerThread extends Thread {
  var port: Int
  var msg = new Message("".getBytes, 0)
  var client: ClientProto = null
  def setPort(port: Int)
  def setClient(client: ClientProto)
}
