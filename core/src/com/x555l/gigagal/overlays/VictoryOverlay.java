package com.x555l.gigagal.overlays;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.entities.Explosion;
import com.x555l.gigagal.util.Constants;

public class VictoryOverlay extends EndLevelOverlay {
    private Array<Explosion> explosions;

    public VictoryOverlay() {
        viewport = new ExtendViewport(Constants.GameWorld.GAME_WORLD_SIZE, Constants.GameWorld.GAME_WORLD_SIZE);
        font = new BitmapFont(Gdx.files.internal(Constants.EndLevelOverlay.FONT_FILE));
        font.getData().setScale(1.2f);
        font.setColor(Color.RED);
    }

    @Override
    public void init() {
        explosions = new Array<Explosion>();

        for (int i = 0; i < Constants.EndLevelOverlay.EXPLOSION_COUNT; i++) {
            float x = MathUtils.random(0, viewport.getWorldWidth());
            float y = MathUtils.random(0, viewport.getWorldHeight());
            float delay = MathUtils.random(0, Constants.EndLevelOverlay.DURATION);

            Explosion explosion = new Explosion(new Vector2(x, y), false);
            explosion.setDelayTime(delay);

            explosions.add(explosion);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for (Explosion explosion : explosions)
            explosion.render(batch);

        font.draw(
                batch,
                Constants.EndLevelOverlay.VICTORY_MESSAGE,
                viewport.getWorldWidth() / 8,
                viewport.getWorldHeight() * 3 / 4
        );

        batch.end();
    }
}
