package API;



import com.badlogic.gdx.Gdx;


import java.io.IOException;

import javax.websocket.DeploymentException;




public class Reconect extends Thread{
    public static Client client;
    public Reconect() {
        try {
            client = new Client();
            client.loginRead();
            client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
         //   finalize();
        } catch (IOException e) {
          //  e.printStackTrace();
            System.out.println("Переподключение");
        } catch (DeploymentException e) {
            Gdx.app.error("MyTag", "my error message", e);
            // e.printStackTrace();
            System.out.println("Переподключение");
            Gdx.app.error("MyTag", "my error message", e);

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

                } catch (IOException e) {
                   // e.printStackTrace();
                    System.out.println("Переподключение");
                } catch (DeploymentException e) {
                  //  e.printStackTrace();
                    System.out.println("Переподключение");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            if(getClient()!=null)
            if (!(getClient().getUserSession().isOpen()))
            {
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
