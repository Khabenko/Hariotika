package State;

/**
 * Created by Maka on 18.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.google.gson.Gson;
import com.hariotika.Hariotika;



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
    static Table status;
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
    TextButton battleButton;
    TextButton backButton;
    static Label playerName;
    static ProgressBar health;
    static ProgressBar mana;
    static ProgressBar sp;

    //  private ImageButton button = new ImageButton();


    public MainState(final StateManager sm) {
        super(sm);
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
        avaButton.setPosition(10,camera.viewportHeight/1.10f);
        stage.addActor(avaButton);
        //-----------------
        //--------Статусбар
        status = new Table(skin2);
        playerName = new Label("Player",skin2);

        status.add(playerName);
        status.row();

        status.add(new Label("HP", skin2));
        health = new ProgressBar(0, 100, 1, false, skin2);
        health.setValue(0);
        health.setColor(Color.FOREST);
        status.add(health).width(500);

        status.row();

        status.add(new Label("MP", skin2));
        mana = new ProgressBar(0, 100, 1, false, skin2);
        mana.setValue(24);
        mana.setColor(BLUE);
        status.add(mana).width(500);
        status.setPosition(420,510);

        status.row();

        status.add(new Label("SP", skin2));
        sp = new ProgressBar(0, 100, 1, false, skin2);
        sp.setValue(50);
        sp.setColor(Color.CHARTREUSE);
        status.add(sp).width(500);
        status.setPosition(400,camera.viewportHeight/1.05f);
        stage.addActor(status);
        //------------------

        //---------Спел панель
        SpellWindow spellWindow = new SpellWindow(skin2);
       // spellWindow.setPosition(200,30);
        spellWindow.setPosition(camera.viewportWidth/2,30);
      //  stage.addActor(spellWindow);

        //----------Панель друзей
        FriendslistWindow friendslistWindow = new FriendslistWindow(skin2);
        friendslistWindow.setPosition(50,camera.viewportHeight/10);
      //  stage.addActor(friendslistWindow);

        //----- Батл кнопка
        battleButton = new TextButton("Battle",skin2);
        battleButton.setPosition(camera.viewportWidth/20,camera.viewportHeight/10);
        battleButton.setSize(150,80);
        stage.addActor(battleButton);

        //----- Батл кнопка
        backButton = new TextButton("Back",skin2);
        backButton.setPosition(camera.viewportWidth-camera.viewportWidth*0.05f,camera.viewportHeight-camera.viewportWidth*0.05f);
        backButton.setSize(80,60);
        stage.addActor(backButton);




        avaButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Open PlayerState");
                CharState charState = new  CharState(sm,skin2,backButton);
                sm.set(charState);

            };
        });

        battleButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                client.sendMessage("RegToBattle");
                sm.set(new BattleState(sm,skin2,backButton) );
                //  client.sendMessage("Battle#"+client.getLogin());
            };
        });

        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sm.set(new MainState(sm));
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
        health.setValue(client.getCharacter().getHP());
        playerName.setText(client.getCharacter().getName());


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
        background.dispose();
        stage.dispose();
    }

    public static ProgressBar getHealth() {
        return health;
    }

    public static void setHealth(ProgressBar health) {
        MainState.health = health;
    }
}
