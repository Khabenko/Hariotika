package Domain;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;



/**
 * Created by Maka on 09.12.2017.
 */

public class LogWindow extends Window {

    public LogWindow(Skin skin) {
        super("", skin);

        pack();
        setKeepWithinStage(true);
        setMovable(false);
    }



}
