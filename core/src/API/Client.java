package API;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Properties;

import javax.websocket.*;

import Domain.Battle;
import Domain.Character;
import Domain.GlobalUpdate;


@ClientEndpoint
public class Client  {

    public static boolean load = false;
    Properties prop;
    static String login;
    static String pass;
    static Session userSession = null;
    static Gson gson = new Gson();
    public static Character character = new Character();
    public static Battle battle;

    private MessageHandler messageHandler;
             URI uri = URI.create("ws://localhost:8081/");
          //       URI uri = URI.create("ws://64.250.115.155");
        //        URI uri = URI.create("ws://10.0.2.2:8081/");

    public Client() throws IOException, DeploymentException {
         Gdx.app.log("HariotikaLogsInfo", "Reconected to  "+uri);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, uri);

    }

    @OnOpen
    public void onOpen(Session server) throws IOException {
        System.out.println("Open Connection ..." + server);
        this.userSession = server;
        this.userSession.setMaxIdleTimeout(10000);


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
        Gdx.app.log("HariotikaLogsInfo", "Server sended  "+message);
     //   System.out.println(message);
           //loginWrite(message,null);
        parsingMessage(message);

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


    public void loginWrite () {

        //Вычитываемы проперти log i pass
        //Вызываем конкшен для получения карты характеристик
        //Есди проперти пустые - сохраняем login который вернул нам сервре.

        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("config.properties");
            // set the properties value
            prop.setProperty("login", login);
            prop.setProperty("password", pass);
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public Properties loginRead() {

        //Вычитываемы проперти log i pass
        //Вызываем конкшен для получения карты характеристик
        //Есди проперти пустые - сохраняем login который вернул нам сервре.
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");
            // load a properties file
            prop.load(input);
            this.login = prop.getProperty("login");
            this.pass = prop.getProperty("password");
            // get the property value and print it out
            System.out.println(prop.getProperty("login"));
            System.out.println(prop.getProperty("password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public  void parsingMessage(String message)
    {
        String[] comand = message.split("#");
        if (comand[0].equals("login"))
        {
            load =true;
            if (comand[1].equals("1")) {
                character = gson.fromJson(comand[2], Character.class);
               // System.out.printf("Тут нам прислали данные по чару "+character.getName());

            }
            else if (comand[1].equals("2"))
                System.out.printf("Неверный логин и пароль");
            else {
                this.setLogin(comand[1]);
                this.setPass("null");
                loginWrite();
            }
        }

        if (comand[0].equals("Battle")){
            System.out.println(message);
            System.out.println(gson.fromJson(comand[1],Battle.class).getPlayer1().getHP());
            setBattle(gson.fromJson(comand[1],Battle.class));
            System.out.println("Прислали батл"+comand[1]);

            if (getBattle()!=null)
                System.out.println(battle.getPlayer1().getHP()+"++++++++++++++++++++++++++++++++++++++"+getBattle().getPlayer1().getName().equals(Client.character.getName()));
                System.out.println(battle.getPlayer2().getHP()+"++++++++++++++++++++++++++++++++++++++"+getBattle().getPlayer2().getName().equals(Client.character.getName()));
                if (getBattle().getPlayer1().getName().equals(Client.character.getName())){
                    character = getBattle().getPlayer1();


                }
                else if (getBattle().getPlayer2().getName().equals(Client.character.getName())){
                    character = getBattle().getPlayer2();
                }

        }


    }


    public  Character getCharacter() {
        return character;
    }

    public  void setCharacter(Character character) {
        Client.character = character;
    }

    public static Battle getBattle() {
        return battle;
    }

    public static void setBattle(Battle battle) {
        System.out.println("Засетл батл");
        Client.battle = battle;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
