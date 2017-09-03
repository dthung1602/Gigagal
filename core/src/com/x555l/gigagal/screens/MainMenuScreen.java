package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class MainMenu extends ScreenAdapter {
    private Game game;

    private Stage stage;
    private SpriteBatch batch;
    private Viewport backgroundViewport;

    public MainMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        // for background rendering
        batch = new SpriteBatch();
        backgroundViewport = new StretchViewport(
                Constants.MAIN_MENU_WORLD_SIZE,
                Constants.MAIN_MENU_WORLD_SIZE
        );

        // new stage
        stage = new Stage(new ExtendViewport(
                Constants.MAIN_MENU_WORLD_SIZE,
                Constants.MAIN_MENU_WORLD_SIZE)
        );
        Gdx.input.setInputProcessor(stage);

        // table to organize widgets
        Table table = new Table();
        table.setFillParent(true);
        table.debug();
        stage.addActor(table);

        // create buttons
        createButton(table);
    }

    private void createButton(Table table) {
        // load skin
        Skin skin = Assets.instance.skin;

        // start button
        TextButton button = new TextButton("CONTINUE", skin, "default");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO start game
                System.out.println("CLICKED!");
                ((TextButton) event.getTarget()).setText("HELLO!");
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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render(float delta) {
        // render background
        backgroundViewport.apply();
        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        batch.begin();
        batch.draw(Assets.instance.screenBackgroundAssets.mainMenu, 0, 0);
        batch.end();

        stage.getViewport().apply();
        stage.draw();
    }
}
