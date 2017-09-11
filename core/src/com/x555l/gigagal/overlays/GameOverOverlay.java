package com.x555l.gigagal.overlays;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

public class GameOverOverlay extends EndLevelOverlay {
    private Array<StaticEnemy> enemies;

    public GameOverOverlay() {
        viewport = new ExtendViewport(Constants.GameWorld.GAME_WORLD_SIZE, Constants.GameWorld.GAME_WORLD_SIZE);
        font = new BitmapFont(Gdx.files.internal(Constants.EndLevelOverlay.FONT_FILE));
        font.getData().setScale(1.1f);
        font.setColor(Color.RED);
    }

    @Override
    public void init() {
        enemies = new Array<StaticEnemy>();

        for (int i = 0; i < Constants.EndLevelOverlay.ENEMY_COUNT; i++) {
            enemies.add(new StaticEnemy());
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for (StaticEnemy enemy : enemies) {
            enemy.render(batch);
        }

        font.draw(
                batch,
                Constants.EndLevelOverlay.GAME_OVER_MESSAGE,
                viewport.getWorldWidth() / 8,
                viewport.getWorldHeight() * 3 / 4
        );

        batch.end();
    }

    private class StaticEnemy {
        float x;
        float y;
        float delay;
        float angle;
        long startTime;

        StaticEnemy() {
            x = MathUtils.random(0, viewport.getWorldWidth());
            y = MathUtils.random(0, viewport.getWorldHeight());
            delay = MathUtils.random(0, Constants.EndLevelOverlay.DURATION);
            angle = MathUtils.random(0, (float) Math.PI * 2);
            startTime = TimeUtils.nanoTime();
        }

        void render(SpriteBatch batch) {
            float time = Util.secondsSince(startTime);
            if (time > delay) {
                batch.draw(
                        Assets.instance.enemy.basicEnemy,
                        x,
                        y + (float) Math.sin(2 * Math.PI / Constants.Enemy.BASIC_ENEMY_FLOAT_PERIOD * time + angle) * Constants.Enemy.BASIC_ENEMY_FLOAT_AMPLITUDE
                );
            }
        }
    }

}
