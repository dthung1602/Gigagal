package com.x555l.gigagal;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.inputProcessor.InputProcessor;
import com.x555l.gigagal.inputProcessor.KeyPressProcessor;
import com.x555l.gigagal.inputProcessor.TouchProcessor;
import com.x555l.gigagal.overlays.EndLevelOverlay;
import com.x555l.gigagal.overlays.GameOverOverlay;
import com.x555l.gigagal.overlays.GigagalHUD;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.VictoryOverlay;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.ChaseCamera;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.LevelLoader;
import com.x555l.gigagal.util.Util;


class PlayScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private ChaseCamera chaseCamera;

    private Level level;

    private GigagalHUD hud;
    private OnscreenControl onscreenControl;
    private InputProcessor inputProcessor;

    private VictoryOverlay victoryOverlay;
    private GameOverOverlay gameoverOverlay;

    private long levelEndOverLayerStartTime;

    private boolean onMobile;

    @Override
    public void show() {
        Assets.instance.init();

        batch = new SpriteBatch();

        hud = new GigagalHUD();
        victoryOverlay = new VictoryOverlay();
        gameoverOverlay = new GameOverOverlay();

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
    public void render(float delta) {
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
        // TODO ???
        startNewLevel();
    }

    private void startNewLevel() {
        if (level == null) {
            level = LevelLoader.load(1); // begin of game, load level 1
        } else {
            if (level.victory)
                // TODO end of game, congrat player
                if (level.levelNum == Constants.MAX_LEVEL) {
                    level = LevelLoader.load(1);
                } else {
                    level = LevelLoader.load(level.levelNum + 1); // load next level
                }
            else
                level = LevelLoader.load(level.levelNum); // lose --> reload that level
        }

        chaseCamera = new ChaseCamera(level.getViewport().getCamera(), level.getGigagal());
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // must resize to init viewports

        level.getGigagal().setInputProcessor(inputProcessor);
        inputProcessor.reset();

        levelEndOverLayerStartTime = 0;
    }
}
