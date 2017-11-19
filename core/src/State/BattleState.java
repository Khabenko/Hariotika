package State;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Checkbox;

import static API.Reconect.client;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by Maka on 06.11.2017.
 */

public class BattleState extends State {
    private Texture background;
    private Stage stage;
    TextButton battleButton;

    public BattleState(StateManager sm, Skin skin) {
        super(sm);
        background = new Texture("fon2.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        final ButtonGroup checkboxGroup = new ButtonGroup();
        CheckBox checkboxHead = new CheckBox("HEAD",skin);
        checkboxHead.setName("HEAD");
        checkboxHead.setPosition(10,200);
        final CheckBox checkboxBody = new CheckBox("BODY",skin);
        checkboxBody.setName("BODY");
        checkboxBody.setPosition(10,170);
        CheckBox checkboxLegs = new CheckBox("LEGS",skin);
        checkboxLegs.setName("LEGS");
        checkboxLegs.setPosition(10,140);
        checkboxGroup.add(checkboxBody);
        checkboxGroup.add(checkboxHead);
        checkboxGroup.add(checkboxLegs);
        checkboxGroup.setMaxCheckCount(1);
        checkboxGroup.setUncheckLast(true);

        stage.addActor(checkboxBody);
        stage.addActor(checkboxHead);
        stage.addActor(checkboxLegs);

        battleButton = new TextButton("Battle",skin);
        battleButton.setPosition(camera.viewportWidth/2,camera.viewportHeight/2);
        battleButton.setSize(150,80);
        stage.addActor(battleButton);


        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
              //  System.out.println(checkboxGroup.getChecked().getName());

               client.sendMessage("Battle#123#Obama#"+checkboxGroup.getChecked().getName()+"#"+checkboxGroup.getChecked().getName());
             //   client.sendMessage("RegToBattle");
                //  client.sendMessage("Battle#"+client.getLogin());
            };
        });

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

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
}
