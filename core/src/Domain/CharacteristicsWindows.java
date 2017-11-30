package Domain;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import static API.Reconect.client;

/**
 * Created by M.Khabenko on 09.11.2017.
 */

public class CharacteristicsWindows extends Window {
    public CharacteristicsWindows (Skin skin) {
        super("Characteristics", skin);
        Array<String> namesArray = new Array<String>();
        ArrayMap<String,Integer> characteristics = new ArrayMap();

        characteristics.put("Strange",client.getCharacter().getStrength());
        characteristics.put("Agility",client.getCharacter().getAgility());
        characteristics.put("Vitality",client.getCharacter().getVitality());
        characteristics.put("Armor",client.getCharacter().getArmor());


        for (int i = 0; i < characteristics.size; i++) {
            String key = characteristics.getKeyAt(i);
            String value = String.valueOf(characteristics.getValueAt(i));
            namesArray.add(key+"  "+value);

        }

        List<String> names = new List<String>(skin);
        names.setItems(namesArray);
        ScrollPane scroller = new ScrollPane(names, skin);

        add(scroller).height(300).width(300);

        pack();
        setKeepWithinStage(true);
    }
}
