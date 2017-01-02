package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;

public class Player extends MovingSprite {

    public static final int MOVE_TOP = 0;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
    public Boolean dead = false;
    Animation frogUp;
    Animation frogSide;
    public int jumpSize = 32;

    public Player(Frogger game, float x, float y) {
        super(game, x, y);
        Array<Sprite> sprites = new Array<>();
        TextureRegion region = ImageCache.getTexture("frog");
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

    }

    public void moveFrogUp() {
        setY(getY() + jumpSize);
    }

    public void moveFrogDown() {
        setY(getY() - jumpSize);
    }

    public void moveFrogLeft() {
        setX(getX() - jumpSize);
    }

    public void moveFrogRight() {
        setX(getX() + jumpSize);
    }

    public void draw () {
        draw(game.batch);
    }
}
