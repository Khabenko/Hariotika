package Domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

import API.Client;
import State.CharState;

import static API.Reconect.client;
import static State.CharState.ava;


/**
 * Created by Maka on 28.11.2017.
 */

public class InventoryWindows  extends Window {

    public InventoryWindows(final Skin skin) {
        super("Inventory",skin);
        defaults().pad(2);

        for (int i = 0; i < 2; i++) {

                final Button spell = new TextButton("S" + i, skin);
                spell.addListener(new TextTooltip("2222", skin));
                add(spell).width(52).height(52);
                final int finalI = i;



            final Net.HttpResponseListener listener = new Net.HttpResponseListener() {
                public void handleHttpResponse (Net.HttpResponse httpResponse) {
                    HttpStatus status = httpResponse.getStatus();
                    if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
                        // it was successful
                    //    System.out.println(httpResponse.getResult());
                        byte [] avatar = httpResponse.getResult();
                        try {

                            InputStream in = new ByteArrayInputStream(avatar);
                            BufferedImage bImageFromConvert = ImageIO.read(in);
                            File outputfile = new File("avatar/ava.png");
                            ImageIO.write(bImageFromConvert, "png", outputfile);
                             } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        // do something else
                        System.out.println(status.getStatusCode());
                    }

                }


                @Override
                public void failed(Throwable throwable) {

                }

                @Override
                public void cancelled() {

                }
            };


                spell.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("СПЕЛ: "+ finalI);
                        System.out.println(client.getCharacter().getAvatar());
                        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:8081/").build();
                        Gdx.net.sendHttpRequest(httpRequest, listener);

                    };
                });



        }

        pack();
        setKeepWithinStage(true);
        setMovable(false);

    }

    public void updateAva(){
        ava = new Texture("ava.png");
    }

}
