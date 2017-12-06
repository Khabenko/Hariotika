package API;



import com.badlogic.gdx.Gdx;


import java.io.IOException;

import javax.websocket.DeploymentException;




public class Reconect extends Thread{
    public static Client client;
    public Reconect() {
       // Gdx.app.log("HariotikaLogsInfo", "ReconectedLoaded"+client.getUri());
        try {
            client = new Client();
            client.loginRead();
            client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
         //   finalize();
        } catch (IOException e) {
          //  e.printStackTrace();
            System.out.println("Переподключение");
        } catch (DeploymentException e) {
            Gdx.app.error("Hariotika Conection", "my error message", e);
            // e.printStackTrace();
            System.out.println("Переподключение");
            Gdx.app.error("Hariotika Conection", "my error message", e);

        }
    }

    @Override
    public void run() {
        while (true){
            if (getClient()== null){
                try {
                    client = new Client();
                    client.loginRead();
                    client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
                    Thread.sleep(1000);
                    Gdx.app.log("HariotikaLogsInfo", "Try Reconected "+client.getUri());
                } catch (IOException e) {
                   // e.printStackTrace();
                    System.out.println("Переподключение");
                    Gdx.app.error("Hariotika Conection", "my error message", e);
                } catch (DeploymentException e) {
                  //  e.printStackTrace();
                    System.out.println("Переподключение");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Gdx.app.error("Hariotika Conection", "my error message", e);
                }

            }

            if(getClient()!=null)
            if (!(getClient().getUserSession().isOpen()))
            {
                Gdx.app.log("HariotikaLogsInfo", "Try Reconected "+client.getUri());
                System.out.println(String.valueOf(getClient().getUserSession().isOpen()));
                try {
                    setClient(new Client());
                    client.loginRead();
                    client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
                    Thread.sleep(1000);
                    System.out.println("-----------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DeploymentException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Reconect.client = client;
    }
}
