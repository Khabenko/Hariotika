package State;

/**
 * Created by Maka on 18.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import com.hariotika.Hariotika;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.websocket.DeploymentException;

import API.Client;
import API.Reconect;
import Domain.FriendslistWindow;
import Domain.SpellWindow;

import static API.Reconect.client;
import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;



public class MainState extends State {
    Reconect reconect;
    Gson gson;
    Stage stage;
    SpriteBatch batch;
    //-------
    ImageButton avaButton;
    private Texture background;
    Skin skin2;
    Skin skin;
    TextureAtlas buttonAtlas;
    ImageButton.ImageButtonStyle avaButtonStyle;
    ImageButton.ImageButtonStyle imgButtonStyle;

    //  private ImageButton button = new ImageButton();


    public MainState(final StateManager sm) {
        super(sm);
        reconect = new Reconect();
        reconect.start();
        gson = new Gson(); //Передать дальше
        background = new Texture("darckbgr.png");
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin2 = new Skin(Gdx.files.internal("data/visui/uiskin.json"));
        //Создание анимированной кнопки
    /*
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
     */
        //-------- Кнопка аватарка
        buttonAtlas = new TextureAtlas(Gdx.files.internal("avatar/ava.txt"));
        skin.addRegions(buttonAtlas);
        avaButtonStyle = new ImageButton.ImageButtonStyle();
        avaButtonStyle.imageUp = skin.getDrawable("ava");
        avaButtonStyle.imageDown = skin.getDrawable("ava");
        ImageButton avaButton = new ImageButton(avaButtonStyle);
        avaButton.setSize(120,100);
        stage.addActor(avaButton);
        //-----------------
        //--------Статусбар
        final Table status = new Table(skin2);

        status.add(new Label("HP", skin2));
        ProgressBar health = new ProgressBar(0, 100, 1, false, skin2);
        health.setValue(75);
        health.setColor(Color.FOREST);
        status.add(health).width(500);

        status.row();

        status.add(new Label("MP", skin2));
        ProgressBar mana = new ProgressBar(0, 100, 1, false, skin2);
        mana.setValue(24);
        mana.setColor(BLUE);
        status.add(mana).width(500);
        status.setPosition(420,510);

        status.row();

        status.add(new Label("SP", skin2));
        ProgressBar sp = new ProgressBar(0, 100, 1, false, skin2);
        sp.setValue(50);
        sp.setColor(Color.CHARTREUSE);
        status.add(sp).width(500);
        status.setPosition(400,500);

        avaButton.setPosition(10,440);
        stage.addActor(status);
        //------------------

        //---------Спел панель
        SpellWindow spellWindow = new SpellWindow(skin2);
        spellWindow.setPosition(200,30);
        stage.addActor(spellWindow);

        //----------Панель друзей
        FriendslistWindow friendslistWindow = new FriendslistWindow(skin2);
        friendslistWindow.setPosition(800,500);
        stage.addActor(friendslistWindow);

        avaButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Open PlayerState");
                CharState charState = new  CharState(sm,skin2,status);
                sm.set(charState);

            };
        });



      //  System.out.printf(gson.toJson(client));


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
        sb.end();
        stage.draw();

    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }



}
