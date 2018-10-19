import abstraction.Service;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.Javalin;

public class echo_Server extends Service {
    public echo_Server(String URL) {
        super(URL);
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

    class DescriptionReveived{
        public DescriptionReveived(byte[] arr, String URL){
            this.message=arr;
            this.URLSender=URL;
        }
        public byte[] message;
        public String URLSender;
    }

    class SrvThr extends Service.ServerThread {

        public void setClient(Service.Client client){
            this.client = client;
        }

        Handler rootHandler=new Handler() {
            @Override
            public void handle(Context ctx) throws Exception {
                byte[] received = ctx.bodyAsBytes();
                System.out.println("ORIGINAL SENDER PATH==>"+ctx.req.getServletPath());
                String senderURL = "http://"+ ctx.host()+"/";
               // receive(new DescriptionReveived(received, senderURL));
            }
        };
        public void run(){
            Javalin app = Javalin.create().disableStartupBanner().start(getPortFromURL());
            app.post("/", rootHandler);
            app.get("/", rootHandler);

        }
    }
}
