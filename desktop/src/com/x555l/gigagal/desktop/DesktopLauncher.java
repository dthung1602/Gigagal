package com.x555l.gigagal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.x555l.gigagal.GigaGalGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Gigagal";
        config.width = 900;
        config.height = 600;
        new LwjglApplication(new GigaGalGame(), config);
    }
}
