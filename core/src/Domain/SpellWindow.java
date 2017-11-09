package Domain;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class SpellWindow extends Window {
    public SpellWindow (Skin skin) {
        super("Spells", skin);

        defaults().pad(2);

        for (int i = 0; i < 8; i++) {
            final Button spell = new TextButton("S" + i, skin);
            spell.addListener(new TextTooltip("2222", skin));
            add(spell).width(52).height(52);
            final int finalI = i;
            spell.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("СПЕЛ: "+ finalI);
                    //	client1.sendMessage("Soket work");
                    //	stage.addActor(window);

                };
            });

        }


        pack();
        setKeepWithinStage(true);
        setMovable(false);
    }
}
