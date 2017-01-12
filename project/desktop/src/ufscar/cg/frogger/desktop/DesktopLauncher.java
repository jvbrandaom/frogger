package ufscar.cg.frogger.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ufscar.cg.frogger.Frogger;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("core/assets/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Frogger(), config);
	}
}
