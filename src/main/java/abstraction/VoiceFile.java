package abstraction;

import java.io.*;

public class VoiceFile implements Serializable {
    public VoiceFile(byte[] arr, String name) {
        this.content = arr;
        this.name = name;
    }

    public byte[] content;
    public String name;

    public static VoiceFile restoreVoiceFile(byte[] input) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(input);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return (VoiceFile) o;
    }

    public static byte[] saveVoiceFile(VoiceFile event) {
        byte[] Result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(event);
            out.flush();
            Result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return Result;
    }
}