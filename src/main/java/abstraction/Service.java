package abstraction;
public abstract class Service {
    public final String URL;
    public Service(String URL ){
        this.URL=URL;
        this.Client = new Client(getPortFromURL());
        setupServer();
    }
    public int getPortFromURL(){
        return Integer.parseInt(this.URL.substring(this.URL.lastIndexOf(":")+1, this.URL.length()-1));
    }
    public Client Client;
    protected abstract  void setupServer();
    public abstract void receive(Object input);

    public abstract class ServerThread extends Thread{
        public Client client;
        public abstract void setClient(Client client);
    }

}
