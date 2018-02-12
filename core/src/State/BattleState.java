package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;


import API.Client;
import API.Command;
import API.HariotikaMessage;
import API.WsCode;
import Domain.AfterBattleWindow;
import Domain.Battle;
import Domain.Character;
import Domain.LogWindow;
import Domain.PartOfBody;
import Domain.RoundLogs;

import static API.Client.battle;
import static API.Client.character;
import static API.Client.getBattle;
import static API.Client.setBattle;
import static API.Reconect.client;
import static State.MainState.exp;
import static State.MainState.expCount;
import static State.MainState.expLable;
import static State.MainState.getHealth;
import static State.MainState.getMana;
import static State.MainState.mana;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.badlogic.gdx.graphics.Color.RED;
import static java.lang.Thread.enumerate;


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
    TextButton backButton;
    LogWindow logWindow;
    AfterBattleWindow afterBattleWindow;
    private Texture shield;
    private int checkBoxDistance = 80;
    private float oneX = camera.viewportWidth/100;
    private float oneY = camera.viewportWidth/100;
    HashMap<String,CheckBox> checkBoxMap = new HashMap<String, CheckBox>();
    BitmapFont fountDamage;


    float walk = 0;

    final ButtonGroup checkboxGroupDef = new ButtonGroup();
    ArrayList<PartOfBody> partOfBodies = new ArrayList<PartOfBody>();

    Label playerLog;
    Label enemyLog;

    private Texture knightWalk;
    private Animation knightwalkAnimation;

    private String avatarUri;
    private Texture playerAvatar;
    private Texture enemyAvatar;


    public BattleState(final StateManager sm, final Skin skin, TextButton backButton) {
        super(sm);
        skinChebox = new Skin(Gdx.files.internal("data/visui1/uiskin.json"));
        shield= new Texture("src/Battle/shield-icon.png");
        gson = new Gson();
        this.backButton = backButton;
        this.skin = skin;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        BitmapFont fountDamage = new BitmapFont(Gdx.files.internal("data/Battel/TimerBattel/Damage.fnt"));
        Label.LabelStyle Style =  new Label.LabelStyle(fountDamage,new Color(200));


        playerLog = new Label("",Style);
        enemyLog = new Label("",Style);
        playerLog.setPosition(oneX*30,oneY*15);
        stage.addActor(playerLog);
        enemyLog.setPosition(oneX*55,oneY*15);
        stage.addActor(enemyLog);


        BitmapFont fount = new BitmapFont(Gdx.files.internal("data/Battel/TimerBattel/Timer.fnt"));
        Label.LabelStyle LabelStyle =  new Label.LabelStyle(fount,new Color(95));

        timer = new Label("30", LabelStyle);
        timer.setPosition(oneX*45,oneY*50);
        stage.addActor(timer);


        for (int i = 0; i < 2 ; i++) {
            for (PartOfBody mass : PartOfBody.values()) {
                partOfBodies.add(mass);

            }
        }



        this.table = MainState.status;
        this.table.removeActor(exp);
        this.table.removeActor(expLable);
        this.table.removeActor(expCount);
      //  this.enemy = getEnemy();
        initEnemy();
        createEnemyBar();

       

        backgroundLoading = new Texture("loadBattl1.png");
      //  background = new Texture("fon2.png");
        background = new Texture("arena.jpg");
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
        logWindow .setPosition(oneX*20,oneY*25);
        logWindow.setSize(oneX*45,oneY*20);
       // stage.addActor(logWindow);

        battleButton = new TextButton("Battle",skin);
        battleButton.setSize(oneX*14,oneY*8);
        battleButton.setPosition(oneX*75,oneY*5);
       stage.addActor(battleButton);



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
        backButton.setPosition(oneX,oneY);
        backButton.setSize(250,150);
       // stage.addActor(backButton);


        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                  battelRemoved();
              //  setBattle(null);
               // sm.set(new MainState(sm));

            };
        });
        final Skin finalSkin = skin;

        stage.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {


                if ( battle!=null) {
                    if (battle.getPlayer1() != null && battle.getPlayer2() != null && enemy == null) {
                        createEnemyBar();
                        initEnemy();
                    }

                    if (battle.isFinished()) {
                        AfterBattleWindow afterBattleWindow = new AfterBattleWindow(finalSkin, sm);
                        afterBattleWindow.setPosition(oneX * 35, oneY * 25);
                        afterBattleWindow.setSize(400, 300);
                        stage.addActor(afterBattleWindow);
                    }

                    if (battle.getPlayer1LogHit() != null && battle.getPlayer2LogHit() != null) {
                        playerLog.setText("");
                        enemyLog.setText("");
                        logWindow.clear();
                       // playerLog.clear();
                     //   enemyLog.clear();
                        printLog(battle.getPlayer1LogHit(), battle.getPlayer1().getName(), battle.getPlayer2().getName());
                        printLog(battle.getPlayer2LogHit(), battle.getPlayer2().getName(), battle.getPlayer1().getName());
                    }
                }
            }
        });



         //  cartman = new Texture("Animation/cartman.png");
        //   cartmanAnimatiom = new Animation(new TextureRegion(cartman), 4, 0.5f);
         //   sb.draw(cartmanAnimatiom.getFrame(), (camera.viewportWidth/2), camera.viewportHeight/2+50);



        knightWalk = new Texture(Gdx.files.internal("Animation/Knight/Knight1/WALK.png"));
        knightwalkAnimation = new Animation(new TextureRegion(knightWalk), 7, 1f);

            // avatarUri = "http://10.0.2.2:8081/getAvatar/?name=";
           // avatarUri = "http://localhost:8081/getAvatar/?name=";
           avatarUri = "http://64.250.115.155/getAvatar/?name=";
          // avatarUri = "http://64.250.115.155:8082/getAvatar/?name=";

      //    Gdx.app.log("Hariotika API"," Get from Server Enemy avatar "+enemy.getName());
          HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
          Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(avatarUri+enemy.getName()).build();
          Gdx.net.sendHttpRequest(httpRequest, enemyListener);

    }



    public void printLog(RoundLogs roundLogs, String namePlayer, String enemyName){


         String temp = "";

        logWindow.add().row();
        logWindow.add(namePlayer+" Hit ");
        logWindow.add().row();

        if (!roundLogs.isEnemyBlock()) {
             if (roundLogs.isEnemyDodge()) {
                 if (roundLogs.isEnemyParry()) {
                   if (roundLogs.isPlayerCritkal()) {
                //Крит прошел
                 logWindow.add(namePlayer+" deal Critickal damage "+roundLogs.getPlayerDamaged());
                logWindow.add().row();
                temp = "Critickal damage "+roundLogs.getPlayerDamaged()+" to " +roundLogs.getHit();
                     }
                     else{
                     logWindow.add(namePlayer+" deal damage "+roundLogs.getPlayerDamaged()+" to " +roundLogs.getHit());
                     logWindow.add().row();
                     knightwalkAnimation.setPlay(true);
                     temp= "Damage "+ roundLogs.getPlayerDamaged()*-1+" to " +roundLogs.getHit();
            }

        } else
            {
            logWindow.add(enemyName+" Parried hit ");
            logWindow.add().row();
            temp = "Parried hit to " +roundLogs.getHit();
            }
    } else
        {
           logWindow.add(enemyName+" Dodged hit to " +roundLogs.getHit());
           logWindow.add().row();
           temp = "Dodged hit to " +roundLogs.getHit();
       }
    } else
        {
            logWindow.add(enemyName+" Blocked hit");
            logWindow.add().row();
            temp = "Blocked hit to " +roundLogs.getHit();
        }
        logWindow.add("\n");
        logWindow.add().row();

        if (!namePlayer.equals(character.getName())){
            playerLog.setText(temp);
        }else {
            enemyLog.setText(temp);
        }

    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {




         knightwalkAnimation.update(dt);
         addDefPart();

        if (knightwalkAnimation.isPlay()) {
            walk += 0.05/dt;

        }

        if ((oneX*30)+walk > oneX*55){
            knightwalkAnimation.setPlay(false);
            walk =0;
        }




        if (battle.getPlayer1LogHit() != null && battle.getPlayer2LogHit() != null) {
            playerLog.setText("");
            enemyLog.setText("");
             playerLog.clear();
             enemyLog.clear();
            printLog(battle.getPlayer1LogHit(), battle.getPlayer1().getName(), battle.getPlayer2().getName());
            printLog(battle.getPlayer2LogHit(), battle.getPlayer2().getName(), battle.getPlayer1().getName());
        }


      //  Gdx.app.log("Hariotika",character.getName()+" "+Gdx.files.local("avatar/"+character.getName()+".png").exists());
      //  Gdx.app.log("Hariotika",enemy.getName()+" "+Gdx.files.local("avatar/"+enemy.getName()+".png").exists());
     //   Gdx.app.log("Hariotika", String.valueOf(playerAvatar == null));
      //  Gdx.app.log("Hariotika", String.valueOf(enemyAvatar==null));

        if(Gdx.files.local("avatar/"+enemy.getName()+".png").exists() && enemyAvatar==null){
            try {
            enemyAvatar = new Texture(Gdx.files.local("avatar/"+enemy.getName()+".png"));
               }
            catch (Exception e){
                e.printStackTrace();
                Gdx.app.log("Battle","Can't load enemy avatar");
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
            getMana().setValue(client.getCharacter().getMP());
            if (enemy.getName() != null) {
                enemyhealth.setValue(enemy.getHP());
                enemymana.setValue(enemy.getMP());
                enemyName.setText(enemy.getName());
            //   logWindow.clear();
            //   logWindow.add(client.getBattle().getLog());
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

/*
            if (knightwalkAnimation.isPlay()) {

                sb.draw(knightwalkAnimation.getFrame(), (oneX*30)+walk,oneY*15);
            }
            else {
                sb.draw(playerAvatar,oneX*30,oneY*15) ;
            }

*/
              sb.draw(playerAvatar,oneX*30,oneY*15) ;
              sb.draw(enemyAvatar, oneX*55,oneY*15);

            if (battle!=null && battle.getPlayer1DefanceList().size()>0 && checkBoxMap !=null && checkBoxMap.size()>0) {
                for (int i = 0; i <battle.getPlayer1DefanceList().size() ; i++) {
                              sb.draw(shield, checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i))).getX(), checkBoxMap.get(String.valueOf(battle.getPlayer1DefanceList().get(i))).getY()+50, 20, 20);
                }
            }

            sb.end();
            stage.draw();

        }

    }

    @Override
    public void dispose() {

    }


    public void battelRemoved(){
        playerLog.setText("");
        enemyLog.setText("");
        setBattle(null);
        sm.set(new MainState(sm));

    }


    private void createEnemyBar(){
        if (enemy!=null && enemy.getName()!=null) {
            statusEnemy = new Table(skin);
            enemyName = new Label("Enemy", skin);
            statusEnemy.add(enemyName);
            statusEnemy.row();
            statusEnemy.add(new Label("HP", skin));
            enemyhealth = new ProgressBar(0, enemy.getMaxHP(), 1, false, skin);
            enemyhealth.setValue(enemy.getHP());
            enemyhealth.setColor(Color.FOREST);
            statusEnemy.add(enemyhealth).width(500);

            statusEnemy.row();

            statusEnemy.add(new Label("MP", skin));
            enemymana = new ProgressBar(0, enemy.getMaxMP(), 1, false, skin);
            enemymana.setValue(enemy.getMP());
            enemymana.setColor(BLUE);
            statusEnemy.add(enemymana).width(500);
            statusEnemy.setPosition(420, 510);

            statusEnemy.setPosition(camera.viewportWidth / 2 + 400, camera.viewportHeight / 1.08f);
            stage.addActor(statusEnemy);
            //Gdx.app.log("HariotikaBattleState", "createEnemyBar");
        }


    }


    private void initEnemy(){

        if (battle !=null) {
       //     System.out.println(getBattle().getPlayer1().getName().equals(Client.character.getName()));
       //     System.out.println(getBattle().getPlayer1().getName().equals(Client.character.getName()));
            if (!(battle.getPlayer1().getName().equals(Client.character.getName()))) {
                setEnemy(getBattle().getPlayer1());
             }
            if (!(battle.getPlayer2().getName().equals(Client.character.getName()))) {
                setEnemy(battle.getPlayer2());
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
                 //  System.out.println(httpResponse.getResult());
                   //client.getCharacter().setAvatar(httpResponse.getResult());
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
                Gdx.files.local("avatar/"+enemy.getName()+".png").delete();
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




    public void addDefPart() {
        if (battle != null) {

            String check = checkboxGroupDef.getChecked().getName();

            if (battle.getPlayer1DefanceList() != null)
                battle.getPlayer1DefanceList().clear();
            if (partOfBodies != null && partOfBodies.size() > 0)
                for (int i = 0; i < partOfBodies.size(); i++) {
                    if (String.valueOf(partOfBodies.get(i)).equals(check)) {
                        battle.getPlayer1DefanceList().add(PartOfBody.valueOf(checkboxGroupDef.getChecked().getName()));
                        battle.getPlayer1DefanceList().add(partOfBodies.get(i + 1));
                        //   checkboxGroupDef.setChecked(String.valueOf(partOfBodies.get(i)));
                        //   checkboxGroupDef.setChecked(String.valueOf(partOfBodies.get(i+1)));
                        break;
                    }
                }
        }
    }




}
