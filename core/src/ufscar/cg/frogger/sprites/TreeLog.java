package ufscar.cg.frogger.sprites;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.ImageCache;

public class TreeLog extends MovingSprite {

    TextureRegion region = ImageCache.getTexture("wood3");
    public int tierIndex;

    public TreeLog(Frogger game, float x, float y) {
        super(game, x, y);
        this.game = game;
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        game.screen.elements.add(this);
    }

}
