package com.x555l.gigagal.entities.enemies;

import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;


/**
 * Enemy that has high health and patrols a platform
 */

public class StrongEnemy extends PatrolPlatformEnemy {
    public StrongEnemy(Level level, Platform platform) {
        super(
                level,
                platform,
                false,
                Constants.Enemy.STRONG_ENEMY_HEALTH,
                Constants.Enemy.STRONG_ENEMY_SPEED,
                Constants.Enemy.STRONG_ENEMY_CENTER,
                Assets.instance.enemy.strongEnemy
        );

        boundary = new RectangularShape();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        float time = Util.secondsSince(startTime);
        position.y = platform.yTop + center.y
                + (time % Constants.Enemy.STRONG_ENEMY_FLOAT_PERIOD) * Constants.Enemy.STRONG_ENEMY_FLOAT_AMPLITUDE;
    }
}
