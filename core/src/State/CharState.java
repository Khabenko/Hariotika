package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;


import java.awt.Checkbox;
import java.awt.CheckboxGroup;

import API.Command;
import API.HariotikaMessage;
import API.WsCode;
import Domain.CharacteristicsWindows;
import Domain.EquipmentWindows;
import Domain.InventoryWindows;
import State.State;

import static API.Reconect.client;
import static State.MainState.getHealth;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class CharState extends State {
    static Stage stage;
    Table table;
    public static Texture ava;
    ProgressBar health;
    Texture skilBgr;
    EquipmentWindows equipmentWindows;
    TextButton  backButton;


    private Texture background;

    public CharState(final StateManager sm , Skin skin) {
        super(sm);
        skilBgr = new Texture("new.png");
        this.table = MainState.status;
        ava= new Texture("avatar/ava.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("playerFon.png");
        CharacteristicsWindows characteristics = new CharacteristicsWindows(skin);
        characteristics.setPosition(camera.viewportWidth*0.7f,camera.viewportHeight/3);
        characteristics.setBackground(new SpriteDrawable(new Sprite(skilBgr)));
        stage.addActor(characteristics);
        stage.addActor(MainState.status);


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



        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                     sm.set(new MainState(sm));


            };
        });



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
