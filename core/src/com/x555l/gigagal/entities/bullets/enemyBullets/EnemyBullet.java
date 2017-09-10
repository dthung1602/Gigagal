package com.x555l.gigagal.entities.bullets.enemyBullets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.bullets.Bullet;
import com.x555l.gigagal.level.Level;

/**
 * Abstract class for all enemies' bullets
 */

public abstract class EnemyBullet extends Bullet {

    /**
     * @param x:             x coordinate of bullet
     * @param y:             y coordinate of bullet
     * @param targetX:       x coordinate of the target of the bullet
     * @param targetY:       y coordinate of the target of the bullet
     * @param center:        center of the bullet, relative to (x,y) origin
     * @param speed:         bullet's speed (px/second)
     * @param damage         : damage caused by bullet
     * @param level          : contains other entities for bullet to interact
     * @param textureRegion: texture for static bullet image
     */
    EnemyBullet(float x, float y, float targetX, float targetY, Vector2 center, float speed, int damage, TextureRegion textureRegion, Level level) {
        super(x, y, damage, center, level);
        this.textureRegion = textureRegion;

        velocity = new Vector2(targetX - x, targetY - y).setLength(speed);
        angle = (float) (Math.atan2(velocity.y, velocity.x) / Math.PI * 180);
    }

    /**
     * Overloaded method for animated bullets
     *
     * @param animation: texture animation
     */
    EnemyBullet(float x, float y, float targetX, float targetY, Vector2 center, float speed, int damage, Animation<TextureRegion> animation, Level level) {
        super(x, y, damage, center, level);
        this.animation = animation;

        velocity = new Vector2(targetX - x, targetY - y).setLength(speed);
        angle = (float) (Math.atan2(velocity.y, velocity.x) / Math.PI * 180);
    }
}
