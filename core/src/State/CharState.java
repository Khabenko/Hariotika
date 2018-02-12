package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ArrayMap;
import com.google.gson.Gson;


import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;

import API.Command;
import API.HariotikaMessage;
import API.WsCode;
import Domain.AfterBattleWindow;
import Domain.CharacteristicsWindows;
import Domain.EquipmentWindows;
import Domain.InventoryWindows;
import State.State;
import javafx.beans.Observable;

import static API.Client.battle;
import static API.Client.character;
import static API.Reconect.client;
import static State.MainState.exp;
import static State.MainState.getHealth;
import static State.MainState.getMana;
import static State.MainState.playerName;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;
import static java.awt.SystemColor.text;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class CharState extends State {
    static Stage stage;
    Table status;
    public static Texture ava;
    ProgressBar health;
    Texture skilBgr;
    EquipmentWindows equipmentWindows;
    TextButton  backButton;
    TextButton resetPoint;
    private float oneX = camera.viewportWidth/100;
    private float oneY = camera.viewportWidth/100;
    String  point = "";
    Label freePoint;
    HariotikaMessage  hariotikaMessage;
    Gson gson;



    Skin skinChar;
    Label labelStrength;
    Label labelAgility;
    Label labelIntuition;
    Label labelWisdom;
    Label labelVitality;
    Label labelIntelligence;
    TextButton addStrength;
    TextButton addAgility;
    TextButton addIntuition;
    TextButton addWisdom;
    TextButton addVitality;
    TextButton addIntelligence;



    Label labelPhy_attack;
    Label labelArmor_penetration;
    Label labelArmor;
    Label labelCriticalchance;
    Label labelCriticalpower;
    Label labelParryChance;
    Label labelCounterattackChance;
    Label labelEvesionChance;
    Label labelDecreaseEnemyEvesion;
    Label labelDecreasePersenCrit;
    Label labelDecreasePowerCrit;



    private Texture background;

    public CharState(final StateManager sm , final Skin skin) {
        super(sm);
        skinChar = new Skin(Gdx.files.internal("data/visui1/uiskin.json"));
        skilBgr = new Texture("new.png");
        this.status = MainState.status;
        gson = new Gson();
        ava= new Texture("avatar/ava.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("playerFon.png");
        stage.addActor(status);

        /*
        final CharacteristicsWindows characteristics = new CharacteristicsWindows(skin);
        characteristics.setPosition(camera.viewportWidth*0.7f,camera.viewportHeight/3);
        characteristics.setBackground(new SpriteDrawable(new Sprite(skilBgr)));
        stage.addActor(characteristics);
        stage.addActor(MainState.status);
        */

        //Chareteristc label

        addStrength = new TextButton("Add",skin);;
        labelStrength = new Label("Strength " +character.getStrength(),skinChar);
        labelStrength.setPosition(oneX*70,oneY*45);
        addStrength.setPosition(labelStrength.getX()+oneX*15,labelStrength.getY()-oneY);
        addStrength.setSize(oneX*5,oneY*3);

        addAgility = new TextButton("Add",skin);
        labelAgility = new Label("Agility " +character.getAgility(),skinChar);
        labelAgility.setPosition(labelStrength.getX(),labelStrength.getY()-oneY*4);
        addAgility.setPosition(labelAgility.getX()+oneX*15,labelAgility.getY()-oneY);
        addAgility.setSize(oneX*5,oneY*3);

        addIntuition = new TextButton("Add",skin);
        labelIntuition= new Label("Intuition " +character.getIntuition(),skinChar);
        labelIntuition.setPosition(labelStrength.getX(),labelAgility.getY()-oneY*4);
        addIntuition.setPosition(labelIntuition.getX()+oneX*15,labelIntuition.getY()-oneY);
        addIntuition.setSize(oneX*5,oneY*3);

        addWisdom = new TextButton("Add",skin);
        labelWisdom = new Label("Wisdom"+character.getWisdom(),skinChar);
        labelWisdom.setPosition(labelStrength.getX(),labelIntuition.getY()-oneY*4);
        addWisdom.setPosition(labelWisdom.getX()+oneX*15,labelWisdom.getY()-oneY);
        addWisdom.setSize(oneX*5,oneY*3);

        addVitality = new TextButton("Add",skin);
        labelVitality= new Label("Vitality " +character.getVitality(),skinChar);
        labelVitality.setPosition(labelStrength.getX(),labelWisdom.getY()-oneY*4);
        addVitality.setPosition(labelVitality.getX()+oneX*15,labelVitality.getY()-oneY);
        addVitality.setSize(oneX*5,oneY*3);



        addIntelligence = new TextButton("Add",skin);
        labelIntelligence= new Label("Intelligence " +character.getIntelligence(),skinChar);
        labelIntelligence.setPosition(labelStrength.getX(),labelVitality.getY()-oneY*4);
        addIntelligence.setPosition(labelIntelligence.getX()+oneX*15,labelIntelligence.getY()-oneY);
        addIntelligence.setSize(oneX*5,oneY*3);





        stage.addActor(labelAgility);
        stage.addActor(labelIntuition);
        stage.addActor(labelVitality);
        stage.addActor(labelWisdom);
        stage.addActor(labelIntelligence);
        stage.addActor(labelStrength);
        stage.addActor(addStrength);
        stage.addActor(addAgility);
        stage.addActor(addIntuition);
        stage.addActor(addWisdom);
        stage.addActor(addVitality);
        stage.addActor(addIntelligence);

        if (character.getPointCharacteristics()<=0){
            setAddButtunVisible(false);
        }

        labelPhy_attack = new Label("Damage "+client.getCharacter().getPhy_attack(),skinChar);
        labelArmor = new Label("Armor "+client.getCharacter().getArmor(),skinChar);
        labelDecreasePersenCrit  = new Label("Decrease Persen Crit "+client.getCharacter().getDecreasePersen_Crit(),skinChar);
        labelDecreasePowerCrit = new Label("Decrease Power Crit "+client.getCharacter().getDecreasePower_Crit(),skinChar);
        labelArmor_penetration = new Label("Armor penetration "+client.getCharacter().getArmor_penetration(),skinChar);
        labelCriticalchance = new Label("Critical chance "+client.getCharacter().getChance_criticalPhyAttack(),skinChar);
        labelCriticalpower = new Label("Critical power "+client.getCharacter().getPower_criticalPhyAttack(),skinChar);
        labelParryChance = new Label("Parry chance "+client.getCharacter().getChance_parry(),skinChar);
        labelCounterattackChance = new Label("Counterattack chance "+client.getCharacter().getChance_counterattack(),skinChar);
        labelEvesionChance  = new Label("Evesion chance "+client.getCharacter().getEvesion(),skinChar);
        labelDecreaseEnemyEvesion = new Label("Decrease Enemy Evesion " +client.getCharacter().getDecreaseEnemyEvesion(),skinChar);


        labelPhy_attack.setPosition(labelStrength.getX()-oneX*40, labelStrength.getY());
        labelArmor.setPosition(labelStrength.getX()-oneX*40, labelPhy_attack.getY()-oneY*2);
        labelDecreasePersenCrit.setPosition(labelStrength.getX()-oneX*40, labelArmor.getY()-oneY*2);
        labelDecreasePowerCrit.setPosition(labelStrength.getX()-oneX*40, labelDecreasePersenCrit.getY()-oneY*2);


        labelEvesionChance.setPosition(labelStrength.getX()-oneX*40, labelDecreasePowerCrit.getY()-oneY*2);
        labelDecreaseEnemyEvesion .setPosition(labelStrength.getX()-oneX*40, labelEvesionChance.getY()-oneY*2);
        labelArmor_penetration.setPosition(labelStrength.getX()-oneX*40, labelDecreaseEnemyEvesion.getY()-oneY*2);


        labelCriticalchance.setPosition(labelStrength.getX()-oneX*40, labelArmor_penetration.getY()-oneY*2);
        labelCriticalpower.setPosition(labelStrength.getX()-oneX*40, labelCriticalchance.getY()-oneY*2);
        labelCounterattackChance.setPosition(labelStrength.getX()-oneX*40, labelCriticalpower.getY()-oneY*2);
        labelParryChance.setPosition(labelStrength.getX()-oneX*40, labelCounterattackChance.getY()-oneY*2);





        stage.addActor(labelPhy_attack);
        stage.addActor(labelArmor_penetration);
        stage.addActor(labelArmor);
        stage.addActor(labelCriticalchance);
        stage.addActor(labelCriticalpower);
        stage.addActor(labelParryChance);
        stage.addActor(labelCounterattackChance);
        stage.addActor(labelEvesionChance);
        stage.addActor(labelDecreaseEnemyEvesion);
        stage.addActor(labelDecreasePersenCrit);
        stage.addActor(labelDecreasePowerCrit);



        equipmentWindows = new EquipmentWindows(skin);
        equipmentWindows.setBackground(new SpriteDrawable(new Sprite(skilBgr)));
        equipmentWindows.setPosition(50,600);
        stage.addActor(equipmentWindows);

        InventoryWindows inventoryWindows = new InventoryWindows(skin);
        inventoryWindows.setBackground(new SpriteDrawable(new Sprite(skilBgr)));
        inventoryWindows.setPosition(50,200);
        stage.addActor(inventoryWindows);

        backButton = new TextButton("Back",skin);
        backButton.setPosition(camera.viewportWidth-camera.viewportWidth*0.05f,camera.viewportHeight-camera.viewportWidth*0.05f);
        backButton.setSize(80,60);
        stage.addActor(backButton);


        TextButton resetPoint = new TextButton("Reset",skin);
        resetPoint.setSize(oneX*7,oneY*5);
        resetPoint.setPosition(oneX*75,oneY*10);

        freePoint = new Label("Point "+point,skinChar);
        freePoint.setPosition(oneX*75,oneY*17);

        stage.addActor(freePoint);
        stage.addActor(resetPoint);





        addStrength.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Strength,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }


            };
        });


        addAgility.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Agility,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }

            };
        });



        addIntuition.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Intuition,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }

            };
        });


        addWisdom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Wisdom,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }

            };
        });




        addVitality.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Vitality,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }

            };
        });


        addIntelligence.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (character.getPointCharacteristics()>0) {
                    hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Intelligence,character);
                    client.sendMessage(gson.toJson(hariotikaMessage));
                }
                if (character.getPointCharacteristics()<=0){
                    setAddButtunVisible(false);
                }

            };
        });





        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sm.set(new MainState(sm));
            };
        });



        resetPoint.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                hariotikaMessage = new HariotikaMessage(Command.Characteristic, WsCode.Reset,character);
                client.sendMessage(gson.toJson(hariotikaMessage));
       //         setAddButtunVisible(true);


            };
        });


    }


    public void setAddButtunVisible(boolean visible){
        addStrength.setVisible(visible);
        addAgility.setVisible(visible);
        addIntuition.setVisible(visible);
        addWisdom.setVisible(visible);
        addVitality.setVisible(visible);
        addIntelligence.setVisible(visible);

    }



    @Override
    protected void handleInput() {
            /*
        if (Gdx.input.justTouched())
        {  camera.update();
           sm.set(new MainState(sm));

        }
 */
    }

    @Override
    public void update(float dt) {
         getHealth().setValue(client.getCharacter().getHP());
         getMana().setValue(client.getCharacter().getMP());

        labelStrength.setText("Strength " +character.getStrength());
        labelAgility.setText("Agility " +character.getAgility());
        labelIntuition.setText("Intuition " +character.getIntuition());
        labelWisdom.setText("Wisdom "+character.getWisdom());
        labelVitality.setText("Vitality " +character.getVitality());
        labelIntelligence.setText("Intelligence " +character.getIntelligence());


        labelArmor_penetration.setText("Armor penetration "+client.getCharacter().getArmor_penetration());;
        labelArmor.setText("Armor "+client.getCharacter().getArmor());
        labelCriticalchance.setText("Critical chance "+client.getCharacter().getChance_criticalPhyAttack());
        labelCriticalpower.setText("Critical power "+client.getCharacter().getPower_criticalPhyAttack());
        labelParryChance.setText("Parry chance "+client.getCharacter().getChance_parry());
        labelCounterattackChance.setText("Counterattack chance "+client.getCharacter().getChance_counterattack());
        labelEvesionChance.setText("Evesion chance "+client.getCharacter().getEvesion());
        labelDecreaseEnemyEvesion.setText("Decrease Enemy Evesion " +client.getCharacter().getDecreaseEnemyEvesion());
        labelDecreasePersenCrit.setText("Decrease Persen Crit "+client.getCharacter().getDecreasePersen_Crit());
        labelDecreasePowerCrit.setText("Decrease Power Crit "+client.getCharacter().getDecreasePower_Crit());
        labelPhy_attack.setText("Damage "+client.getCharacter().getPhy_attack());




        if (character.getPointCharacteristics()>0)
        setAddButtunVisible(true);

        freePoint.setText("Points "+String.valueOf(character.getPointCharacteristics()));




 handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.draw(ava, 120,618, 300, 220);
        sb.end();
        stage.draw();



    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();

    }


}
