package com.x555l.gigagal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.x555l.gigagal.screens.MainMenuScreen;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;

import java.lang.reflect.Method;


public class GigaGalGame extends Game {

    @Override
    public void create() {
        Assets.instance.load();
        Configs.instance.load();
        setScreen(new MainMenuScreen(this));
    }
}