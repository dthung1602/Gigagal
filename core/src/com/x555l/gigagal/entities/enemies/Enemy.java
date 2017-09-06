package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;

/**
 * Abstract class for all enemies
 */
public abstract class Enemy {
    public Vector2 position;
    public Vector2 velocity;

    public int health;
    public long startTime;

    public Facing facing;

    TextureRegion textureRegion;

    Enemy() {
        startTime = TimeUtils.nanoTime();
        facing = Facing.LEFT;
    }

    abstract public void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x - Constants.ENEMY_BASIC_CENTER.x,
                position.y - Constants.ENEMY_BASIC_CENTER.y
        );
    }
}
