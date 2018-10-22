package services

import abstractions.{ClientProto, Service}


class ebs_service(val URL: String) extends Service {
  override var Client: ClientProto

  override def setupClient(cp: ClientProto): Unit = {
    this.Client=new _root_.Client(getPortFromURL(URL))
  }

  override val thSrv: ServerThread = _

  override protected def setupServer(): Unit = ???

  override def receive(input: Any): Unit = ???

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
}



