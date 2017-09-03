package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class SettingScreen extends MyScreen {
    public SettingScreen(Game game) {
        super(
                game,
                Constants.SETTING_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.setting
        );
    }


    void createButton(Table table) {

    }
}
