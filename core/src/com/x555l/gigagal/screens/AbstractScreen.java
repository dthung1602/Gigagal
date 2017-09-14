package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;

/**
 * Screen adapter for other screens
 */

abstract class AbstractScreen implements Screen {
    Game game;
    Skin skin = Assets.instance.skin;

    Stage stage;
    private float worldSize;

    AbstractScreen() {
    }

    AbstractScreen(Game game, float worldSize) {
        this.game = game;
        this.worldSize = worldSize;
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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void show() {
        // new stage
        stage = new Stage(
                new ExtendViewport(worldSize, worldSize),
                new SpriteBatch()
        );
        stage.setDebugAll(Configs.instance.isDebugScreenLayoutEnabled());
        Gdx.input.setInputProcessor(stage);

        // add stack to stage
        Stack stack = new Stack();
        stack.setSize(worldSize * Constants.DeskTop.SCREEN_RATIO, worldSize);
        stage.addActor(stack);

        // add other widgets
        createLayers(stack);
    }

    /**
     * Add layers to the screen by this method
     */
    void createLayers(Stack stack) {
    }

    /**
     * Create a layer that has only a back to main menu button at top left of the screen
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

    //-----------------------------------------//
    //              INNER CLASS                //
    //-----------------------------------------//

    /**
     * Abstract class for confirm window
     * When an action requires confirmation, a new instance of this class should be created
     * It will add itself to the stage automatically and become visibel.
     * When Yes/No button is pressed, yes() and no() method will be invoked accordingly. Then the
     * window become invisible.
     */
    abstract class ConfirmWindow extends Window {

        /**
         * @param title:   window's title
         * @param message: confirm what?
         */
        ConfirmWindow(String title, String message) {
            super(title, skin);
            setVisible(true);

            // make window semi-transparent
            this.setColor(1, 1, 1, 0.8f);

            // label to display confirm message
            Label label = new Label(message, skin);
            this.add(label).pad(2).row();

            // yes button
            TextButton yesButton = new TextButton("Yes", skin);
            yesButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setVisible(false);
                    yes();
                }
            });
            this.add(yesButton).pad(15).prefWidth(50);

            // no button
            TextButton noButton = new TextButton("No", skin);
            noButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setVisible(false);
                    no();
                }
            });
            this.add(noButton).pad(15).prefWidth(50);

            // pack window and align center
            this.pack();
            setPosition(
                    stage.getWidth() / 2 - getWidth() / 2,
                    stage.getHeight() / 2 - getHeight() / 2
            );

            // finally add window to stage
            stage.addActor(this);
        }

        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);

            // disable/enable other gui components accordingly
            Stack stack = (Stack) stage.getActors().get(0);
            if (visible)
                stack.setTouchable(Touchable.disabled);
            else
                stack.setTouchable(Touchable.enabled);
        }

        abstract void yes();

        abstract void no();
    }
}
