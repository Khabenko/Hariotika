package Domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by Maka on 22.11.2017.
 */

public class EquipmentWindows extends Window {
    TextureAtlas buttonAtlas;
    ImageButton.ImageButtonStyle avaButtonStyle;
    ImageButton avaButton;

    public EquipmentWindows(Skin skin) {
        super("Equipment",skin);
        defaults().pad(2);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("avatar/ava.txt"));
        skin.addRegions(buttonAtlas);
        avaButtonStyle = new ImageButton.ImageButtonStyle();
        avaButtonStyle.imageUp = skin.getDrawable("ava");
        avaButtonStyle.imageDown = skin.getDrawable("ava");
        avaButton = new ImageButton(avaButtonStyle);

        avaButton.addListener(new TextTooltip("2222", skin));
        add(avaButton ).width(52).height(52);
        row();
        avaButton = new ImageButton(avaButtonStyle);
        avaButton.addListener(new TextTooltip("2222", skin));
        add(avaButton ).width(52).height(52);
        row();
        avaButton = new ImageButton(avaButtonStyle);
        avaButton.addListener(new TextTooltip("2222", skin));
        add(avaButton ).width(52).height(52);
        row();
        avaButton = new ImageButton(avaButtonStyle);
        avaButton.addListener(new TextTooltip("2222", skin));
        add(avaButton ).width(52).height(52);

        pack();
        setKeepWithinStage(true);
        setMovable(false);
    }




    public void rest()
    {

    }
}
