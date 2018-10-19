package abstraction;

import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class Service {
    public final String URL;
    public byte[] LastResponced;
    public Service(String URL ){
        this.URL=URL;
        setupServer();
    }
    public HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    protected abstract  void setupServer();





    public abstract void receive(Object input);
    public HttpResponse<?> sendSync(byte[] message, String URL) throws IOException, InterruptedException {
        HttpRequest binaryReq = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofByteArray(message))
                .build();
        return httpClient.send(binaryReq, HttpResponse.BodyHandlers.discarding());
    }

    public  CompletableFuture<byte[]> sendAsync(byte[] message,String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofByteArray(message))
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenApply(HttpResponse::body);
    }



}
