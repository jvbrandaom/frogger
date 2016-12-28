package ufscar.cg.frogger.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageCache {

    public static Texture sheet;
    public static TextureAtlas atlas;

    public static void load () {
        //TODO: create data file containing images
//        String textureFile = "";
//        atlas = new TextureAtlas(Gdx.files.internal(textureFile), Gdx.files.internal("data"));
    }

    public static TextureRegion getTexture (String name) {
        return atlas.findRegion(name);
    }

    public static TextureRegion getFrame (String name, int index) {
        return atlas.findRegion(name, index);
    }
}
