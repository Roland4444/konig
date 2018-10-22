package abstraction;
public abstract class ClientPrototype {
    public int port;

    public ClientPrototype(int port){

    }

    abstract  public void sendAsync(byte[] message, String uri);

}