package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.screens.GameScreen;

public class MovingSprite extends Sprite {
    public static final int  UP = 0;
    public static final int  DOWN = 1;
    public static final int  LEFT = 2;
    public static final int  RIGHT = 3;

    public float vx = 0;
    public float vy = 0;
    public float nextX = 0;
    public float nextY = 0;
    public float speed;
    Frogger game;
    public float initialX;

    public MovingSprite (Frogger game, float x, float y) {
        this.game = game;
        setX(x);
        setY(y);
        initialX = x;
        nextX = x;
        nextY = y;
    }

    public float next_right () {
        return nextX + getWidth();
    }

    public float next_left () {
        return nextX;
    }

    public float next_top () {
        return nextY + getHeight();
    }

    public float next_bottom () {
        return nextY;
    }

    public Rectangle next_bounds () {
        return new Rectangle(next_left(), next_bottom(), getWidth(), getHeight());
    }

    public void update (float dt) {
        if (getX() > -getWidth()) {
            setX(getX() + speed * dt);
        } else {
            if (speed < 0) {
                setX(game.screenWidth);
            } else {
                setX(-GameData.TILE_SIZE);
            }
        }
    }

    public void place () {
        setX(nextX);
        setY(nextY);
    }
}
