package ufscar.cg.frogger.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;

public class Alligator extends MovingSprite {

    public Alligator(Frogger game, float x, float y) {
        super(game, x, y);
        this.game = game;
        setAlligator(ImageCache.getTexture("alligator1"));
        game.screen.elements.add(this);
    }

    private void setAlligator(TextureRegion region) {
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
    }
}
