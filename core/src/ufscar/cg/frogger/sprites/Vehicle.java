package ufscar.cg.frogger.sprites;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.data.GameData;
import ufscar.cg.frogger.data.ImageCache;

public class Vehicle extends MovingSprite {

    public Vehicle (Frogger game, float x, float y, String textureRegion) {
        super(game, x, y);
        this.game = game;

        setVehicle(ImageCache.getTexture(textureRegion));
        setOrigin(getWidth() / 2, getHeight() / 2);
        game.screen.elements.add(this);
    }

    private void setVehicle(TextureRegion region){
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());
    }

}
