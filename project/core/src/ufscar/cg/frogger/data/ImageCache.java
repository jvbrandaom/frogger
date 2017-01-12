package ufscar.cg.frogger.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageCache {

    public static TextureAtlas atlas;

    public static void load () {
        String textureFile = "core/assets/data.txt";
        atlas = new TextureAtlas(Gdx.files.internal(textureFile), Gdx.files.internal("core/assets/"));
    }

    public static TextureRegion getTexture (String name) {
        return atlas.findRegion(name);
    }
}
