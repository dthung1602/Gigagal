package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class LevelSelectScreen extends MyScreen {
    LevelSelectScreen(Game game) {
        super(
                game,
                Constants.SELECT_LEVEL_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.selectLevel
        );
    }

    @Override
    void createButton(Table table) {

    }
}
