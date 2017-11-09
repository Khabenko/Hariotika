package Domain;

/**
 * Created by M.Khabenko on 09.11.2017.
 */
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;

public class FriendslistWindow extends Window {
    public FriendslistWindow (Skin skin) {
        super("Friendslist", skin);

        Array<String> namesArray = new Array<String>();
        for (int i = 0; i < 5; i++) {
            String name = " Friend_" + i;
            if (i % 3 == 0) {
                name = "[O] " + name;
            } else {
                name = "[X] " + name;
            }
            namesArray.add(name);
        }

        List<String> names = new List<String>(skin);
        names.setItems(namesArray);
        ScrollPane scroller = new ScrollPane(names, skin);

        add(scroller).height(500).width(200);

        pack();
        setKeepWithinStage(true);
    }
}
