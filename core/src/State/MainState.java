package State;

/**
 * Created by Maka on 18.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.gson.Gson;
import com.hariotika.Hariotika;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.websocket.DeploymentException;

import API.Client;
import API.Command;
import API.HariotikaMessage;
import API.Reconect;
import API.WsCode;
import Domain.AvatarWindow;
import Domain.FriendslistWindow;
import Domain.ReconectWindows;
import Domain.SearchBattleWindow;
import Domain.SpellWindow;

import static API.Client.character;
import static API.Client.getBattle;
import static API.Client.load;
import static API.Reconect.client;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;



public class MainState extends State {

    static Table status;
    Gson gson;
    Stage stage;
    SpriteBatch batch;
    //-------
    ImageButton avaButton;
    private Texture background;
    Skin skin2;
    Skin skin;
    TextureAtlas buttonAtlas;
    ImageButton.ImageButtonStyle avaButtonStyle;
    ImageButton.ImageButtonStyle imgButtonStyle;
    TextButton battleButton;
    TextButton backButton;
    static Label playerName;
    static ProgressBar health;
    static ProgressBar mana;
    static ProgressBar sp;
    HariotikaMessage hariotikaMessage;
    private boolean registrationInBattl =false;
    String locRoot = Gdx.files.getLocalStoragePath();
    private Texture playerAvatar;

      // String avatarUri = "http://localhost:8081/getAvatar/?name=";
    //String avatarUri = "http://10.0.2.2:8081/getAvatar/?name=";
   String avatarUri = "http://64.250.115.155/getAvatar/?name=";


    //  private ImageButton button = new ImageButton();


    public MainState(final StateManager sm) {
        super(sm);
        gson = new Gson(); //Передать дальше
        background = new Texture("darckbgr.png");
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin2 = new Skin(Gdx.files.internal("data/visui/uiskin.json"));
        //Создание анимированной кнопки
    /*
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("1.txt"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new ImageButton.ImageButtonStyle();
        textButtonStyle.imageUp = skin.getDrawable("b1");
        textButtonStyle.imageDown = skin.getDrawable("b2");
        textButtonStyle.checked = skin.getDrawable("b3");
        button = new ImageButton(textButtonStyle);
        button.setPosition(400,400);
        stage.addActor(button);
     */
        //-------- Кнопка аватарка
        buttonAtlas = new TextureAtlas(Gdx.files.internal("avatar/ava.txt"));
        skin.addRegions(buttonAtlas);
        avaButtonStyle = new ImageButton.ImageButtonStyle();
        avaButtonStyle.imageUp = skin.getDrawable("ava");
        avaButtonStyle.imageDown = skin.getDrawable("ava");
        ImageButton avaButton = new ImageButton(avaButtonStyle);
        avaButton.setSize(120,100);
        avaButton.setPosition(10,camera.viewportHeight/1.15f);
        stage.addActor(avaButton);
        //-----------------
        //--------Статусбар
        status = new Table(skin2);
        playerName = new Label("Player",skin2);

        status.add(playerName);
        status.row();

        status.add(new Label("HP", skin2));
        health = new ProgressBar(0, 100, 1, false, skin2);
        health.setValue(0);
        health.setColor(Color.FOREST);
        status.add(health).width(500);

        status.row();

        status.add(new Label("MP", skin2));
        mana = new ProgressBar(0, 100, 1, false, skin2);
        mana.setValue(24);
        mana.setColor(BLUE);
        status.add(mana).width(500);
        status.setPosition(420,510);

        status.row();

        status.add(new Label("SP", skin2));
        sp = new ProgressBar(0, 100, 1, false, skin2);
        sp.setValue(50);
        sp.setColor(Color.CHARTREUSE);
        status.add(sp).width(500);
        status.setPosition(400,camera.viewportHeight/1.08f);
        stage.addActor(status);
        //------------------

        //---------Спел панель
        SpellWindow spellWindow = new SpellWindow(skin2);
       // spellWindow.setPosition(200,30);
        spellWindow.setPosition(camera.viewportWidth/2,30);
      //  stage.addActor(spellWindow);

        //----------Панель друзей
        FriendslistWindow friendslistWindow = new FriendslistWindow(skin2);
        friendslistWindow.setPosition(50,camera.viewportHeight/10);
      //  stage.addActor(friendslistWindow);

        //----- Батл кнопка
        battleButton = new TextButton("Battle",skin2);
        battleButton.setPosition(camera.viewportWidth/20,camera.viewportHeight/10);
        battleButton.setSize(150,80);
        stage.addActor(battleButton);

        //----- Батл кнопка
        backButton = new TextButton("Back",skin2);
        backButton.setPosition(camera.viewportWidth-camera.viewportWidth*0.05f,camera.viewportHeight-camera.viewportWidth*0.05f);
        backButton.setSize(80,60);

/*
        ReconectWindows reconectWindows = new ReconectWindows(skin2);
        reconectWindows.setSize(200,200);
        reconectWindows.setPosition(400,400);
        reconectWindows.setBackground(new SpriteDrawable(new Sprite(background)));
        stage.addActor(reconectWindows);

*/
        AvatarWindow avatarWindow = new AvatarWindow(skin2);
        avatarWindow.setPosition(200,200);
      //  stage.addActor(avatarWindow);

        final SearchBattleWindow searchBattleWindow = new SearchBattleWindow(skin2,backButton);
        searchBattleWindow.setPosition(400,400);
        searchBattleWindow.setSize(400,300);







        while (character.getName() ==null){
         //Ждем что приде нам character
        }

            Gdx.app.log("Hariotika mainState", "Send to server avatar " + character.getName());
            HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
            Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(avatarUri + character.getName()).build();
            Gdx.net.sendHttpRequest(httpRequest, listener);





        avaButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Open PlayerState");
                CharState charState = new  CharState(sm,skin2);
                sm.set(charState);

            };
        });

        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 if (character.getHP()>0) {
                     hariotikaMessage = new HariotikaMessage(Command.Battle, WsCode.RegistrationToBattle);
                     client.sendMessage(gson.toJson(hariotikaMessage));
                     registrationInBattl = true;
                     stage.addActor(searchBattleWindow);

                 }
//                Gdx.app.log("Hariotika Main", "Registration To Battle");

            };
        });

        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
           //     sm.set(new MainState(sm));
                hariotikaMessage = new HariotikaMessage(Command.Battle, WsCode.CancelRegistrationToBattle);
                client.sendMessage(gson.toJson(hariotikaMessage));
                 registrationInBattl =false;
                 searchBattleWindow.remove();
              //  client.sendMessage("CancelRegBattle");
            };
        });


      //  System.out.printf(gson.toJson(client));

    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched())
        {
                // client.sendMessage("login");
        }

    }

    @Override
    public void update(float dt) {


            if(Gdx.files.local("avatar/"+character.getName()+".png").exists() && character.getAvatar() == null){
                try {
                    playerAvatar =  new Texture(Gdx.files.local("avatar/"+character.getName()+".png"));
                           }
                catch (Exception e){
                    Gdx.app.log("Hariotika mainState","Can't load avatar "+character.getName());
                    Gdx.app.log("Hariotika mainState",Gdx.files.local("avatar/"+character.getName()+".png").path());

                }
            }

        if (registrationInBattl) {
            if(getBattle() != null) {
                sm.set(new BattleState(sm, skin2, backButton));
            }
        }

        handleInput();
        if (load) {
            health.setValue(client.getCharacter().getHP());
            playerName.setText(client.getCharacter().getName());
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.end();
        stage.draw();


    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }




    Net.HttpResponseListener listener = new Net.HttpResponseListener() {
        public void handleHttpResponse (Net.HttpResponse httpResponse) {
            HttpStatus status = httpResponse.getStatus();
            if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
                // it was successful
                //    System.out.println(httpResponse.getResult());
                //  client.getCharacter().setAvatar(httpResponse.getResult());
                try {


                    Gdx.app.log("Hariotika API"," Get Player avatar ");
                    InputStream in = new ByteArrayInputStream(httpResponse.getResult());
                    if (Gdx.files.local("avatar/"+character.getName()+".png").exists())
                        Gdx.files.local("avatar/"+character.getName()+".png").delete();
                    FileHandle handle = Gdx.files.local("avatar/"+character.getName()+".png");
                    handle.write(in,true);
                    Gdx.app.log("Hariotika API","Save Player avatart");
                    in.close();

                  } catch (Exception e) {
                    Gdx.app.error("Hariotika API ", e.toString());
                    e.printStackTrace();
                }

            } else {
                // do something else
                System.out.println("Код "+status.getStatusCode());
                Gdx.app.log("Hariotika API","Bad coe "+status.getStatusCode());
            }

        }


        @Override
        public void failed(Throwable throwable) {
            Gdx.app.error("Hariotika API ", throwable.toString());
            System.out.println(throwable);
        }

        @Override
        public void cancelled() {
            System.out.println("Cancel");
        }
    };







    public static ProgressBar getHealth() {
        return health;
    }

    public static void setHealth(ProgressBar health) {
        MainState.health = health;
    }

}
