package com.x555l.gigagal.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.inputProcessors.InputProcessor;
import com.x555l.gigagal.inputProcessors.KeyPressProcessor;
import com.x555l.gigagal.inputProcessors.TouchProcessor;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.level.LevelLoader;
import com.x555l.gigagal.overlays.EndLevelOverlay;
import com.x555l.gigagal.overlays.GameOverOverlay;
import com.x555l.gigagal.overlays.GigagalHUD;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.PauseGameOverlay;
import com.x555l.gigagal.overlays.VictoryOverlay;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.ChaseCamera;
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
    private PauseGameOverlay pauseGameOverlay;

    private long levelEndOverLayerStartTime;

    private boolean onMobile;
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
        pauseGameOverlay = new PauseGameOverlay(game, batch);

        onMobile = (Gdx.app.getType() == Application.ApplicationType.Android);

        if (onMobile) {
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
        if (onMobile) {
            onscreenControl.getViewport().update(width, height, true);
            onscreenControl.calculateButtonPosition();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.instance.dispose();
    }

    @Override
    public void pause() {
        pause = true;
        Gdx.input.setInputProcessor(pauseGameOverlay.stage);
        System.out.println("pause");
    }

    @Override
    public void resume() {
        pause = false;
        Gdx.input.setInputProcessor(inputProcessor);
        inputProcessor.reset();
        System.out.println("resume");
    }

    @Override
    public void render(float delta) {
        // check if game is paused or not
        if (inputProcessor.pauseKeyPressed)
            pause();
        if (pause) {
            pauseGameOverlay.render();
            return;
        }

        // update everything
        level.update(delta);
        chaseCamera.update(delta);

        // clear screen
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render everything
        level.render(batch);
        hud.render(batch, level.getGigagal());
        if (onMobile)
            onscreenControl.render(batch);

        // render messages when level ends
        renderEndLevelOverlay();
    }

    private void renderEndLevelOverlay() {
        EndLevelOverlay endLevelOverlay;

        if (level.victory) // win
            endLevelOverlay = victoryOverlay;
        else if (level.gameover) // lose
            endLevelOverlay = gameoverOverlay;
        else // playing --> do nothing
            return;

        // init layer for the first time
        if (levelEndOverLayerStartTime == 0) {
            endLevelOverlay.init();
            levelEndOverLayerStartTime = TimeUtils.nanoTime();
        }

        // render
        endLevelOverlay.render(batch);

        // end layer after a period of time
        if (Util.seccondsSince(levelEndOverLayerStartTime) > Constants.LEVEL_END_DURATION) {
            levelComplete();
        }
    }

    private void levelComplete() {
        if (level.victory)
            // TODO end of game, congrat player
            if (level.levelNum == Constants.MAX_LEVEL) {
                levelNum = 1;
            } else {
                levelNum++; // load next level
            }

        // if lose levelNum stays the same --> replay that level

        startNewLevel();
    }

    public void startNewLevel() {
        level = LevelLoader.load(levelNum);

        chaseCamera = new ChaseCamera(level.getViewport().getCamera(), level.getGigagal());
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // must resize to init viewports

        level.getGigagal().setInputProcessor(inputProcessor);
        inputProcessor.reset();

        pause = false;
        levelEndOverLayerStartTime = 0;
    }
}
