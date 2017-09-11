package com.x555l.gigagal.entities.enemies;

import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;


/**
 * Enemy that patrols a platform and does nothing else
 * Can cause damage when touched
 */
public class BasicEnemy extends PatrolPlatformEnemy {

    public BasicEnemy(Level level, Platform platform) {
        super(
                level,
                platform,
                false,
                Constants.Enemy.BASIC_ENEMY_HEALTH,
                Constants.Enemy.BASIC_ENEMY_SPEED,
                Constants.Enemy.BASIC_ENEMY_CENTER,
                Assets.instance.enemy.basicEnemy
        );

        boundary = new CircularShape();
    }

    @Override
    public void update(float delta) {
        // move <-->
        super.update(delta);

        // create floating effect
        float time = Util.secondsSince(startTime);
        position.y = platform.yTop + center.y
                + (float) Math.sin(2 * Math.PI / Constants.Enemy.BASIC_ENEMY_FLOAT_PERIOD * time) * Constants.Enemy.BASIC_ENEMY_FLOAT_AMPLITUDE;
    }
}
