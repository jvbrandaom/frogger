package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.data.ImageCache;

public class Player extends MovingSprite {
    public Animation<Sprite> frogUp;
    public int jumpSize = 32;
    public boolean isVisible;
    public Boolean isMoving;
    TextureRegion region = ImageCache.getTexture("frog");
    Sprite sprite1 = new Sprite(ImageCache.getTexture("frog_jump"));
    Sprite sprite2 = new Sprite(ImageCache.getTexture("frog"));
    public int tierIndex = 0;
    public boolean isOnALog;

    public Player(Frogger game, float x, float y) {
        super(game, x, y);
        setPlayer();
        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(sprite1);
        sprites.add(sprite2);
        frogUp = new Animation(0.1f, sprites);
        isVisible = true;
        isMoving = false;
        isOnALog = false;
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
        tierIndex += 1;
        System.out.println(tierIndex);
        game.gameData.score += 8;
    }

    public void moveFrogDown() {
        setRotation(0f);
        setScale(1, -1);
        setY(getY() - jumpSize);
        isMoving = true;
        tierIndex -= 1;
        System.out.println(tierIndex);
        game.gameData.score -= 10;
    }

    public void moveFrogLeft() {
        if (getScaleY() == -1) {
            setRotation(-90f);
        } else {
            setRotation(90f);
        }
        setX(getX() - jumpSize);
        isMoving = true;
        game.gameData.score -= 1;
    }

    public void moveFrogRight() {
        if (getScaleY() == -1) {
            setRotation(90f);
        } else {
            setRotation(-90f);
        }
        setX(getX() + jumpSize);
        isMoving = true;
        game.gameData.score -= 1;
    }

    public void draw () {
        draw(game.batch);
    }

    public void reset() {
        tierIndex = 0;
        isOnALog = false;
        setPosition(320, 0);
    }
}
