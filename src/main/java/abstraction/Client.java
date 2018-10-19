package abstraction;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Client {
    public HttpClient httpClient;
    public int port;

    public Client(int port){
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        this.port=port;
    }

    public CompletableFuture<?> sendAsync(byte[] message, String uri) {
        Message msg = new Message(message, this.port);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofByteArray(Message.saveMessagetoByte(msg)))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept(response -> {
            System.out.println(new String(response.body()));
        });
    }


}