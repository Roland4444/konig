import java.net.URI

import abstraction.{ClientPrototype, Message}
import java.io.IOException

import com.softwaremill.sttp._

class Client extends ClientPrototype(var port: Int) {
  httpClient = HttpClient.newBuilder.version(HttpClient.Version.HTTP_2).build
  var httpClient: HttpClient = null

  def sendAsync(message: Array[Byte], uri: String): CompletableFuture[_] = {
    val msg = new Message(message, this.port)
    val request = HttpRequest.newBuilder.uri(URI.create(uri)).POST(HttpRequest.BodyPublishers.ofByteArray(Message.saveMessagetoByte(msg))).build
    httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray).thenAccept((response: HttpResponse[Array[Byte]]) => {
      def foo(response: HttpResponse[Array[Byte]]) = {
        System.out.println(new String(response.body))
      }

      foo(response)
    })
  }
}