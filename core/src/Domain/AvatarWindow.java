package Domain;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.graphics.Color.ORANGE;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class AvatarWindow extends Window {
    public AvatarWindow(Skin skin) {
        super("<Maka>", skin);

        defaults().pad(2);

        TextButton textButton = new TextButton("Avatar", skin);
        add(textButton).width(64).height(64);

        Table status = new Table(skin);

        status.add(new Label("Health", skin));
        ProgressBar health = new ProgressBar(0, 100, 1, false, skin);
        health.setValue(75);
        health.setColor(ORANGE);
        status.add(health).width(200);

        status.row();

        status.add(new Label("Mana", skin));
        ProgressBar mana = new ProgressBar(0, 100, 1, false, skin);
        mana.setValue(24);
        status.add(mana).width(200);

        add(status);

        pack();
        setKeepWithinStage(true);

      textButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Open PlayerState");
                //	client1.sendMessage("Soket work");
                //	stage.addActor(window);

            };
        });



    }
}
