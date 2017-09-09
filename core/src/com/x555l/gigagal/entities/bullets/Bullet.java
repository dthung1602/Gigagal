package com.x555l.gigagal.entities.bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;

/**
 * Abstract class for all bullets
 */

public abstract class Bullet {
    public Vector2 position;
    public Vector2 velocity;

    public int damage;
    public boolean active;

    protected Level level;
    protected TextureRegion textureRegion;
    protected Vector2 center;

    protected Bullet(float x, float y, int damage, Vector2 center, Level level) {
        this.position = new Vector2(x, y);
        this.damage = damage;
        this.center = center;
        this.level = level;

        active = true;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        checkOutOfScreen();
        checkHitPlatform();
    }

    /**
     * Set out-of-screen bullets inactive
     */
    private void checkOutOfScreen() {
        final float halfWorldWidth = level.getViewport().getWorldWidth() / 2;
        final float cameraX = level.getViewport().getCamera().position.x;
        final float halfWorldHeight = level.getViewport().getWorldHeight() / 2;
        final float cameraY = level.getViewport().getCamera().position.y;

        if (position.x < cameraX - halfWorldWidth
                || position.x > cameraX + halfWorldWidth
                || position.y < cameraY - halfWorldHeight
                || position.y > cameraY + halfWorldHeight)
            active = false;
    }

    /**
     * Set bullets that hit non-passable platforms inactive
     */
    private void checkHitPlatform() {
        for (Platform platform : level.getPlatforms()) {
            if ((!platform.passable) && platform.contains(position)) {
                active = false;
                return;
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x - center.x,
                position.y - center.y
        );
    }
}
