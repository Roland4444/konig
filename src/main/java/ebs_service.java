import abstraction.Service;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.Javalin;
public class ebs_service extends Service {
    public ebs_service(String URL) {
        super(URL);
    }
    @Override
    public void receive(Object input) {
        byte[] arr = (byte[]) input;
        System.out.println("Just received==>"+new String(arr));
    }

    public int getPortFromURL(){
        return Integer.parseInt(this.URL.substring(this.URL.lastIndexOf(":")+1, this.URL.length()-1));
    }

    @Override
    public void  setupServer(){
        Thread thSrv = new SrvThr();
        ((ServerThread) thSrv).setClient(this.Client);
        thSrv.run();
    }

    class SrvThr extends ServerThread  {

        public void setClient(Client client){
            this.client = client;
        }

        Handler rootHandler=new Handler() {
            @Override
            public void handle(Context ctx) throws Exception {
                byte[] received = ctx.bodyAsBytes();
                receive(received);

            }
        };
        public void run(){
            Javalin app = Javalin.create().disableStartupBanner().start(getPortFromURL());
            app.post("/", rootHandler);
            app.get("/", rootHandler);

        }
    }
}
