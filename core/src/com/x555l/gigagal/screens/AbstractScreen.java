package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

/**
 * Screen adapter for other screens
 */

abstract class AbstractScreen implements Screen {
    Game game;
    Skin skin;

    Stage stage;

    private Batch batch;
    private Viewport backgroundViewport;
    private float worldSize;
    private TextureRegion background;

    AbstractScreen(Game game, float worldSize, TextureRegion background) {
        this.skin = Assets.instance.skin;
        this.game = game;
        this.worldSize = worldSize;
        this.background = background;
    }

    /**
     * Add layers to the screen by this method
     */
    abstract void createLayers(Stack stack);

    @Override
    public void show() {
        System.out.println(Gdx.graphics.getWidth());
        System.out.println(Gdx.graphics.getHeight());

        // new stage
        stage = new Stage(new ExtendViewport(worldSize, worldSize));
        stage.setDebugAll(true); // DEBUG
        Gdx.input.setInputProcessor(stage);

        // for background rendering
        batch = stage.getBatch();
        backgroundViewport = new StretchViewport(worldSize, worldSize);

        // add stack to stage
        Stack stack = new Stack();
        stack.setSize(worldSize * Constants.DeskTop.SCREEN_RATIO, worldSize);
        stage.addActor(stack);

        // add other widgets
        createLayers(stack);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        backgroundViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
//        // render background
//        backgroundViewport.apply();
//        batch.setProjectionMatrix(backgroundViewport.getCamera().combined);
//        batch.begin();
//        batch.draw(background, 0, 0);
//        batch.end();
//
//        stage.getViewport().apply();
        stage.draw();
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

    /**
     * Create a back to main menu button at top left of the screen,
     */
    Table createBackButtonLayer() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().pad(15);

        // new back button
        TextButton backButton = new TextButton("BACK", skin, "default");

        // add action
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(backButton);

        return table;
    }
}
