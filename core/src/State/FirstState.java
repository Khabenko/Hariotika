package State;

/**
 * Created by Maka on 18.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hariotika.Hariotika;

import java.io.IOException;

import javax.websocket.DeploymentException;

import API.Client;

import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;



public class FirstState extends State {
    Stage stage;
    SpriteBatch batch;
    TextField textField;
    ImageButton button;
    Texture img;
    Texture player;
    private Texture background;
    private Texture playBtn;
    Client client;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    ImageButton.ImageButtonStyle textButtonStyle;
    //  private ImageButton button = new ImageButton();

    public FirstState(final StateManager sm) {
        super(sm);
        background = new Texture("bgr.png");
        playBtn = new Texture("playbutton.png");
        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout =10000;

        img = new Texture("fon2.png");
        player = new Texture("data/Player.png");
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("1.txt"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new ImageButton.ImageButtonStyle();
        textButtonStyle.imageUp = skin.getDrawable("b1");
        textButtonStyle.imageDown = skin.getDrawable("b2");
        textButtonStyle.checked = skin.getDrawable("b3");
        button = new ImageButton(textButtonStyle);
        button.setPosition(400,400);
        stage.addActor(button);
   //     Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "ws://localhost/", 8081, socketHints);


        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeploymentException e) {
            e.printStackTrace();
        }


        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button work");
                sm.set(new MainState(sm));
                if (client!=null)
                client.sendMessage("login");
            };
        });
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched())
        {
                // client.sendMessage("login");
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
        stage.draw();
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

    }
}
