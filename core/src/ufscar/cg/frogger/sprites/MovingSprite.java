package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;

public class MovingSprite extends Sprite {
    public float speed;
    Frogger game;
    public float initialX;

    public MovingSprite (Frogger game, float x, float y) {
        this.game = game;
        setX(x);
        setY(y);
        initialX = x;
    }

    public void update (float dt) {
        if (getX() > -getWidth() && speed < 0 || getX() < game.screenWidth && speed > 0) {
            setX(getX() + speed * dt);
        } else {
            if (speed < 0) {
                setX(game.screenWidth);
            } else {
                setX(-GameData.TILE_SIZE);
            }
        }
    }
}
