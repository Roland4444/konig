import abstraction.*;
import io.javalin.Context;
import io.javalin.Handler;
import io.javalin.Javalin;

import java.io.*;

public class ebs_service extends Service {
    public ebs_service(String URL) {
        super(URL);
    }
    @Override
    public void receive(Object input) throws IOException {
        DescriptionReveived desc = (DescriptionReveived) input;
        VoiceFile voice = VoiceFile.restoreVoiceFile(desc.message);
        byte[] filecont = voice.content;
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(voice.name));
        bos.write(voice.content);
        bos.close();
        new File(voice.name).delete();
        System.out.println("Just received==>"+new String(desc.message));
        System.out.println("FROM"+ desc.URLSender);

    }
    @Override
    public void  setupServer(){
        Thread thSrv = new SrvThr();
        ((ServerThread) thSrv).setClient(this.Client);
        thSrv.run();
    }

    class SrvThr extends ServerThread  {

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
