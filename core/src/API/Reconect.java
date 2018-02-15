package API;



import com.badlogic.gdx.Gdx;


import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.io.WebSocketClientConnection;

import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;


public class Reconect extends Thread{

    public static Client client;

    @Override
    public void run() {
        while (true){
           getClient();

        }
    }

    public static Client getClient() {
        if (client == null || !(client.getUserSession().isOpen())) {
            try {
                client = new Client();
                Thread.sleep(1000);
            } catch (Exception e) {
                //e.printStackTrace();
                Gdx.app.log("HariotikaLogsInfo", "Try conection... ");
            }

        }
        return client;
    }



    public static void setClient(Client client) {
        Reconect.client = client;
    }
}
