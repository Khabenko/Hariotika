package State;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by Maka on 06.11.2017.
 */

public class MainState extends State {
    private Texture background;
    public MainState(StateManager sm) {
        super(sm);
        background = new Texture("fon2.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, WIDTH, HEIGHT);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
