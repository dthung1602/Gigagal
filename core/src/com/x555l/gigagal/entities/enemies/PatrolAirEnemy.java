package com.x555l.gigagal.entities.enemies;

import com.x555l.gigagal.level.Entity;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

/**
 * Enemy that follow a polyline to patrol the air
 */
public class PatrolAirEnemy extends NonPlatformEnemy {
    public PatrolAirEnemy(Level level, Entity entity) {
        super(
                level,
                entity.polyline,
                Constants.Enemy.ENEMY_PATROL_AIR_HEALTH,
                Constants.Enemy.ENEMY_PATROL_AIR_SPEED,
                Constants.Enemy.ENEMY_PATROL_AIR_CENTER,
                Assets.instance.enemy.patrolAirEnemy
        );

        boundary = new CircularShape();
    }
}
