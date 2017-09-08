package com.x555l.gigagal.entities.enemies;

import com.x555l.gigagal.level.Entity;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class FollowPathEnemy extends NonPlatformEnemy {

    public FollowPathEnemy(Entity entity) {
        super(
                entity.polygon,
                Constants.ENEMY_FOLLOW_PATH_HEALTH,
                Constants.ENEMY_FOLLOW_PATH_SPEED,
                Constants.ENEMY_FOLLOW_PATH_CENTER,
                Assets.instance.enemy.followPathEnemy
        );

        boundary = new RectangularShape();
    }
}
