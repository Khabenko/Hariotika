package API;

import java.io.IOException;

import javax.websocket.DeploymentException;

/**
 * Created by Maka on 24.10.2017.
 */

public class Main {
    public static void main(String[] args)
    {
        try {
            Client client = new Client();
            client.sendMessage("ANDROID");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }
}