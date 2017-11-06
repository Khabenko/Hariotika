package State;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Maka on 17.10.2017.
 */

public abstract class State {
   protected OrthographicCamera camera;
   protected Vector3 mouse;
   protected StateManager sm;

    public State(StateManager sm) {
        this.camera = new OrthographicCamera();
        this.mouse = new Vector3();
        this.sm = sm;
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
