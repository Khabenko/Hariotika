package API;

import java.io.IOException;
import java.net.URI;

import javax.websocket.*;

;


@ClientEndpoint
public class Client  {
    Session userSession = null;
    private MessageHandler messageHandler;
    URI uri = URI.create("ws://localhost:8081/");

    public Client() throws IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, uri);

    }

    @OnOpen
    public void onOpen(Session server) throws IOException {
        System.out.println("Open Connection ..." + server);
        this.userSession = server;
        this.userSession.setMaxIdleTimeout(600000);
    }
    @OnClose
    public void onClose(){
        System.out.println("Close Connection ...");
    }

    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
        System.out.println(message);
         //   return echoMsg;
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }

    public Session getUserSession() {
        return userSession;
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }




    public static interface MessageHandler {

        public void handleMessage(String message);
    }

}
