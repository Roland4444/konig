import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.Assert.*;

public class ebs_serviceTest {

    @Test
    public void receive() throws IOException, InterruptedException {
        ebs_service receiver = new ebs_service("http://127.0.0.1:7777/");
        ebs_service sender = new ebs_service("http://127.0.0.1:8888/");
        assertNotEquals(null, sender.Client.sendAsync("hello".getBytes(), receiver.URL));
        Thread.sleep(1000);



    }

    @Test
    public void getPortFromURL() {
        String URL ="http://127.0.0.1:7777/";
        ebs_service receiver = new ebs_service("http://127.0.0.1:7777/");
        assertEquals(7777, receiver.getPortFromURL());
    }

    @Test
    public void Echo() throws InterruptedException {
        echo_Server echo=new echo_Server("http://127.0.0.1:7777/");
        ebs_service ebs = new ebs_service("http://127.0.0.1:8888/");
        ebs.Client.sendAsync("Hi".getBytes(), echo.URL);
        Thread.sleep(1000);
    }
}