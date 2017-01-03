package ufscar.cg.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.sprites.Player;
import ufscar.cg.frogger.sprites.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameScreen extends Screen {
    private Player player;
    int len;
    float elapsedTime = 0f;


    public GameScreen(Frogger game) {
        super(game);
        elements = new ArrayList<Sprite>();
    }

    @Override
    public void createScreen() {
        player = new Player(game, 320, 0);
        if (elements.size() == 0) {
            elements.add(new Sprite(ImageCache.getTexture("background_640")));
        }

        initializeVehicles(5, 90, GameData.LEFT, 5, "car1");
        initializeVehicles(6, 50, GameData.RIGHT, 4, "car2");
        initializeVehicles(5, 60, GameData.LEFT, 3, "car1");
        initializeVehicles(5, 30, GameData.RIGHT, 2, "truck");
    }

    private void initializeVehicles(int numberOfVehicles, float speed, int direction, int tierIndex, String textureRegion) {
        for (int i = 0; i < numberOfVehicles; i ++) {
            Vehicle vehicle;
            if (direction == GameData.LEFT) {
               vehicle = new Vehicle(game, game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE, textureRegion);
            } else {
                vehicle = new Vehicle(game, GameData.TILE_SIZE + game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE, textureRegion);
                vehicle.flip(true, false);
            }
            vehicle.speed = speed * direction;
        }
    }

    @Override
    public void update(float dt) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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


        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.enableBlending();
        game.batch.begin();
        len = elements.size();
        Sprite element;


        for (int i = 0; i < len; i++) {
            element = elements.get(i);
            if (element instanceof Vehicle) {
                ((Vehicle) element).update(dt);
            }
            element.draw(game.batch);
        }

        if (player.isMoving) {
            Sprite keyFrame = player.frogUp.getKeyFrame(elapsedTime, false);
            keyFrame.setPosition(player.getX(), player.getY());
            keyFrame.setRotation(player.getRotation());
            keyFrame.setScale(player.getScaleX(), player.getScaleY());
            if (!player.frogUp.isAnimationFinished(elapsedTime)) {
                elapsedTime += dt;
                keyFrame.draw(game.batch);
            } else {
                player.isMoving = false;
                elapsedTime = 0f;
                player.draw();
            }
        }
        else {
            player.draw();
        }

        game.batch.end();
    }

}
