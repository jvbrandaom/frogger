package ufscar.cg.frogger.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.utils.Array;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.sprites.GameSprite;

import java.util.ArrayList;

public class MenuScreen extends Screen {

    private SpriteCache spriteCache;
    private int spriteCacheIndex;
    private BitmapFont title;
    private Animation<Sprite> animation;
    private float elapsedTime = 0f;
    int i = 0;

    public MenuScreen (Frogger game) {
        super(game);
    }

    @Override
    public void createScreen() {
        title = new BitmapFont();

        if (elements.size() == 0) {
            Array<Sprite> sprites = new Array<Sprite>();
            Sprite sprite2 = new Sprite(ImageCache.getTexture("frog"));
            Sprite sprite3 = new Sprite(ImageCache.getTexture("frog_jump"));
            sprites.add(sprite2);
            sprites.add(sprite3);
            animation = new Animation<Sprite>(0.5f, sprites);
        }
    }

    @Override
    public void update(float dt) {


        if (Gdx.input.justTouched()) {
            game.setScreen("GameScreen");

        } else {
            GL20 gl = Gdx.gl;
            gl.glClearColor(1, 1, 1, 1);
            gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.camera.update();
            game.batch.begin();
            title.setColor(Color.GREEN);
            title.draw(game.batch, "Frogger", 300, 300);
            game.batch.end();
            i++;
            elapsedTime += dt;
            Sprite keyFrame = animation.getKeyFrame(elapsedTime, true);
            keyFrame.setY(i);
            keyFrame.setX(240);
            game.batch.begin();
            keyFrame.draw(game.batch);
            //System.out.println(sprite.getRotation());
            game.batch.end();
        }

    }

}

