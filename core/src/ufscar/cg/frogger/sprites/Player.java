package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
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
    public Animation<Sprite> frogUp;
    public int jumpSize = 32;
    public boolean isVisible;
    public Boolean isMoving;
    TextureRegion region = ImageCache.getTexture("frog");
    Sprite sprite1 = new Sprite(ImageCache.getTexture("frog_jump"));
    Sprite sprite2 = new Sprite(ImageCache.getTexture("frog"));

    public Player(Frogger game, float x, float y) {
        super(game, x, y);
        setPlayer();
        Array<Sprite> sprites = new Array<>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        frogUp = new Animation(0.1f, sprites);
        isVisible = true;
        isMoving = false;
    }

    private void setPlayer() {
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

    public void moveFrogUp() {
        setRotation(0f);
        setScale(1, 1);
        setY(getY() + jumpSize);
        isMoving = true;
    }

    public void moveFrogDown() {
        setRotation(0f);
        setScale(1, -1);
        setY(getY() - jumpSize);
        isMoving = true;
    }

    public void moveFrogLeft() {
        if (getScaleY() == -1) {
            setRotation(-90f);
        } else {
            setRotation(90f);
        }
        setX(getX() - jumpSize);
        isMoving = true;
    }

    public void moveFrogRight() {
        if (getScaleY() == -1) {
            setRotation(90f);
        } else {
            setRotation(-90f);
        }
        setX(getX() + jumpSize);
        isMoving = true;
    }

    public void draw () {
        draw(game.batch);
    }
}
