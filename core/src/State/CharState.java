package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


import java.awt.Checkbox;
import java.awt.CheckboxGroup;

import Domain.CharacteristicsWindows;
import State.State;

import static API.Reconect.client;
import static State.MainState.getHealth;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class CharState extends State {
    Stage stage;
    Table table;
    Texture ava;
    ProgressBar health;


    private Texture background;

    public CharState(StateManager sm , Skin skin, TextButton backButton) {
        super(sm);

        this.table = MainState.status;
        ava= new Texture("avatar/ava.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = new Texture("fon2.png");
        CharacteristicsWindows characteristics = new CharacteristicsWindows(skin);
        characteristics.setPosition(200,200);
        stage.addActor(characteristics);
        stage.addActor(MainState.status);
        stage.addActor(backButton);


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
        sb.draw(ava, 10,camera.viewportHeight/1.10f, 120, 100);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();

    }
}
