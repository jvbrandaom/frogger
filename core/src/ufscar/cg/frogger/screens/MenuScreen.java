package ufscar.cg.frogger.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.sprites.GameSprite;

public class MenuScreen extends Screen {

    private SpriteCache _spriteCache;
    private int _spriteCacheIndex;
    private BitmapFont title;


    public MenuScreen (Frogger game) {
        super(game);
    }

    @Override
    public void createScreen() {
        title = new BitmapFont();

//        if (elements.size() == 0) {
//            GameSprite logo = new GameSprite ("logo", _game, _game.screenWidth * 0.5f,  _game.screenHeight * 0.7f);
//            GameSprite label1 = new GameSprite ("label_how_to", _game, _game.screenWidth * 0.5f,  _game.screenHeight * 0.53f);
//            GameSprite label2 = new GameSprite ("label_instructions", _game, _game.screenWidth * 0.5f,  _game.screenHeight * 0.2f);
//            GameSprite label3 = new GameSprite ("label_tap", _game, _game.screenWidth * 0.5f,  _game.screenHeight * 0.02f);
//            GameSprite control = new GameSprite ("control", _game, _game.screenWidth * 0.5f,  _game.screenHeight * 0.4f);
//
//
//        /*
//        //OPTION 1: With SpriteBatch
//        elements.add(logo);
//        elements.add(label1);
//        elements.add(label2);
//        elements.add(label3);
//        elements.add(control);
//        */
//
//            //OPTION 2: With SpriteCache
//            _spriteCache = new SpriteCache();
//            _spriteCache.beginCache();
//            _spriteCache.add(logo.skin, logo.x, logo.y);
//            _spriteCache.add(label1.skin, label1.x, label1.y);
//            _spriteCache.add(label2.skin, label2.x, label2.y);
//            _spriteCache.add(label3.skin, label3.x, label3.y);
//            _spriteCache.add(control.skin, control.x, control.y);
//            _spriteCacheIndex = _spriteCache.endCache();
//
//        }

    }

    @Override
    public void update(float dt) {


        if (Gdx.input.justTouched()) {
            Gdx.app.log("A HIT!", "A MOST PALPABLE HIT");
            //game.setScreen("GameScreen");

        } else {
            GL20 gl = Gdx.gl;
            gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
            gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.camera.update();
            game.batch.begin();
            title.setColor(Color.GREEN);
            title.draw(game.batch, "Frogger", 300, 300);
            game.batch.end();

        /*
        //OPTION 1: With SpriteBatch
        _game.spriteBatch.setProjectionMatrix(_game.camera.combined);
        _game.spriteBatch.enableBlending();
        _game.spriteBatch.begin();

        int len = elements.size();
        GameSprite element;
        for (int i = 0; i < len; i++) {
            element = elements.get(i);
            _game.spriteBatch.draw(element.skin, element.x, element.y);
        }
        _game.spriteBatch.end();
        */

//            //OPTION 2: With SpriteCache
//            gl.glEnable(GL20.GL_BLEND);
//            gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            _spriteCache.setProjectionMatrix(game.camera.combined);
//            _spriteCache.begin();
//            _spriteCache.draw(_spriteCacheIndex);
//            _spriteCache.end();



        }

    }

}

