package Domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static API.Reconect.client;
import static com.badlogic.gdx.graphics.Color.BLUE;

/**
 * Created by Maka on 18.11.2017.
 */

public class GlobalUpdate extends Thread {
    static Table status;
    ProgressBar health;
    ProgressBar mana;
    ProgressBar sp;
    protected OrthographicCamera camera;
    Skin skin;

    public GlobalUpdate() {
        skin = new Skin(Gdx.files.internal("data/visui/uiskin.json"));
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        status = new Table(skin);
        status.add(new Label("HP", skin));
        health = new ProgressBar(0, client.getCharacter().getMaxHP(), 1, false, skin);
        health.setValue(client.getCharacter().getHP());
        health.setColor(Color.FOREST);
        status.add(health).width(500);

        status.row();

        status.add(new Label("MP", skin));
        ProgressBar mana = new ProgressBar(0, 100, 1, false, skin);
        mana.setValue(24);
        mana.setColor(BLUE);
        status.add(mana).width(500);
        status.setPosition(420,510);

        status.row();

        status.add(new Label("SP", skin));
        ProgressBar sp = new ProgressBar(0, 100, 1, false, skin);
        sp.setValue(50);
        sp.setColor(Color.CHARTREUSE);
        status.add(sp).width(500);
        status.setPosition(400,camera.viewportHeight/1.05f);
    }

    @Override
    public void run() {


    }
}
