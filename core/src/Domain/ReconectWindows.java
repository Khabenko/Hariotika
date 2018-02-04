package Domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import static API.Reconect.client;

/**
 * Created by Maka on 21.11.2017.
 */

public class ReconectWindows extends Window {
    public ReconectWindows(Skin skin) {
                    super("Reconect", skin);



            pack();
            setKeepWithinStage(true);
        }
    }

