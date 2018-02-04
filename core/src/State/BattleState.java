package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.google.gson.Gson;
import com.hariotika.Animation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


import API.Client;
import API.Command;
import API.HariotikaMessage;
import API.WsCode;
import Domain.Battle;
import Domain.Character;
import Domain.LogWindow;
import Domain.PartOfBody;

import static API.Client.battle;
import static API.Client.character;
import static API.Client.getBattle;
import static API.Client.setBattle;
import static API.Reconect.client;
import static State.MainState.getHealth;
import static com.badlogic.gdx.graphics.Color.BLUE;


/**
 * Created by Maka on 06.11.2017.
 */

public class BattleState extends State {

    HariotikaMessage hariotikaMessage;
    String uri;
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

    private Texture shield;
    private int checkBoxDistance = 80;

    HashMap<String,CheckBox> checkBoxMap = new HashMap<String, CheckBox>();



    final ButtonGroup checkboxGroupDef = new ButtonGroup();
    ArrayList<PartOfBody> partOfBodies = new ArrayList<PartOfBody>();

    private Texture cartman;
    private Animation cartmanAnimatiom;




    String avatarUri;


    private Texture playerAvatar;
    private Texture enemyAvatar;


    public BattleState(final StateManager sm, Skin skin, TextButton backButton) {
        super(sm);
        skinChebox = new Skin(Gdx.files.internal("data/visui1/uiskin.json"));
        shield= new Texture("src/Battle/shield-icon.png");
        gson = new Gson();
        this.backButton = backButton;
        this.skin = skin;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        timer = new Label("30",  skinChebox);
        timer.setPosition(camera.viewportWidth/2,camera.viewportHeight*0.30f);
        stage.addActor(timer);


        for (int i = 0; i < 2 ; i++) {
            for (PartOfBody mass : PartOfBody.values()) {
                partOfBodies.add(mass);

            }
        }


        enemy = new Character();
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
        checkboxHeadHit.setPosition(camera.viewportWidth*0.80f,camera.viewportHeight*0.70f);

        CheckBox checkboxNeckHit = new CheckBox("NECK",skinChebox);
        checkboxNeckHit.setName("NECK");
        checkboxNeckHit.setPosition(camera.viewportWidth*0.80f,checkboxHeadHit.getY()-checkBoxDistance);

        CheckBox checkboxChestHit = new CheckBox("CHEST",skinChebox);
        checkboxChestHit.setName("CHEST");
        checkboxChestHit.setPosition(camera.viewportWidth*0.80f,checkboxNeckHit.getY()-checkBoxDistance);

        CheckBox checkboxBellyHit = new CheckBox("BELLY",skinChebox);
        checkboxBellyHit.setName("BELLY");
        checkboxBellyHit.setPosition(camera.viewportWidth*0.80f,checkboxChestHit.getY()-checkBoxDistance);

        CheckBox checkboxLegsHit = new CheckBox("LEGS",skinChebox);
        checkboxLegsHit.setName("LEGS");
        checkboxLegsHit.setPosition(camera.viewportWidth*0.80f,checkboxBellyHit.getY()-checkBoxDistance);

        checkboxGroupHit.add(checkboxHeadHit);
        checkboxGroupHit.add(checkboxNeckHit);
        checkboxGroupHit.add(checkboxChestHit);
        checkboxGroupHit.add(checkboxBellyHit);
        checkboxGroupHit.add(checkboxLegsHit);
        checkboxGroupHit.setMaxCheckCount(1);



        stage.addActor(checkboxChestHit);
        stage.addActor(checkboxHeadHit);
        stage.addActor(checkboxLegsHit);
        stage.addActor(checkboxNeckHit);
        stage.addActor(checkboxBellyHit);




      //  final ButtonGroup checkboxGroupDef = new ButtonGroup();


        CheckBox checkboxHeadDef = new CheckBox("HEAD",skinChebox);
        checkboxHeadDef.setName("HEAD");
        checkboxHeadDef.setPosition(10,camera.viewportHeight*0.70f);

        CheckBox checkboxNeckDef = new CheckBox("NECK",skinChebox);
        checkboxNeckDef.setName("NECK");
        checkboxNeckDef.setPosition(10,checkboxHeadDef.getY()-checkBoxDistance);

        CheckBox checkboxChestDef = new CheckBox("CHEST",skinChebox);
        checkboxChestDef.setName("CHEST");
        checkboxChestDef.setPosition(10,checkboxNeckDef.getY()-checkBoxDistance);

        CheckBox checkboxBellyDef = new CheckBox("BELLY",skinChebox);
        checkboxBellyDef.setName("BELLY");
        checkboxBellyDef.setPosition(10,checkboxChestDef.getY()-checkBoxDistance);

        CheckBox checkboxLegsDef = new CheckBox("LEGS",skinChebox);
        checkboxLegsDef.setName("LEGS");
        checkboxLegsDef.setPosition(10,checkboxBellyDef.getY()-checkBoxDistance);



        checkBoxMap.put(checkboxHeadDef.getName(),checkboxHeadDef);
        checkBoxMap.put(checkboxNeckDef.getName(),checkboxNeckDef);
        checkBoxMap.put(checkboxChestDef.getName(),checkboxChestDef);
        checkBoxMap.put(checkboxBellyDef.getName(),checkboxBellyDef);
        checkBoxMap.put(checkboxLegsDef.getName(),checkboxLegsDef);


        checkboxGroupDef.add(checkboxHeadDef);
        checkboxGroupDef.add(checkboxNeckDef);
        checkboxGroupDef.add(checkboxChestDef);
        checkboxGroupDef.add(checkboxBellyDef);
        checkboxGroupDef.add(checkboxLegsDef);
        checkboxGroupDef.setMaxCheckCount(1);



/*
        stage.addListener(new ChangeListener() {
                      @Override
                      public void changed(ChangeEvent changeEvent, Actor actor) {

                         тут вызываем любой мето, при изменеии любого поля state - вызовится лисинер.
        }
                  });


        checkboxHeadDef.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

           // addDefPart();

            }
        });

        checkboxNeckDef.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                //   addDefPart();
            }
        });
        checkboxChestDef.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            //   addDefPart();
            }
        });
        checkboxBellyDef.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            //   addDefPart();
            }
        });
        checkboxLegsDef.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
             //   addDefPart();
            }
        });

*/


        stage.addActor(checkboxChestDef);
        stage.addActor(checkboxHeadDef);
        stage.addActor(checkboxLegsDef);
        stage.addActor(checkboxBellyDef);
        stage.addActor(checkboxNeckDef);

        Gdx.app.log("HariotikaBattleState", "Loaded ChekBox");

        logWindow = new LogWindow(skinChebox);
        logWindow .setPosition(400,10);
        logWindow.setSize(camera.viewportWidth/2,300);
        stage.addActor(logWindow);

        battleButton = new TextButton("Battle",skin);
        battleButton.setSize(250,150);
        battleButton.setPosition(camera.viewportWidth/6-battleButton.getWidth(),camera.viewportHeight/7);
        stage.addActor(battleButton);

       // stage.addActor(backButton);

        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            hariotikaMessage = new HariotikaMessage(Command.Battle, WsCode.UpdateBattle,battle);
            hariotikaMessage.setCharName(character.getName());
            hariotikaMessage.setHit(checkboxGroupHit.getChecked().getName());
            hariotikaMessage.setDef(checkboxGroupDef.getChecked().getName());
            hariotikaMessage.setPlayerDefance(battle.getPlayer1DefanceList());
            if (!getBattle().isFinished() && getBattle()!=null) {
                client.sendMessage(gson.toJson(hariotikaMessage));
                System.out.println(battle.isFinished());
                System.out.println(battle);
            }

            };
        });


        backButton = new TextButton("Back",skin);
        backButton.setPosition(camera.viewportWidth/2+600,camera.viewportHeight/7);
        backButton.setSize(250,150);
        stage.addActor(backButton);

        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setBattle(null);
                sm.set(new MainState(sm));

            };
        });

      //  cartman = new Texture("Animation/cartman.png");
       // cartmanAnimatiom = new Animation(new TextureRegion(cartman), 4, 0.5f);
        //sb.draw(cartmanAnimatiom.getFrame(), (camera.viewportWidth/2), camera.viewportHeight/2+50);

         // avatarUri = "http://10.0.2.2:8081/getAvatar/?name=";
           // avatarUri = "http://localhost:8081/getAvatar/?name=";
          avatarUri = "http://64.250.115.155/getAvatar/?name=";


          Gdx.app.log("Hariotika API"," Get from Server Enemy avatar "+enemy.getName());
          HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
          Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(avatarUri+enemy.getName()).build();
          Gdx.net.sendHttpRequest(httpRequest, enemyListener);




    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

        //checkboxGroupDef.setChecked(String.valueOf(checkboxGroupDef.getButtons().get(checkboxGroupDef.getCheckedIndex()+1)));
        addDefPart();

       //   cartmanAnimatiom.update(dt);

     //   Gdx.app.log("Hariotika",character.getName()+" "+Gdx.files.local("avatar/"+character.getName()+".png").exists());
     //   Gdx.app.log("Hariotika",enemy.getName()+" "+Gdx.files.local("avatar/"+enemy.getName()+".png").exists());
     //   Gdx.app.log("Hariotika", String.valueOf(playerAvatar == null));
     //   Gdx.app.log("Hariotika", String.valueOf(enemyAvatar==null));


        if(Gdx.files.local("avatar/"+enemy.getName()+".png").exists() && enemyAvatar==null){
            try {
            enemyAvatar = new Texture(Gdx.files.local("avatar/"+enemy.getName()+".png"));
               }
            catch (Exception e){
                Gdx.app.log("Battle","Can't load avatar");
            }
        }

            if(Gdx.files.local("avatar/"+character.getName()+".png").exists() && playerAvatar==null){
                try {
                    playerAvatar = new Texture(Gdx.files.local("avatar/"+character.getName()+".png"));
                }
                catch (Exception e){
                    Gdx.app.log("Battle","Can't load avatar");
                }
            }



        if (battle!=null) {
            timer.setText(String.valueOf(battle.getTimer()));
            initEnemy();
            getHealth().setValue(client.getCharacter().getHP());
            if (enemy.getName() != null) {
                enemyhealth.setValue(enemy.getHP());
                // System.out.println(enemy.getName());
                enemyName.setText(enemy.getName());
                logWindow.clear();
                logWindow.add(client.getBattle().getLog());
                if (battleButton.isVisible() && (getBattle().isFinished() || getBattle() == null)) {
                    battleButton.setVisible(false);

                    // sm.set(new MainState(sm));
                }
            }
        }

    }




    @Override
    public void render(SpriteBatch sb) {
        if (battle == null || playerAvatar==null || enemyAvatar ==null ) {

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
            sb.draw(playerAvatar, camera.viewportWidth/2-400,camera.viewportHeight/2+50, 250, 220);
            sb.draw(enemyAvatar, (camera.viewportWidth/2),camera.viewportHeight/2+50, 250, 220);
            if (battle!=null && battle.getPlayer1DefanceList().size()>0 && checkBoxMap !=null && checkBoxMap.size()>0) {
                for (int i = 0; i <battle.getPlayer1DefanceList().size() ; i++) {
                              sb.draw(shield, checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i))).getX(), checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i))).getY()+50, 20, 20);
                //    sb.draw(shield, checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i+1))).getX(), checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i+1))).getY(), 40, 40);


                }

            }

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




        Net.HttpResponseListener enemyListener = new Net.HttpResponseListener() {
        public void handleHttpResponse (Net.HttpResponse httpResponse) {
            HttpStatus status = httpResponse.getStatus();
            if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
                // it was successful
                //    System.out.println(httpResponse.getResult());
                //  client.getCharacter().setAvatar(httpResponse.getResult());
                try {


                    Gdx.app.log("Hariotika API"," Get Enemy avatar "+enemy.getName());
                    InputStream in = new ByteArrayInputStream(httpResponse.getResult());
                    if ( Gdx.files.local("avatar/"+enemy.getName()+".png").exists())
                    Gdx.files.local("avatar/"+enemy.getName()+".png").delete();
                    FileHandle handle = Gdx.files.local("avatar/"+enemy.getName()+".png");
                    handle.write(in,true);
                    Gdx.app.log("Hariotika API","Save Enemy avatart "+enemy.getName());
                    in.close();

                } catch (IOException e) {

                    Gdx.app.error("Hariotika API ", e.toString());
                    e.printStackTrace();
                }

            } else {
                // do something else

                Gdx.app.log("Hariotika API","Bad code "+status.getStatusCode());
            }

        }


        @Override
        public void failed(Throwable throwable) {
            Gdx.app.error("Hariotika API","Throwable", throwable);

            System.out.println(throwable);
        }

        @Override
        public void cancelled() {
            System.out.println("Cancel");
        }
    };




    public void addDefPart(){

        String check = checkboxGroupDef.getChecked().getName();
        if (battle.getPlayer1DefanceList() != null)
        battle.getPlayer1DefanceList().clear();
        if (partOfBodies!=null&&partOfBodies.size()>0)
        for (int i = 0; i < partOfBodies.size() ; i++) {
            if (String.valueOf(partOfBodies.get(i)).equals(check)){
                battle.getPlayer1DefanceList().add(PartOfBody.valueOf(checkboxGroupDef.getChecked().getName()));
                battle.getPlayer1DefanceList().add(partOfBodies.get(i+1));
             //   checkboxGroupDef.setChecked(String.valueOf(partOfBodies.get(i)));
             //   checkboxGroupDef.setChecked(String.valueOf(partOfBodies.get(i+1)));
                break;
            }
        }
    }




}
