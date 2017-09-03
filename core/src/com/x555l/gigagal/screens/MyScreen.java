package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;


abstract class Screen extends ScreenAdapter {
    private Game game;

    private Stage stage;

    private SpriteBatch batch;
    private Viewport backgroundViewport;
    private float worldSize;
    private TextureRegion background;

    /**
     * Add widgets of the screen by this method
     */
    abstract void createButton(Table table);

    @Override
    public void show() {
        // for background rendering
        batch = new SpriteBatch();
        backgroundViewport = new StretchViewport(worldSize, worldSize);

        // new stage
        stage = new Stage(new ExtendViewport(worldSize, worldSize));
        Gdx.input.setInputProcessor(stage);

        // table to organize widgets
        Table table = new Table();
        table.setFillParent(true);
        table.debug(); // DEBUG del it
        stage.addActor(table);

        // create buttons
        createButton(table);
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
