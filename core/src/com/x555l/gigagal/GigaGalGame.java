package com.x555l.gigagal;

import com.badlogic.gdx.Game;
import com.x555l.gigagal.screens.MainMenuScreen;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;


public class GigaGalGame extends Game {

    @Override
    public void create() {
        Assets.instance.load();
        Configs.instance.load();
        setScreen(new MainMenuScreen(this));
    }
}