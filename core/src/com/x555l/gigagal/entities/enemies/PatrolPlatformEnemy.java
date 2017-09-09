package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum;

/**
 * Abstract class for enemies that patrol platforms
 */
abstract class PatrolPlatformEnemy extends Enemy {
    Platform platform;
    private boolean ableToDetectGigagal;
    boolean gigagalDetected;
    private float speed;

    /**
     * @param platform:      platform that enemy will patrol
     * @param health:        enemy's health
     * @param speed:         enemy's horizontal speed
     * @param center:        enemy's center's coordinate with respect to its bottom left pixel
     * @param textureRegion: texture region for rendering
     */
    PatrolPlatformEnemy(Level level, Platform platform, boolean ableToDetectGigagal, int health, float speed, Vector2 center, TextureRegion textureRegion) {
        super(level);

        this.ableToDetectGigagal = ableToDetectGigagal;
        this.health = health;
        this.speed = speed;
        this.platform = platform;
        this.center = center;
        this.textureRegion = textureRegion;

        position = new Vector2(platform.x, platform.yTop + center.y);
        velocity = new Vector2(speed, 0);
    }

    @Override
    public void update(float delta) {
        // detect ggg
        if (ableToDetectGigagal) {
            GigaGal gigaGal = level.getGigagal();

            if (gigaGal.getCurrentPlatform() == platform) {
                gigagalDetected = true;
                // follow ggg
                if (gigaGal.position.x < position.x) {
                    facing = Enum.Facing.LEFT;
                    velocity.x = -speed;
                    delta *= Constants.ENEMY_SPEED_BOOST;
                } else {
                    facing = Enum.Facing.RIGHT;
                    velocity.x = speed;
                    delta *= Constants.ENEMY_SPEED_BOOST;
                }
            } else {
                gigagalDetected = false;
            }
        }

        // move enemy horizontally
        position.x += velocity.x * delta;

        // change direction at platform edges
        if (position.x < platform.x) {
            velocity.x *= -1;
            position.x = platform.x;
            facing = Enum.Facing.RIGHT;
        } else if (position.x > platform.xRight) {
            velocity.x *= -1;
            position.x = platform.xRight;
            facing = Enum.Facing.LEFT;
        }
    }
}
