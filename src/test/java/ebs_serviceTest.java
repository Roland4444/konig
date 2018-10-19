import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class ebs_serviceTest {

    @Test
    public void receive() throws IOException, InterruptedException {
        ebs_service receiver = new ebs_service("http://127.0.0.1:7777/");
        ebs_service sender = new ebs_service("http://127.0.0.1:8888/");
        Thread.sleep(2000);
        assertNotEquals(null, sender.sendAsync("hello".getBytes(), receiver.URL));
        HttpResponse res = sender.sendSync("hello".getBytes(), receiver.URL);
        System.out.println("\n\n\n"+res.statusCode());
        while(true){

        }

    }

    @Test
    public void getPortFromURL() {
        String URL ="http://127.0.0.1:7777/";
        ebs_service receiver = new ebs_service("http://127.0.0.1:7777/");
        assertEquals(7777, receiver.getPortFromURL());
    }
}