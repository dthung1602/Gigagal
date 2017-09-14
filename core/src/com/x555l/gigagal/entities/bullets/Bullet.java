package com.x555l.gigagal.entities.bullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Util;

/**
 * Abstract class for all bullets
 */

public abstract class Bullet {
    public Vector2 position;
    public Vector2 velocity;

    public int damage;
    public boolean active;
    private long startTime;
    protected float angle;

    protected Level level;
    protected TextureRegion textureRegion;
    protected Animation<TextureRegion> animation;
    private Vector2 center;

    protected Bullet(float x, float y, int damage, Vector2 center, Level level) {
        this.position = new Vector2(x, y);
        this.damage = damage;
        this.center = center;
        this.level = level;

        active = true;
        startTime = TimeUtils.nanoTime();
        angle = 0;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        checkOutOfScreen();
        checkHitPlatform();
        updateTextureRegion();
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

    /**
     * Ensure consistency between animated & static bullet image
     */
    private void updateTextureRegion() {
        if (animation != null)
            textureRegion = animation.getKeyFrame(Util.secondsSince(startTime));
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x,
                position.y,
                center.x,
                center.y,
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                1,
                1,
                angle
        );
    }
}
