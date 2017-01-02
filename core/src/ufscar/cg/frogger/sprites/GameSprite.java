package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;

public class GameSprite {

    public boolean active;
    public boolean visible;
    public float x = 0;
    public float y = 0;
    public int width = 0;
    public int height = 0;

    public TextureRegion skin;
    public Rectangle body;

    protected Frogger game;

    public GameSprite (Frogger game, float x, float y) {
        this.game = game;
        this.x = x;
        this.y = y;
        active = true;
        visible = true;
        skin = null;
    }

    public GameSprite (String skinName, Frogger game, float x, float y) {
        this.game = game;
        active = true;
        visible = true;
        this.x = x;
        this.y = y;
        setSkin (skinName);
    }

    public void setSkin (String skinName, int skinIndex) {
        setSkin (ImageCache.getFrame(skinName, skinIndex));
    }
    public void setSkin (String skinName) {
        setSkin (ImageCache.getTexture(skinName));
    }

    public void setSkin (TextureRegion texture) {
        this.skin = texture;
        width = skin.getRegionWidth();
        height = skin.getRegionHeight();
    }

    public float right () {
        return x + width;
    }

    public float left () {
        return x;
    }

    public float top () {
        return y + height;
    }

    public float bottom () {
        return y;
    }

    public Rectangle bounds () {
        return new Rectangle(x, y, width, height);
    }

    public void reset () {}
    public void update (float dt) {}
    public void show () {}
    public void hide () {}
    public void draw () {
        game.batch.draw(skin, x, y);
    }
}
