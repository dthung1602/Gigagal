package com.x555l.gigagal.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.x555l.gigagal.GigaGalGame;
import com.x555l.gigagal.util.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Gigagal";
        config.width = Constants.DeskTop.SCREEN_WIDTH;
        config.height = Constants.DeskTop.SCREEN_HEIGHT;
        config.resizable = false;
        new LwjglApplication(new GigaGalGame(), config);
    }
}
