package ufscar.cg.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.sprites.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen {

    public List<Sprite> elements;
    private Player player;
    int len;

    public GameScreen(Frogger game) {
        super(game);
        elements = new ArrayList<>();
    }

    @Override
    public void createScreen() {
        player = new Player(game, 320, 0);

        if (elements.size() == 0) {
            elements.add(new Sprite(ImageCache.getTexture("background_640")));
            elements.add(player);
        }

    }

    @Override
    public void update(float dt) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.moveFrogLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.moveFrogRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.moveFrogUp();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.moveFrogDown();
        }

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.enableBlending();
        game.batch.begin();

        len = elements.size();
        Sprite element;
        for (int i = 0; i < len; i++) {
            element = elements.get(i);
            element.draw(game.batch);
        }
        game.batch.end();

    }
}
