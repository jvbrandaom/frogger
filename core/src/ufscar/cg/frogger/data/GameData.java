package ufscar.cg.frogger.data;

import ufscar.cg.frogger.Frogger;

public class GameData {
    public static final int POINTS_JUMP = 10;
    public static final int POINTS_TARGET = 100;
    public static final int POINTS_FLY = 100;
    public static final int POINTS_BONUS = 200;

    public int score = 0;
    public int level = 1;
    public int lives = 3;
    public int gameMode;
    public int targetsReached = 0;
    public float tierSpeed1 = 1.0f;
    public float tierSpeed2 = 1.0f;

    public GameData (Frogger game) {
        gameMode = Frogger.GAME_STATE_PAUSE;
    }

    public void reset () {
        score  = 0;
        level = 1;
        lives = 3;
        tierSpeed1 = 1.0f;
        tierSpeed2 = 1.0f;
        targetsReached = 0;
    }
}
