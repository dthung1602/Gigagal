package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;


public class BasicEnemy extends Enemy {
    private Platform platform;

    public BasicEnemy(Platform platform) {
        super();

        this.platform = platform;
        health = Constants.ENEMY_HEALTH;
        textureRegion = Assets.instance.enemyAssets.region;

        position = new Vector2(platform.x, platform.yTop + Constants.ENEMY_CENTER.y);
        velocity = new Vector2(Constants.ENEMY_SPEED, 0);
    }

    @Override
    public void update(float delta) {
        // move the enemy horizontally
        position.x += velocity.x * delta;

        // create floating effect
        float time = Util.secondsSince(startTime);
        position.y = platform.yTop
                + Constants.ENEMY_CENTER.y
                + (float) Math.sin(2 * Math.PI / Constants.FLOAT_PERIOD * time) * Constants.FLOAT_AMPLITUDE;

        // change direction
        if (position.x < platform.x || position.x > platform.xRight) {
            velocity.x *= -1;
        }
    }
}
