import abstraction.Service;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.Javalin;

import java.io.IOException;
import java.sql.SQLException;

public class ebs_service extends Service {
    public ebs_service(String URL) {
        super(URL);
    }
    public String responce;
    public int lenInput;
    @Override
    public void receive(Object input) {
        responce="received";
        String casted  = (String) input;
        lenInput = casted.length();
        System.out.println(casted.length());
    }

    public int getPortFromURL(){
        return Integer.parseInt(this.URL.substring(this.URL.lastIndexOf(":")+1, this.URL.length()-1));
    }

    @Override
    public void  setupServer(){
        Thread thSrv = new ServerThread();
        thSrv.run();
    }

    class ServerThread extends Thread{
        Handler rootHandler=new Handler() {
            @Override
            public void handle(Context ctx) throws Exception {
                ctx.html("yo");
            }
        };
        public void run(){
            Javalin app = Javalin.create().start(getPortFromURL());
            app.post("/", rootHandler);
            app.get("/", rootHandler);

        }
    }
}
