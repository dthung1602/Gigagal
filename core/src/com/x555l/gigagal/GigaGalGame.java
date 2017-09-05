package com.x555l.gigagal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.x555l.gigagal.screens.*;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;


public class GigaGalGame extends Game {

    @Override
    public void create() {
        Assets.instance.load();
        Configs.instance.load();
        debug();
        setScreen(new MainMenuScreen(this));
    }

    private void debug() {
        final String TAG = "GGG";
        Gdx.app.log(TAG, "---------- DEBUG CALLED -----------");

        Gdx.app.log(TAG, "" + Configs.instance.getCurrentLevel());
        Gdx.app.log(TAG, "" + Configs.instance.getBrightness());
        Gdx.app.log(TAG, "" + Configs.instance.isSoundEnabled());
        Gdx.app.log(TAG, "" + Configs.instance.getSoundVolume());
        Gdx.app.log(TAG, "" + Configs.instance.isMusicEnabled());
        Gdx.app.log(TAG, "" + Configs.instance.getMusicVolume());

        Gdx.app.log(TAG, "---------- DEBUG ENDED -----------");
    }
}
