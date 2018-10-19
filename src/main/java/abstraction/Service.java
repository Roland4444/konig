package abstraction;
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
        this.Client = new Client();
        setupServer();
    }
    public Client Client;
    protected abstract  void setupServer();  


    public abstract class ServerThread extends Thread{
        public Client client;

        public abstract void setClient(Service.Client client);
    }
    
    public class Client {
        public HttpClient httpClient;
        public Client(){
            httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
        }

        public HttpResponse<?> sendSync(byte[] message, String URL) throws IOException, InterruptedException {
            HttpRequest binaryReq = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(message))
                    .build();
            return httpClient.send(binaryReq, HttpResponse.BodyHandlers.discarding());
        }

        public  CompletableFuture<?> sendAsync(byte[] message,String uri) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(message))
                    .build();
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept(response -> {
                System.out.println(new String(response.body()));
            });
        }
        
        public  byte[] getRawAnswer(byte[] message,String uri) {
            byte[] resp=null;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(message))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept(response -> {
                setBuff(response.body());
            });
            return resp;
        }
    }

    public abstract void receive(Object input);

    byte[] resp=null;
    void setBuff(byte[] input){
        this.resp=input;
    }






}
