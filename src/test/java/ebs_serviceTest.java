import abstraction.VoiceFile;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void TestPortInjection() throws InterruptedException {
        echo_Actor echo=new echo_Actor("http://127.0.0.1:15555/");
        ebs_service ebs = new ebs_service("http://127.0.0.1:11223/");
        assertEquals(11223, ebs.Client.port);
        assertEquals(15555, echo.Client.port);
        //ebs.Client.sendAsync("Hi".getBytes(), echo.URL);
        //Thread.sleep(1000);
    }

    @Test
    public void Echo() throws InterruptedException {
        echo_Actor echo=new echo_Actor("http://127.0.0.1:15555/");
        ebs_service ebs = new ebs_service("http://127.0.0.1:11223/");
        ebs.Client.sendAsync("Hi".getBytes(), echo.URL);
        Thread.sleep(1000);
    }
    @Test
    public void Echo2() throws InterruptedException {
        echo_Actor echo=new echo_Actor("http://127.0.0.1:15555/");
        ebs_service ebs = new ebs_service("http://127.0.0.1:11223/");
        for (int i =0; i<5000; i++){
            System.out.println(i);
            ebs.Client.sendAsync("Hi".getBytes(), echo.URL);
        }

        Thread.sleep(10000);
    }

    @Test
    public void receiveWavs() throws InterruptedException, IOException {
        String path = "/home/roland/Downloads/download/.build_l64/tests_data/";
        echo_Actor echo=new echo_Actor("http://127.0.0.1:15555/");
        ebs_service ebs = new ebs_service("http://127.0.0.1:11223/");

        Files.walk(Paths.get(path))
                .filter(Files::isRegularFile)
                .forEach(f -> {
                    try {
                        sendFileToEBS(echo,ebs, f.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        Thread.sleep(40000);
    }

    public void sendFileToEBS(echo_Actor echo, ebs_service ebs, String reference) throws IOException {
        VoiceFile vf = new VoiceFile(Files.readAllBytes(new File(reference).toPath()), new File(reference).getName());
        echo.Client.sendAsync(VoiceFile.saveVoiceFile(vf), ebs.URL);
    }

}