package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Platform;

/**
 * Abstract class for enemies that patrol platforms
 */
abstract class PatrolPlatformEnemy extends Enemy {
    Platform platform;

    /**
     * @param platform:      platform that enemy will patrol
     * @param health:        enemy's health
     * @param speed:         enemy's horizontal speed
     * @param offset:        enemy's center's coordinate with respect to its bottom left pixel
     * @param textureRegion: texture region for rendering
     */
    PatrolPlatformEnemy(Platform platform, int health, float speed, Vector2 offset, TextureRegion textureRegion) {
        super();

        this.health = health;
        this.platform = platform;
        this.offset = offset;
        this.textureRegion = textureRegion;

        position = new Vector2(platform.x, platform.yTop + offset.y);
        velocity = new Vector2(speed, 0);
    }

    @Override
    public void update(float delta) {
        // move the enemy horizontally
        position.x += velocity.x * delta;

        // change direction
        if (position.x < platform.x || position.x > platform.xRight) {
            velocity.x *= -1;
        }
    }
}