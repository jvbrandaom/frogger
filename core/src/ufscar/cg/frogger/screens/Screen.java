package ufscar.cg.frogger.screens;


import ufscar.cg.frogger.Frogger;
import ufscar.cg.frogger.sprites.GameSprite;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen implements com.badlogic.gdx.Screen {


    public List<GameSprite> elements;
    protected Frogger game;

    public Screen (Frogger game) {
        this.game = game;
        elements = new ArrayList<GameSprite>();
    }

    public void pause () {};
    public void resume () {};
    public void dispose (){};
    public void hide (){};
    public void show (){};
    public void destroy () {};

    public abstract void createScreen ();
    public abstract void update (float dt);

    @Override
    public void render(float arg0) {
    }

    @Override
    public void resize(int arg0, int arg1) {
    }
}
