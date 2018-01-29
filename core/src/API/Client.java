package API;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.websocket.*;

import Domain.Battle;
import Domain.Character;



@ClientEndpoint
public class Client  {


    public static boolean load = false;
    public Properties prop;
    private String login;
    private String pass;
    static Session userSession;
    static Gson gson = new Gson();
    public static Character character = new Character();
    public static Battle battle;
    public WebSocketContainer container;
    private HariotikaMessage hariotikaMessage;


    private MessageHandler messageHandler;
             //  URI uri = URI.create("ws://localhost:8081/");
             URI uri = URI.create("ws://64.250.115.155");
          //URI uri = URI.create("ws://10.0.2.2:8081/");




    protected Client() throws IOException, DeploymentException {
        Gdx.app.log("HariotikaLogsInfo", "Conection to  "+uri);
        container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, uri);
        loginRead();
        hariotikaMessage = new HariotikaMessage(Command.Login,WsCode.Authorithation,login,pass);
        sendMessage(gson.toJson(hariotikaMessage));


    }

    @OnOpen
    public void onOpen(Session server) throws IOException {
        System.out.println("Open Connection ..." + server);
        this.userSession = server;
        this.userSession.setMaxIdleTimeout(30000);

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
       //  Gdx.app.log("HariotikaLogsInfo", "Server sended  "+message);

        parsingHariotikaMessage(message);

    }

    @OnError
    public void onError(Throwable e){
        System.out.println("Error Connection ...");

    }

    public Session getUserSession() {
        return userSession;
    }
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
    public void sendMessage(String message) {
        synchronized (userSession) {
            userSession.getMessageHandlers().clear();
            this.userSession.getAsyncRemote().sendText(message);
        }
    }
    public static interface MessageHandler {

        public void handleMessage(String message);
    }


    public void loginWrite () {
        //Вычитываемы проперти log i pass
        //Вызываем конкшен для получения карты характеристик
        //Есди проперти пустые - сохраняем login который вернул нам сервре.
        Preferences prefs = Gdx.app.getPreferences("loginprop");
        try {
            prefs.putString("login", login);
            prefs.putString("password", pass);
            prefs.flush();
            Gdx.app.log("HRWrite", "Login "+ login);
            Gdx.app.log("HRWrite", "Pass "+ pass);
            Gdx.app.log("HRWrite", "Path config ");
            // save properties to project root folder
        } catch (Exception io) {
            io.printStackTrace();
        }
    }
    public void loginRead() {
        Gdx.app.log("HRRead", "Login Read started");
        //Вычитываемы проперти log i pass
        //Вызываем конкшен для получения карты характеристик
        //Есди проперти пустые - сохраняем login который вернул нам сервре.
        Preferences prefs ;
        try {
                prefs = Gdx.app.getPreferences("loginprop");
                this.login = prefs.getString("login", "null");
                this.pass = prefs.getString("password", "null");
            // get the property value and print it out
            Gdx.app.log("HRRead", "Login "+ login);
            Gdx.app.log("HRRead", "Pass "+ pass);
            Gdx.app.log("HRRead", "Path config ");
        } catch (Exception ex) {
            Gdx.app.error("HRRead", ex.toString());
            ex.printStackTrace();
        }
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



   private void parsingHariotikaMessage(String message){
        try {
            hariotikaMessage = gson.fromJson(message,HariotikaMessage.class);

        switch (hariotikaMessage.getCommand()){
            case Login: commandLoginCode(hariotikaMessage);
                break;
            case Battle: commandBattleCode(hariotikaMessage);
                Gdx.app.log("HariotikaLogsInfo", "Server sended  "+message);
                break;
            default:
                 Gdx.app.error("Invalid command","Server sended "+message);
                break;
        }
        }
        catch (Exception e){
            Gdx.app.error("Error convert message from json ",message);
        }
   }


   private void commandLoginCode(HariotikaMessage message){
       load =true;
       switch (message.getCode()){
           case Success:
               character = message.getCharacter();
               break;
           case Reject:
               Gdx.app.error("Wrong login or password ", pass);
               break;
           case New:
               this.setLogin(message.getCharacter().getName());
               this.setPass("null");
               loginWrite();
               break;
           default:
               Gdx.app.error("Invalid command","Server sended "+message);
               break;
       }

   }

    private void commandBattleCode(HariotikaMessage message){

        switch (message.getCode()){
            case RegistrationToBattle:
                break;
            case CancelRegistrationToBattle:
                break;
            case UpdateBattle:
                setBattle(message.getBattle());
                if (getBattle().getPlayer1().getName().equals(Client.character.getName())){
                    character = getBattle().getPlayer1();
                } else if (getBattle().getPlayer2().getName().equals(Client.character.getName())){
                    character = getBattle().getPlayer2();
                }
                break;
            case Success:
                Gdx.app.log("Command Battle Code ",message.getCode().toString());
                break;
            case Reject:
                Gdx.app.log("Command Battle Code ",message.getCode().toString());
                break;
            case UpdateTimer:
                battle.setTimer(message.getTimer());
                break;
            default:
                System.out.println("Invalid Command Battle Code "+message.getCode());
                break;
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
        System.out.println("Обновился батл");
        Client.battle = battle;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }



}
