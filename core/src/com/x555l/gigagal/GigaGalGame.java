package com.x555l.gigagal;

import com.badlogic.gdx.Game;
import com.x555l.gigagal.screens.*;
import com.x555l.gigagal.util.Assets;


public class GigaGalGame extends Game {

    @Override
    public void create() {
        Assets.instance.init();
//        setScreen(new PlayScreen());
        setScreen(new MainMenu());
    }
}
