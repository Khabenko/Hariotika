package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Checkbox;

import API.Client;
import Domain.Battle;
import Domain.Character;

import static API.Client.getBattle;
import static API.Reconect.client;
import static State.MainState.getHealth;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by Maka on 06.11.2017.
 */

public class BattleState extends State {


    static Character enemy;
    Skin skin;
    private Texture background;
    private Stage stage;
    TextButton battleButton;
    Table table;
    Table statusEnemy;
    static Label enemyName;
    static ProgressBar enemyhealth;
    static ProgressBar enemymana;
    static ProgressBar enemysp;

    public BattleState(StateManager sm, Skin skin, TextButton backButton) {
        super(sm);
        this.skin = skin;
        stage = new Stage();
        enemy= new Character();
        this.table = MainState.status;
        createEnemyBar();
        initEnemy();

        background = new Texture("fon2.png");
        Gdx.input.setInputProcessor(stage);
        stage.addActor(backButton);
        stage.addActor(table);

        final ButtonGroup checkboxGroupHit = new ButtonGroup();
        CheckBox checkboxHeadHit = new CheckBox("HEAD",skin);
        checkboxHeadHit.setName("HEAD");
        checkboxHeadHit.setPosition(10,camera.viewportHeight*0.85f);
        final CheckBox checkboxBodyHit = new CheckBox("BODY",skin);
        checkboxBodyHit.setName("BODY");
        checkboxBodyHit.setPosition(10,checkboxHeadHit.getY()-20);
        CheckBox checkboxLegsHit = new CheckBox("LEGS",skin);
        checkboxLegsHit.setName("LEGS");
        checkboxLegsHit.setPosition(10,checkboxBodyHit.getY()-20);
        checkboxGroupHit.add(checkboxBodyHit);
        checkboxGroupHit.add(checkboxHeadHit);
        checkboxGroupHit.add(checkboxLegsHit);
        checkboxGroupHit.setMaxCheckCount(1);


        stage.addActor(checkboxBodyHit);
        stage.addActor(checkboxHeadHit);
        stage.addActor(checkboxLegsHit);



        final ButtonGroup checkboxGroupDef = new ButtonGroup();
        CheckBox checkboxHeadDef = new CheckBox("HEAD",skin);
        checkboxHeadDef.setName("HEAD");
        checkboxHeadDef.setPosition(camera.viewportWidth*0.95f,camera.viewportHeight*0.85f);
        final CheckBox checkboxBodyDef = new CheckBox("BODY",skin);
        checkboxBodyDef.setName("BODY");
        checkboxBodyDef.setPosition(camera.viewportWidth*0.95f,checkboxHeadDef.getY()-20);
        CheckBox checkboxLegsDef = new CheckBox("LEGS",skin);
        checkboxLegsDef.setName("LEGS");
        checkboxLegsDef.setPosition(camera.viewportWidth*0.95f,checkboxBodyDef.getY()-20);

        checkboxGroupDef.add(checkboxBodyDef);
        checkboxGroupDef.add(checkboxHeadDef);
        checkboxGroupDef.add(checkboxLegsDef);
        checkboxGroupDef.setMaxCheckCount(1);

        stage.addActor(checkboxBodyDef);
        stage.addActor(checkboxHeadDef);
        stage.addActor(checkboxLegsDef);



        battleButton = new TextButton("Battle",skin);
        battleButton.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
        battleButton.setSize(150,80);
        stage.addActor(battleButton);


        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                        client.sendMessage("Battle#"+ getBattle().getNumber()+"#"+client.getCharacter().getName()+"#"+checkboxGroupHit.getChecked().getName()+"#"+checkboxGroupDef.getChecked().getName());

            };
        });

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        initEnemy();
        getHealth().setValue(client.getCharacter().getHP());
        if (enemy.getName()!=null) {
            enemyhealth.setValue(enemy.getHP());
           // System.out.println(enemy.getName());
            enemyName.setText(enemy.getName());
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
        statusEnemy.setPosition(camera.viewportWidth/2+400,camera.viewportHeight/1.05f);
        stage.addActor(statusEnemy);


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


    }


    public Character getEnemy() {
        return enemy;
    }

    public void setEnemy(Character enemy) {
        this.enemy = enemy;
    }
}
