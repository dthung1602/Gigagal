package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;


abstract class MyScreen implements Screen {
    Game game;
    Skin skin;

    Stage stage;

    private Batch batch;
    private Viewport backgroundViewport;
    private float worldSize;
    private TextureRegion background;

    MyScreen(Game game, float worldSize, TextureRegion background) {
        this.skin = Assets.instance.skin;
        this.game = game;
        this.worldSize = worldSize;
        this.background = background;
    }

    /**
     * Add widgets of the screen by this method
     */
    abstract void createWidgets(Table table);

    @Override
    public void show() {
        // new stage
        stage = new Stage(new ExtendViewport(worldSize, worldSize));
        Gdx.input.setInputProcessor(stage);

        // for background rendering
        batch = stage.getBatch();
        backgroundViewport = new StretchViewport(worldSize, worldSize);

        // table to organize widgets
        Table table = new Table();
        table.setFillParent(true);
        table.debug(); // DEBUG del it
        stage.addActor(table);

        // create buttons
        createWidgets(table);
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
        batch.draw(background, 0, 0);
        batch.end();

        stage.getViewport().apply();
        stage.draw();

        // DEBUG del these
//        System.out.println("----------------------------------------------");
//        System.out.println("Gdx");
//        System.out.println(Gdx.graphics.getWidth());
//        System.out.println(Gdx.graphics.getHeight());
//        System.out.println("stage");
//        System.out.println(stage.getWidth());
//        System.out.println(stage.getHeight());
//        System.out.println("viewport world");
//        System.out.println(stage.getViewport().getWorldWidth());
//        System.out.println(stage.getViewport().getWorldHeight());
//        System.out.println("viewport screen");
//        System.out.println(stage.getViewport().getScreenWidth());
//        System.out.println(stage.getViewport().getScreenHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
