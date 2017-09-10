package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum;

/**
 * Abstract class for enemies that patrol platforms
 */
abstract class PatrolPlatformEnemy extends Enemy {
    Platform platform;
    private float speed;

    /**
     * @param platform:            platform that enemy will patrol
     * @param health:              enemy's health
     * @param ableToDetectGigagal: decide whether enemy can know if ggg is on its platform
     * @param speed:               enemy's horizontal speed
     * @param center:              enemy's center's coordinate with respect to its bottom left pixel
     * @param textureRegion:       texture textureRegion for rendering
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
        if (ableToDetectGigagal && detectGigagal()) {
            attack(delta, level.getGigagal().position);
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

    @Override
    boolean detectGigagal() {
        gigagalDetected = platform == level.getGigagal().getCurrentPlatform();
        return gigagalDetected;
    }

    /**
     * A convenient implementation of attack method for platform enemies
     * Enemy will move towards Gigagal and push
     */
    void push(float delta, Vector2 gigagal) {

        // change direction & velocity to follow ggg
        if (gigagal.x < position.x) {
            facing = Enum.Facing.LEFT;
            velocity.x = -speed;
        } else {
            facing = Enum.Facing.RIGHT;
            velocity.x = speed;
        }

        // move toward target
        position.x += velocity.x * delta * Constants.ENEMY_SPEED_BOOST;
    }
}
