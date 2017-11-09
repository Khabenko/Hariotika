package API;



import java.io.IOException;

import javax.websocket.DeploymentException;




public class Reconect extends Thread{
    public static Client client;
    public Reconect() {
        try {
            client = new Client();
            client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            if (getClient()==null){
                try {
                    client = new Client();
                    client.sendMessage("login#" + client.getLogin() + "#" + client.getPass());
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DeploymentException e) {
                    e.printStackTrace();
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