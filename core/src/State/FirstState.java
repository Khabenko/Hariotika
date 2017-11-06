package State;

/**
 * Created by Maka on 18.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.hariotika.Hariotika;

import java.io.IOException;

import javax.websocket.DeploymentException;

import API.Client;

import static hariotika.Hariotika.HEIGHT;
import static hariotika.Hariotika.WIDTH;


public class FirstState extends State {
    Stage stage;
    TextField textField;
    private Texture background;
    private Texture playBtn;
    Client client;
  //  private ImageButton button = new ImageButton();

    public FirstState(StateManager sm) {
        super(sm);
        background = new Texture("bgr.png");
        playBtn = new Texture("playbutton.png");
        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched())
        {
                 client.sendMessage("login");
        }


    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        sb.draw(playBtn, (WIDTH / 2) - (playBtn.getWidth() / 2), HEIGHT / 2);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

    }
}
