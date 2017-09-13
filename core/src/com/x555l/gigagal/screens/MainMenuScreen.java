package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;


public class MainMenuScreen extends AbstractScreen {
    public MainMenuScreen(Game game) {
        super(
                game,
                Constants.MainMenu.MAIN_MENU_WORLD_SIZE
        );
    }

    @Override
    void createLayers(Stack stack) {
        stack.add(createBackgroundLayer());
        stack.add(createButtonLayer());
    }

    private Image createBackgroundLayer() {
        return new Image(Assets.instance.background.mainMenu);
    }

    private Table createButtonLayer() {
        // root table
        Table table = new Table();
        table.setFillParent(true);
        table.bottom().right().pad(15);

        // start button
        TextButton button = new TextButton("CONTINUE", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayScreen(game, Configs.instance.getCurrentLevel()));
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
                ConfirmWindow window = new ConfirmWindow("Quit", "Are you sure you want to quit?") {
                    @Override
                    void yes() {
                        Util.exitWithOutError();
                    }

                    @Override
                    void no() {
                    }
                };

            }
        });
        table.add(button).fillX();

        return table;
    }
}
