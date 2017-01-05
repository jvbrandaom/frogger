package ufscar.cg.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.data.ImageCache;
import ufscar.cg.frogger.sprites.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen extends Screen {
    private Player player;
    private BitmapFont score;
    private BitmapFont lives;
    private BitmapFont gameStatus;
    float elapsedTime = 0f;
    List<Integer> waterTiers = new ArrayList(Arrays.asList(7, 8, 9, 10, 11, 12));
    private String gameStatusMessage = "";

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

        score = new BitmapFont();
        lives = new BitmapFont();
        gameStatus = new BitmapFont();
        gameStatus.setColor(Color.RED);
        score.setColor(Color.WHITE);
        lives.setColor(Color.WHITE);
        initializeVehicles(4, 100, GameData.LEFT, 5, "car1");
        initializeVehicles(5, 60, GameData.RIGHT, 4, "car2");
        initializeVehicles(4, 70, GameData.LEFT, 3, "car1");
        initializeVehicles(4, 40, GameData.RIGHT, 2, "truck");
        //7-12
        initializeLogs(3, 60, GameData.LEFT, 7, "wood2");
        initializeLogs(3, 50, GameData.RIGHT, 8, "wood3");
        initializeLogs(2, 60, GameData.LEFT, 9, "wood4");
        initializeLogs(3, 70, GameData.RIGHT, 10, "wood2");
        initializeLogs(2, 40, GameData.LEFT, 11, "wood3");
        initializeLogs(3, 50, GameData.RIGHT, 12, "wood2");
    }

    private void initializeVehicles(int numberOfVehicles, float speed, int direction, int tierIndex, String textureRegion) {
        for (int i = 0; i < numberOfVehicles; i++) {
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

    private void initializeLogs(int numberOfLogs, float speed, int direction, int tierIndex, String textureRegion) {
        for (int i = 0; i < numberOfLogs; i++) {
            TreeLog treeLog;
            if (direction == GameData.LEFT) {
                treeLog = new TreeLog(game, game.screenWidth / numberOfLogs * i,
                        tierIndex * GameData.TILE_SIZE, textureRegion);
            } else {
                treeLog = new TreeLog(game, GameData.TILE_SIZE + game.screenWidth / numberOfLogs * i,
                        tierIndex * GameData.TILE_SIZE, textureRegion);
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

        if (game.currentState != game.GAME_STATE_PAUSE) {
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

            for (Sprite element : elements) {
                if (element instanceof MovingSprite) {
                    ((MovingSprite) element).update(dt);
                }
            }


            Boolean collision;
            collision = checkCollision(dt);
            //If there is no collision in water region, it means that the player is not on a log, thus it's game over
            //Otherwise, it's a collision with a vehicle, so it's game over
            if (waterTiers.contains(player.tierIndex) && !collision) {
                gameOver(1);
            } else if (!waterTiers.contains(player.tierIndex) && collision) {
                gameOver(1);
            }
            else if (checkWin()){
                gameOver(0);
            }
            //if (player.tierIndex == )
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player.reset();
            game.currentState = game.GAME_STATE_PLAY;
            game.gameData.score = 0;
        }

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.enableBlending();
        game.batch.begin();

        for (Sprite element : elements) {
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

        if(game.gameData.score < 0){
            game.gameData.score = 0;
        }
        score.draw(game.batch, "SCORE: " + game.gameData.score, 10, 470);
        score.draw(game.batch, "LIVES: " + player.getLives(), 120, 470);

        if (game.currentState == game.GAME_STATE_PAUSE) {
            gameStatus.draw(game.batch, gameStatusMessage, 240, 320);
        }

        game.batch.end();
    }

    private boolean checkWin() {
        return player.tierIndex == 13;
    }

    private Boolean checkCollision(float dt) {
        for (Sprite sprite : elements) {
            if (!(sprite instanceof MovingSprite)) {
                continue;
            }
            MovingSprite element = (MovingSprite) sprite;

            if (player.getBoundingRectangle().overlaps(element.getBoundingRectangle())) {
                if (element instanceof Vehicle) {
                    //gameOver();
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

    private void gameOver(int n) {
        if(n == 0){
            game.gameData.score += game.gameData.POINTS_PER_LIFE * player.getLives();
            gameStatusMessage = "You Won!\nYour Score was: " + game.gameData.score + "\nPress ENTER to play again";
            game.currentState = game.GAME_STATE_PAUSE;
        }
        else if(player.decLives() < 0) {
            System.out.println("Game Over");
            gameStatusMessage = "YOU DIED!\nYour score was: " + game.gameData.score + "\nPress ENTER to play again";
            game.currentState = Frogger.GAME_STATE_PAUSE;
        }
        else
            player.reset();
    }
}
