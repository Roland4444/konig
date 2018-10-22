package abstractions;

import org.junit.Test;

import java.io.FileOutputStream;

import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void saveMessagetoByte() {
        Message msg = new Message("hello".getBytes(), 7777);
        FileOutputStream fos = new FileOutputStream("")
    }
}