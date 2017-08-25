package com.x555l.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.overlays.GigagalHUD;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.ChaseCamera;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.LevelLoader;


class PlayScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private ChaseCamera chaseCamera;

    private Level level;

    private GigagalHUD hud;

    @Override
    public void show() {
        Assets.instance.init();

        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        level = LevelLoader.load("level1", viewport);

        chaseCamera = new ChaseCamera(viewport.getCamera(), level.getGigagal());

        hud = new GigagalHUD(level);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.viewport.update(width, height, true);
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

        level.render(batch);
        hud.render(batch);
    }
}
