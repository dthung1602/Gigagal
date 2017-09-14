package com.x555l.gigagal.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.inputProcessors.InputProcessor;
import com.x555l.gigagal.inputProcessors.KeyPressProcessor;
import com.x555l.gigagal.inputProcessors.TouchProcessor;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.level.LevelLoader;
import com.x555l.gigagal.overlays.EndLevelOverlay;
import com.x555l.gigagal.overlays.GameOverOverlay;
import com.x555l.gigagal.overlays.GigagalHUD;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.VictoryOverlay;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.ChaseCamera;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;


public class PlayScreen extends ScreenAdapter {
    private Game game;

    private SpriteBatch batch;
    private ChaseCamera chaseCamera;

    private Level level;
    private int levelNum;

    private GigagalHUD hud;
    private OnscreenControl onscreenControl;
    private InputProcessor inputProcessor;

    private VictoryOverlay victoryOverlay;
    private GameOverOverlay gameoverOverlay;
    private Stage pauseStage;

    private long levelEndOverLayerStartTime;

    private boolean useOnScreenControl;
    private boolean pause;

    PlayScreen(Game game, int levelNum) {
        this.game = game;
        this.levelNum = levelNum;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        hud = new GigagalHUD();
        victoryOverlay = new VictoryOverlay();
        gameoverOverlay = new GameOverOverlay();
        pauseStage = createPauseStage();

        useOnScreenControl = (Gdx.app.getType() == Application.ApplicationType.Android // on mobile
                || Configs.instance.isDebugOnScreenControlEnabled() // or debugging
        );

        if (useOnScreenControl) {
            onscreenControl = new OnscreenControl();
            inputProcessor = new TouchProcessor(onscreenControl);
        } else {
            inputProcessor = new KeyPressProcessor();
        }

        Gdx.input.setInputProcessor(inputProcessor);

        startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        level.getViewport().update(width, height, true); // this is also viewport in chase cam
        hud.getViewport().update(width, height, true);
        victoryOverlay.getViewport().update(width, height, true);
        gameoverOverlay.getViewport().update(width, height, true);
        if (useOnScreenControl) {
            onscreenControl.getViewport().update(width, height, true);
            onscreenControl.calculateButtonPosition();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void pause() {
        pause = true;
        Gdx.input.setInputProcessor(pauseStage);
    }

    @Override
    public void resume() {
        pause = false;
        Gdx.input.setInputProcessor(inputProcessor);
        inputProcessor.reset();
    }

    @Override
    public void hide() {
        Configs.instance.save();
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(
                Constants.GameWorld.BACKGROUND_COLOR.r,
                Constants.GameWorld.BACKGROUND_COLOR.g,
                Constants.GameWorld.BACKGROUND_COLOR.b,
                Constants.GameWorld.BACKGROUND_COLOR.a
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // check if game is paused or not
        if (inputProcessor.pauseKeyPressed)
            pause();

        // update game if not pause
        if (!pause) {
            level.update(delta);
            chaseCamera.update(delta);
        }

        // render game
        level.render(batch);
        hud.render(batch, level.getGigagal());
        if (useOnScreenControl)
            onscreenControl.render(batch);

        // render messages when level ends
        renderEndLevelOverlay();

        // render pause window
        if (pause)
            pauseStage.draw();
    }

    private Stage createPauseStage() {
        Skin skin = Assets.instance.skin;
        Window window = new Window("", skin);
        window.setColor(1, 1, 1, 0.8f);

        Viewport viewport = new ExtendViewport(Constants.GameWorld.GAME_WORLD_SIZE, Constants.GameWorld.GAME_WORLD_SIZE);
        Stage stage = new Stage(viewport, batch);
        stage.addActor(window);

        // title
        Label title = new Label("GAME PAUSED", skin);
        window.add(title).padBottom(15).row();

        // resume button
        TextButton resume = new TextButton("Resume", skin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().resume();
            }
        });
        window.add(resume).padBottom(5).prefWidth(100).row();

        // menu button
        TextButton menu = new TextButton("Menu", skin);
        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        window.add(menu).padBottom(5).prefWidth(100).row();

        // replay level
        TextButton replay = new TextButton("Replay", skin);
        replay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayScreen playScreen = (PlayScreen) game.getScreen();
                playScreen.startNewLevel();
                playScreen.resume();
            }
        });
        window.add(replay).padBottom(5).prefWidth(100).row();

        // pack and center
        window.setSize(200, 200);
        window.setPosition(
                stage.getWidth() / 2 - window.getWidth() / 2,
                stage.getHeight() / 2 - window.getHeight() / 2
        );

        return stage;
    }

    private void renderEndLevelOverlay() {
        EndLevelOverlay endLevelOverlay;

        if (level.victory) // win
            endLevelOverlay = victoryOverlay;
        else if (level.gameover) // lose
            endLevelOverlay = gameoverOverlay;
        else // playing --> do nothing
            return;

        // load layer for the first time
        if (levelEndOverLayerStartTime == 0) {
            endLevelOverlay.init();
            levelEndOverLayerStartTime = TimeUtils.nanoTime();
        }

        // render
        endLevelOverlay.render(batch);

        // end layer after a period of time
        if (Util.secondsSince(levelEndOverLayerStartTime) > Constants.EndLevelOverlay.DURATION) {
            levelComplete();
        }
    }

    private void levelComplete() {
        // if win
        if (level.victory)
            // TODO end of game, congrat player
            // unlock all level
            if (level.levelNum == Constants.Level.MAX_LEVEL) {
                levelNum = 1;
            } else {
                levelNum++; // load next level
                Configs.instance.setCurrentLevel(levelNum); // save to json object -> save to file
            }

        // if lose levelNum stays the same --> replay that level

        startNewLevel();
    }

    public void startNewLevel() {
        level = LevelLoader.load(levelNum);

        chaseCamera = new ChaseCamera(level.getViewport().getCamera(), level.getGigagal());
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // must resize to load viewports

        level.getGigagal().setInputProcessor(inputProcessor);
        inputProcessor.reset();

        pause = false;
        levelEndOverLayerStartTime = 0;
    }
}
