package ufscar.cg.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.sprites.TreeLog;
import ufscar.cg.frogger.sprites.MovingSprite;
import ufscar.cg.frogger.sprites.Player;
import ufscar.cg.frogger.sprites.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen extends Screen {
    private Player player;
    int len;
    float elapsedTime = 0f;
    List<Integer> waterTiers = new ArrayList(Arrays.asList(7, 8, 9, 10, 11, 12));

    public GameScreen(Frogger game) {
        super(game);
        elements = new ArrayList<>();
    }

    @Override
    public void createScreen() {
        player = new Player(game, 320, 0);
        if (elements.size() == 0) {
            elements.add(new Sprite(ImageCache.getTexture("background_640")));
        }

        initializeVehicles(6, 60, GameData.LEFT, 5);
        initializeVehicles(6, 40, GameData.RIGHT, 4);
        initializeVehicles(6, 50, GameData.LEFT, 3);
        initializeVehicles(6, 40, GameData.RIGHT, 2);
        //7-12
        initializeLogs(3, 50, GameData.LEFT, 7);
        initializeLogs(3, 50, GameData.RIGHT, 8);
        initializeLogs(3, 50, GameData.LEFT, 9);
        initializeLogs(3, 50, GameData.RIGHT, 10);
        initializeLogs(3, 50, GameData.LEFT, 11);
        initializeLogs(3, 50, GameData.RIGHT, 12);
    }

    private void initializeVehicles(int numberOfVehicles, float speed, int direction, int tierIndex) {
        for (int i = 0; i < numberOfVehicles; i ++) {
            Vehicle vehicle;
            if (direction == GameData.LEFT) {
               vehicle = new Vehicle(game, game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE);
            } else {
                vehicle = new Vehicle(game, GameData.TILE_SIZE + game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE);
                vehicle.flip(true, false);
            }
            vehicle.speed = speed * direction;
        }
    }

    private void initializeLogs(int numberOfVehicles, float speed, int direction, int tierIndex) {
        for (int i = 0; i < numberOfVehicles; i ++) {
            TreeLog treeLog;
            if (direction == GameData.LEFT) {
                treeLog = new TreeLog(game, game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE);
            } else {
                treeLog = new TreeLog(game, GameData.TILE_SIZE + game.screenWidth / numberOfVehicles * i,
                        tierIndex * GameData.TILE_SIZE);
                treeLog.flip(true, false);
            }
            treeLog.speed = speed * direction;
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
        Boolean collision;

        for (int i = 0; i < len; i++) {
            element = elements.get(i);
            if (element instanceof MovingSprite) {
                ((MovingSprite) element).update(dt);
            }
            element.draw(game.batch);
        }

        collision = checkCollision(dt);
        //If there is no collision in water region, it means that the player is not on a log, thus it's game over
        //Otherwise, it's a collision with a vehicle, so it's game over
        if (waterTiers.contains(player.tierIndex) && !collision) {
            gameOver();
        } else if (!waterTiers.contains(player.tierIndex) && collision) {
            gameOver();
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

    private Boolean checkCollision(float dt) {
        for (Sprite sprite : elements) {
            if (!(sprite instanceof MovingSprite)) {
                continue;
            }
            MovingSprite element = (MovingSprite) sprite;

            if (player.getBoundingRectangle().overlaps(element.getBoundingRectangle())) {
                if (element instanceof Vehicle) {
                    gameOver();
                    return true;
                }
                if (element instanceof TreeLog) {
                    System.out.println("I'm on a tree log!");
                    player.speed = element.speed;
                    player.update(dt);
                    return true;
                }
            }
        }
        return false;
    }

    private void gameOver() {
        System.out.println("Game Over");
        player.reset();
    }

}
