package Domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import State.BattleState;
import State.MainState;

import static API.Reconect.client;
import static com.badlogic.gdx.graphics.Color.ORANGE;

/**
 * Created by Maka on 13.12.2017.
 */

public class SearchBattleWindow extends Window {
    public SearchBattleWindow(Skin skin, TextButton backButton) {
        super("Search Battle...", skin);

        defaults().pad(2);

        TextButton  battleButton = backButton;
        add(battleButton).width(80).height(60);


        pack();
        setKeepWithinStage(true);


    }
}
