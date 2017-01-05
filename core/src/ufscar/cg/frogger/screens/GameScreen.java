package ufscar.cg.frogger.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
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
    private String gameStatusMessage;
    Sprite msgGameOver;
    Sprite msgWin;
    float elapsedTime = 0f;
    float totalElapsedTime = 0f;

    List<Integer> waterTiers = new ArrayList(Arrays.asList(7, 8, 9, 10, 11, 12));
    Alligator alligator;
    Sprite alligatorKeyFrame;
    private Animation<Sprite> alligatorAnimation;

    public GameScreen(Frogger game) {
        super(game);
        elements = new ArrayList<Sprite>();
    }

    @Override
    public void createScreen() {
        score = new BitmapFont();
        lives = new BitmapFont();
        gameStatusMessage = "";
        gameStatus = new BitmapFont();
        gameStatus.getData().setScale(1.5f,1.5f);
        score.setColor(Color.WHITE);
        lives.setColor(Color.WHITE);

        player = new Player(game, 320, 0);
        if (elements.size() == 0) {
            elements.add(new Sprite(ImageCache.getTexture("background_640")));
        }



        Array<Sprite> alligatorSprites = new Array<Sprite>();
        alligatorSprites.add(new Sprite(ImageCache.getTexture("alligator1")));
        alligatorSprites.add(new Sprite(ImageCache.getTexture("alligator2")));
        alligatorAnimation = new Animation<Sprite>(0.2f, alligatorSprites);

        // alligator on final tier 13
        alligator = new Alligator(game, game.screenWidth/2, 13*GameData.TILE_SIZE);
        alligator.setAlpha(0f);
        alligator.speed = 70;

        // logs initialized for the water tiers
        //7-12
        initializeLogs(3, 50, GameData.RIGHT, 12, "wood2");
        initializeLogs(2, 40, GameData.LEFT, 11, "wood3");
        initializeLogs(3, 70, GameData.RIGHT, 10, "wood2");
        initializeLogs(2, 60, GameData.LEFT, 9, "wood4");
        initializeLogs(3, 50, GameData.RIGHT, 8, "wood3");
        initializeLogs(3, 60, GameData.LEFT, 7, "wood2");

        // vehicles initialized for the street tiers
        // tier 2-5
        initializeVehicles(4, 100, GameData.LEFT, 5, "car1");
        initializeVehicles(5, 60, GameData.RIGHT, 4, "car2");
        initializeVehicles(4, 70, GameData.LEFT, 3, "car1");
        initializeVehicles(4, 40, GameData.RIGHT, 2, "truck");

        msgGameOver = new Sprite(ImageCache.getTexture("msg_gameover"));
        msgGameOver.setX(230);
        msgGameOver.setY(240);
        msgGameOver.setAlpha(0f);
        elements.add(msgGameOver);

        msgWin = new Sprite(ImageCache.getTexture("msg_win"));
        msgWin.setX(230);
        msgWin.setY(240);
        msgWin.setAlpha(0f);
        elements.add(msgWin);
    }

    // initialize methods for vehicles and logs
    // each tier has a determined number of objects, speed, direction and sprite
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

        totalElapsedTime+=dt;

        // if the game is not paused, ie, is running, check for inputs
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

            // all sprites in movement are updated
            for (Sprite element : elements) {
                if (element instanceof MovingSprite) {
                    ((MovingSprite) element).update(dt);
                }
            }
            alligatorKeyFrame = alligatorAnimation.getKeyFrame(totalElapsedTime, true);
            alligatorKeyFrame.setPosition(alligator.getX(), alligator.getY());

            Boolean collision;
            collision = checkCollision(dt);
            // if there is a collision out of the water, it's a vehicle or an alligator, so it's gameover
            if(collision){
                if(!waterTiers.contains(player.tierIndex))
                    gameOver(1);
            }
            // if there's no collision and the player is on the water, it's game over
            else {
                if(waterTiers.contains(player.tierIndex)){
                    gameOver(1);
                }
                // if he's not on the water, check if he's reached the end
                else if (checkWin()){
                    gameOver(0);
                }
            }
        }
        // if the game is paused (after a gameover or a win) check for the Enter key
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player.reset();
            msgGameOver.setAlpha(0f);
            msgWin.setAlpha(0f);
            game.currentState = game.GAME_STATE_PLAY;
            game.gameData.score = 0;
        }

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.enableBlending();
        game.batch.begin();

        // draw all elements
        for (Sprite element : elements) {
            element.draw(game.batch);
        }

        alligatorKeyFrame.draw(game.batch);

        // handle player movements with or without animation
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

        // draw score message if the game is paused
        if (game.currentState == game.GAME_STATE_PAUSE) {
            gameStatus.draw(game.batch, gameStatusMessage, 200, 240);
        }
        game.batch.end();
    }

    private boolean checkWin() {
        return player.tierIndex == 13;
    }

    // check collision of player with vehicles or tree logs
    private Boolean checkCollision(float dt) {
        for (Sprite sprite : elements) {
            if (!(sprite instanceof MovingSprite)) {
                continue;
            }
            MovingSprite element = (MovingSprite) sprite;

            if (player.getBoundingRectangle().overlaps(element.getBoundingRectangle())) {
                if (element instanceof Vehicle) {
                    return true;
                }
                if (element instanceof TreeLog) {
                    // player should move with the tree log
                    player.speed = element.speed;
                    player.update(dt);
                    return true;
                }
                if (element instanceof Alligator) {
                    return true;
                }
            }
        }
        return false;
    }

    // accessed whenever player dies or reaches the end
    private void gameOver(int n) {
        // will be true when checkWin() is true
        if(n == 0){
            game.gameData.score += game.gameData.POINTS_PER_LIFE * player.getLives();
            msgWin.setAlpha(1f);
            gameStatus.setColor(Color.YELLOW);
            gameStatusMessage = "      Your Score was: " + game.gameData.score + "\nPress ENTER to play again";
            game.currentState = game.GAME_STATE_PAUSE;
        }
        // decreases number of lives, game is over if it's below zero
        else if(player.decLives() < 0) {
            msgGameOver.setAlpha(1f);
            gameStatus.setColor(Color.RED);
            gameStatusMessage = "      Your score was: " + game.gameData.score + "\nPress ENTER to play again";
            game.currentState = Frogger.GAME_STATE_PAUSE;
        }
        // player has not won or lost, so the position is reset
        else
            player.reset();
    }
}