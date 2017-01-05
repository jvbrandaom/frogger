package ufscar.cg.frogger.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;


public class MenuScreen extends Screen {

    private BitmapFont title;
    private Animation<Sprite> animation;
    private float elapsedTime = 0f;
    int y = 0;          // y used as an iterator to set the vertical position of the frog

    public MenuScreen (Frogger game) {
        super(game);
    }

    @Override
    public void createScreen() {
        title = new BitmapFont();

        // get sprites for frog standing and jumping
        if (elements.size() == 0) {
            Array<Sprite> sprites = new Array<Sprite>();
            Sprite sprite1 = new Sprite(ImageCache.getTexture("frog"));
            Sprite sprite2 = new Sprite(ImageCache.getTexture("frog_jump"));
            sprites.add(sprite1);
            sprites.add(sprite2);
            animation = new Animation<Sprite>(0.5f, sprites);
        }
    }

    @Override
    public void update(float dt) {

        // check for a click or the Enter key being pressed to start the game
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen("GameScreen");
        }
        else {
            GL20 gl = Gdx.gl;
            gl.glClearColor(1, 1, 1, 1);
            gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // exhibit message
            game.camera.update();
            game.batch.begin();
            title.setColor(Color.GREEN);
            title.draw(game.batch, "Frogger\n\nPress Enter or Click to Play", 240, 280);

            // animate frog
            y++;
            elapsedTime += dt;
            Sprite keyFrame = animation.getKeyFrame(elapsedTime, true);
            keyFrame.setY(y);
            keyFrame.setX(240);
            keyFrame.draw(game.batch);
            game.batch.end();
        }
    }
}

