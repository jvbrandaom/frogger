package ufscar.cg.frogger;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.screens.Screen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Frogger extends ApplicationAdapter {
    public static final int GAME_STATE_PLAY = 0;
    public static final int GAME_STATE_PAUSE = 1;
    public static final int GAME_STATE_ANIMATE = 2;
    public OrthographicCamera camera;
    public Screen screen;
    public HashMap<String, Screen> screens;
    public SpriteBatch batch;
	Texture img;
    public int screenWidth = 0;
    public int screenHeight = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        super.create();

        camera = new OrthographicCamera(640, 480);
        camera.position.set(640 * 0.5f, 480 * 0.5f, 0);

        screenWidth = 640;
        screenHeight = 480;
        ImageCache.load();
        screens = new HashMap<String, Screen>();
        batch = new SpriteBatch();
        setScreen("MenuScreen");
    }

	@Override
	public void render () {
        if (screen != null) {
            screen.update(Gdx.graphics.getDeltaTime());
        } else {
            GL20 gl = Gdx.gl;
            gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    public void setScreen (String screenClassName) {

        screenClassName = "ufscar.cg.frogger.screens."+screenClassName;
        Screen newScreen = null;

        if (screens.containsKey(screenClassName) == false) {

            try {
                Class screenClass =  Class.forName(screenClassName);
                Constructor constructor = screenClass.getConstructor(Frogger.class);
                newScreen = (Screen) constructor.newInstance(this);
                screens.put(screenClassName, newScreen);
            } catch ( InvocationTargetException ex ){
                System.err.println( ex + " Screen with Wrong args in Constructor.");
            } catch ( NoSuchMethodException ex ){
            } catch ( ClassNotFoundException ex ){
                System.err.println( ex + " Screen Class Not Found.");
            } catch( InstantiationException ex ){
                System.err.println( ex + " Screen Must be a concrete class.");
            } catch( IllegalAccessException ex ){
                System.err.println( ex + " Screen with Wrong number of args.");
            }
        } else {
            newScreen = screens.get(screenClassName);
        }

        if (newScreen == null) return;

        if (screen != null) {
            //remove current screen!
            screen.destroy();
        }
        screen = newScreen;
        screen.createScreen();

    }
}
