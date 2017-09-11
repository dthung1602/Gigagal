package com.x555l.gigagal.entities.enemies;

import com.x555l.gigagal.level.Entity;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class FollowPathEnemy extends NonPlatformEnemy {

    public FollowPathEnemy(Level level, Entity entity) {
        super(
                level,
                entity.polygon,
                Constants.Enemy.FOLLOW_PATH_ENEMY_HEALTH,
                Constants.Enemy.FOLLOW_PATH_ENEMY_SPEED,
                Constants.Enemy.FOLLOW_PATH_ENEMY_CENTER,
                Assets.instance.enemy.followPathEnemy
        );

        boundary = new RectangularShape();
    }
}
