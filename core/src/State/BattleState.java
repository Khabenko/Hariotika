package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;

import java.awt.Checkbox;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import API.Client;
import API.Command;
import API.HariotikaMessage;
import API.WsCode;
import Domain.Battle;
import Domain.Character;
import Domain.LogWindow;

import static API.Client.battle;
import static API.Client.character;
import static API.Client.getBattle;
import static API.Client.setBattle;
import static API.Reconect.client;
import static State.MainState.getHealth;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by Maka on 06.11.2017.
 */

public class BattleState extends State {

    HariotikaMessage hariotikaMessage;
    Gson gson;
    static Character enemy;
    Label timer;
    Skin skin;
    Skin skinChebox;
    private Texture background;
    private Texture backgroundLoading;
    private Stage stage;
    TextButton battleButton;
    Table table;
    Table statusEnemy;
    static Label enemyName;
    static ProgressBar enemyhealth;
    static ProgressBar enemymana;
    static ProgressBar enemysp;
    TextButton backButton;
    LogWindow logWindow;

    private Texture playerAvatar;
    private Texture enemyAvatar;


    public BattleState(StateManager sm, Skin skin, TextButton backButton) {
        super(sm);
        skinChebox = new Skin(Gdx.files.internal("data/visui1/uiskin.json"));
        gson = new Gson();
        this.backButton = backButton;
        this.skin = skin;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        timer = new Label("30",  skinChebox);
        timer.setPosition(camera.viewportWidth/2,camera.viewportHeight*0.30f);
        stage.addActor(timer);



        enemy= new Character();
        this.table = MainState.status;
        createEnemyBar();
        initEnemy();
        backgroundLoading = new Texture("loadBattl1.png");
        background = new Texture("fon2.png");
        Gdx.app.log("HariotikaBattleState", "Loaded background");

        skinChebox = new Skin(Gdx.files.internal("data/visui1/uiskin.json"));

        stage.addActor(table);

        final ButtonGroup checkboxGroupHit = new ButtonGroup();
        CheckBox checkboxHeadHit = new CheckBox("HEAD",skinChebox);
        checkboxHeadHit.setName("HEAD");
        checkboxHeadHit.setPosition(camera.viewportWidth*0.85f,camera.viewportHeight*0.70f);
        final CheckBox checkboxBodyHit = new CheckBox("BODY",skinChebox);
        checkboxBodyHit.setName("BODY");
        checkboxBodyHit.setPosition(camera.viewportWidth*0.85f,checkboxHeadHit.getY()-80);
        CheckBox checkboxLegsHit = new CheckBox("LEGS",skinChebox);
        checkboxLegsHit.setName("LEGS");
        checkboxLegsHit.setPosition(camera.viewportWidth*0.85f,checkboxBodyHit.getY()-80);
        checkboxGroupHit.add(checkboxBodyHit);
        checkboxGroupHit.add(checkboxHeadHit);
        checkboxGroupHit.add(checkboxLegsHit);
        checkboxGroupHit.setMaxCheckCount(1);


        stage.addActor(checkboxBodyHit);
        stage.addActor(checkboxHeadHit);
        stage.addActor(checkboxLegsHit);




        final ButtonGroup checkboxGroupDef = new ButtonGroup();
        CheckBox checkboxHeadDef = new CheckBox("HEAD",skinChebox);
        checkboxHeadDef.setName("HEAD");
        checkboxHeadDef.setPosition(10,camera.viewportHeight*0.70f);
        final CheckBox checkboxBodyDef = new CheckBox("BODY",skinChebox);
        checkboxBodyDef.setName("BODY");
        checkboxBodyDef.setPosition(10,checkboxHeadDef.getY()-80);
        CheckBox checkboxLegsDef = new CheckBox("LEGS",skinChebox);
        checkboxLegsDef.setName("LEGS");
        checkboxLegsDef.setPosition(10,checkboxBodyDef.getY()-80);


        checkboxGroupDef.add(checkboxBodyDef);
        checkboxGroupDef.add(checkboxHeadDef);
        checkboxGroupDef.add(checkboxLegsDef);
        checkboxGroupDef.setMaxCheckCount(1);

        stage.addActor(checkboxBodyDef);
        stage.addActor(checkboxHeadDef);
        stage.addActor(checkboxLegsDef);

        Gdx.app.log("HariotikaBattleState", "Loaded ChekBox");

        logWindow = new LogWindow(skinChebox);
        logWindow .setPosition(10,10);
        logWindow.setSize(camera.viewportWidth,300);
        stage.addActor(logWindow );





        battleButton = new TextButton("Battle",skin);
        battleButton.setSize(250,150);
        battleButton.setPosition(camera.viewportWidth/6-battleButton.getWidth(),camera.viewportHeight/7);

        stage.addActor(battleButton);

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:8081?name="+character.getName()).build();
        Gdx.net.sendHttpRequest(httpRequest, listener);

        httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:8081?name="+enemyName).build();
        Gdx.net.sendHttpRequest(httpRequest, enemyListener);




        // stage.addActor(backButton);
       // backButton.setVisible(false);

        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                
            hariotikaMessage = new HariotikaMessage(Command.Battle, WsCode.UpdateBattle,battle);
            hariotikaMessage.setCharName(character.getName());
            hariotikaMessage.setHit(checkboxGroupHit.getChecked().getName());
            hariotikaMessage.setDef(checkboxGroupDef.getChecked().getName());
            client.sendMessage(gson.toJson(hariotikaMessage));

        //   client.sendMessage("Battle#"+ getBattle().getNumber()+"#"+client.getCharacter().getName()+"#"+checkboxGroupHit.getChecked().getName()+"#"+checkboxGroupDef.getChecked().getName());

            };
        });



    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(Gdx.files.local("avatar/"+character.getName()+".png").exists() && Gdx.files.local("avatar/"+enemy.getName()+".png").exists()){
            try {
           playerAvatar = new Texture("avatar/"+character.getName()+".png");
           enemyAvatar = new Texture("avatar/"+enemy.getName()+".png");
               }
            catch (Exception e){
                Gdx.app.log("Battle","Can't load avatar");
            }
        }

        if (battle!=null) timer.setText(String.valueOf(battle.getTimer()));
        initEnemy();
        getHealth().setValue(client.getCharacter().getHP());
        if (enemy.getName()!=null) {
            enemyhealth.setValue(enemy.getHP());
            // System.out.println(enemy.getName());
            enemyName.setText(enemy.getName());
            logWindow.clear();
            logWindow.add(client.getBattle().getLog());
            if (getBattle().isFinished()) {
                setBattle(null);
                sm.set(new MainState(sm));
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        if (getBattle()== null || playerAvatar == null || enemyAvatar == null) {

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            sb.begin();
            sb.draw(backgroundLoading, 0, 0, camera.viewportWidth, camera.viewportHeight);
            sb.end();

        }
        else {

            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            sb.begin();
            sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
            sb.draw(playerAvatar, camera.viewportWidth/2-400,580, 250, 220);
            sb.draw(enemyAvatar, camera.viewportWidth/2,580, 250, 220);

            sb.end();
            stage.draw();

        }

    }

    @Override
    public void dispose() {

    }



    private void createEnemyBar(){
        statusEnemy = new Table(skin);
        enemyName = new Label("Enemy",skin);
        statusEnemy.add(enemyName);
        statusEnemy.row();
        statusEnemy.add(new Label("HP", skin));
        enemyhealth = new ProgressBar(0, 100, 1, false, skin);
        enemyhealth.setValue(0);
        enemyhealth.setColor(Color.FOREST);
        statusEnemy.add(enemyhealth).width(500);

        statusEnemy.row();

        statusEnemy.add(new Label("MP", skin));
        enemymana = new ProgressBar(0, 100, 1, false, skin);
        enemymana.setValue(24);
        enemymana.setColor(BLUE);
        statusEnemy.add(enemymana).width(500);
        statusEnemy.setPosition(420,510);

        statusEnemy.row();

        statusEnemy.add(new Label("SP", skin));
        enemysp = new ProgressBar(0, 100, 1, false, skin);
        enemysp.setValue(50);
        enemysp.setColor(Color.CHARTREUSE);
        statusEnemy.add(enemysp).width(500);
        statusEnemy.setPosition(camera.viewportWidth/2+400,camera.viewportHeight/1.08f);
        stage.addActor(statusEnemy);
      //Gdx.app.log("HariotikaBattleState", "createEnemyBar");


    }


    private void initEnemy(){

        if (getBattle()!=null) {
           // System.out.println(getBattle().getPlayer1().getName().equals(Client.character.getName()));
          //  System.out.println(getBattle().getPlayer1().getName().equals(Client.character.getName()));

            if (!(getBattle().getPlayer1().getName().equals(Client.character.getName()))) {
                setEnemy(getBattle().getPlayer1());
             }

            if (!(getBattle().getPlayer2().getName().equals(Client.character.getName()))) {
                setEnemy(getBattle().getPlayer2());
             }
        }
    //    Gdx.app.log("HariotikaBattleState", "initEnemy()");

    }


    public Character getEnemy() {
        return enemy;
    }

    public void setEnemy(Character enemy) {
        this.enemy = enemy;
    }




    final Net.HttpResponseListener listener = new Net.HttpResponseListener() {
        public void handleHttpResponse (Net.HttpResponse httpResponse) {
            HttpStatus status = httpResponse.getStatus();
            if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
                // it was successful
                //    System.out.println(httpResponse.getResult());
              //  client.getCharacter().setAvatar(httpResponse.getResult());
                try {
                    InputStream in = new ByteArrayInputStream(httpResponse.getResult());
                    BufferedImage bImageFromConvert = ImageIO.read(in);
                    System.out.println("Пришла картинка");
                    FileHandle handle = Gdx.files.local("avatar/"+character.getName()+".png");
                    System.out.println(handle.path());
                    OutputStream outputStream = handle.write(false);
                    ImageIO.write(bImageFromConvert, "png", outputStream);

               //     File outputfile = new File("avatar/"+character.getName()+".png");
               //     ImageIO.write(bImageFromConvert, "png", outputfile);
                    outputStream.close();
                    System.out.println(playerAvatar);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                // do something else
                System.out.println(status.getStatusCode());
            }

        }


        @Override
        public void failed(Throwable throwable) {

        }

        @Override
        public void cancelled() {

        }
    };


    final Net.HttpResponseListener enemyListener = new Net.HttpResponseListener() {
        public void handleHttpResponse (Net.HttpResponse httpResponse) {
            HttpStatus status = httpResponse.getStatus();
            if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
                // it was successful
                //    System.out.println(httpResponse.getResult());
                //  client.getCharacter().setAvatar(httpResponse.getResult());
                try {
                    InputStream in = new ByteArrayInputStream(httpResponse.getResult());
                    BufferedImage bImageFromConvert = ImageIO.read(in);
                    System.out.println("Пришла картинка");
                    FileHandle handle = Gdx.files.local("avatar/"+enemy.getName()+".png");
                    System.out.println(handle.path());
                    OutputStream outputStream = handle.write(false);
                    ImageIO.write(bImageFromConvert, "png", outputStream);

                    //     File outputfile = new File("avatar/"+character.getName()+".png");
                    //     ImageIO.write(bImageFromConvert, "png", outputfile);
                    outputStream.close();
                    System.out.println(playerAvatar);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                // do something else
                System.out.println(status.getStatusCode());
            }

        }


        @Override
        public void failed(Throwable throwable) {

        }

        @Override
        public void cancelled() {

        }
    };




}
