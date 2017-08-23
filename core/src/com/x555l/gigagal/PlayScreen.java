package com.x555l.gigagal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.ChaseCamera;
import com.x555l.gigagal.util.Constants;


class PlayScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private ExtendViewport viewport;
    private ChaseCamera chaseCamera;

    private Level level;

    @Override
    public void show() {
        Assets.instance.init();

        level = new Level();

        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        chaseCamera = new ChaseCamera(viewport.getCamera(), level.gigagal);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        viewport.apply();

        // clear screen
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a
        );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch);
    }
}
