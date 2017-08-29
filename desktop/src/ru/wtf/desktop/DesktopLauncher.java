package ru.wtf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.wtf.MyGdxGame;

public class DesktopLauncher {
	
    public static final int WIDTH = 480;
    public static final int HEIGHT = 700;

	public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Shullet";
        config.width = WIDTH;
        config.height = HEIGHT;
        new LwjglApplication(new MyGdxGame(), config);
    }

}
