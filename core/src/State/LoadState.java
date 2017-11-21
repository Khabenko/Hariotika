package State;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static API.Client.load;
import static com.hariotika.Hariotika.HEIGHT;
import static com.hariotika.Hariotika.WIDTH;

/**
 * Created by Maka on 06.11.2017.
 */

public class LoadState extends State {
    private Texture background;
    public LoadState(StateManager sm) {
        super(sm);
        background = new Texture("loading.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (load)
        sm.push(new MainState(sm));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
