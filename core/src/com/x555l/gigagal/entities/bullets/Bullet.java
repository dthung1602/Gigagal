package com.x555l.gigagal.entities.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Constants;

/**
 * Abstract class for all bullets
 */

public abstract class Bullet {
    protected Vector2 position;
    protected Vector2 velocity;

    public boolean active;

    protected Level level;
    protected TextureRegion region;

    protected Bullet(float x, float y, Level level) {
        this.position = new Vector2(x, y);
        this.level = level;

        active = true;
    }

    abstract public void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                position.x - Constants.BULLET_CENTER.x,
                position.y - Constants.BULLET_CENTER.y
        );
    }
}
