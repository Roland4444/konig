import abstraction.Client;
import abstraction.DescriptionReveived;
import abstraction.Message;
import abstraction.Service;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.Javalin;

public class echo_Actor extends Service {
    public echo_Actor(String URL) {
        super(URL);
        this.Client.port=getPortFromURL();
    }
    @Override
    public void receive(Object input) {
        DescriptionReveived desc = (DescriptionReveived) input;
        System.out.println("Just received==>"+new String(desc.message));
        System.out.println("Sending back... to "+ desc.URLSender);
        Client.sendAsync(desc.message, desc.URLSender);
    }

    public int getPortFromURL(){
        return Integer.parseInt(this.URL.substring(this.URL.lastIndexOf(":")+1, this.URL.length()-1));
    }

    @Override
    public void  setupServer(){
        Thread thSrv = new SrvThr();
        ((Service.ServerThread) thSrv).setClient(this.Client);
        thSrv.run();
    }



    class SrvThr extends Service.ServerThread {

        public void setClient(abstraction.Client client){
            this.client = client;
        }

        Handler rootHandler=new Handler() {
            @Override
            public void handle(Context ctx) throws Exception {
                byte[] received = ctx.bodyAsBytes();
                String senderURL = "http://"+ ctx.ip()+":"+Message.restoreMessage(received).port+"/";
                System.out.println("Original=>"+senderURL);
                receive(new DescriptionReveived(Message.restoreMessage(received).message, senderURL));
            }
        };
        public void run(){
            Javalin app = Javalin.create().disableStartupBanner().start(getPortFromURL());
            app.post("/", rootHandler);
            app.get("/", rootHandler);

        }
    }
}
