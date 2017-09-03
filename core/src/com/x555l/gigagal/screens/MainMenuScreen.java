package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class MainMenuScreen extends MyScreen {
    public MainMenuScreen(Game game) {
        super(
                game,
                Constants.MAIN_MENU_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.mainMenu
        );
    }

    @Override
    void createWidgets(Table table) {

        // start button
        TextButton button = new TextButton("CONTINUE", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO start game
                game.setScreen(new PlayScreen(game, 1));
            }
        });
        table.add(button).fillX().padBottom(5).padBottom(5).row();

        // select level button
        button = new TextButton("SELECT LEVEL", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });
        table.add(button).padBottom(5).fillX().row();

        // setting
        button = new TextButton("SETTING", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingScreen(game));
            }
        });
        table.add(button).fillX().padBottom(5).row();

        // quit
        button = new TextButton("QUIT", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });
        table.add(button).fillX();
    }
}
