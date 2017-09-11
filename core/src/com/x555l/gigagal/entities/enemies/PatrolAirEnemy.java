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
                Constants.Enemy.PATROL_AIR_ENEMY_HEALTH,
                Constants.Enemy.PATROL_AIR_ENEMY_SPEED,
                Constants.Enemy.PATROL_AIR_ENEMY_CENTER,
                Assets.instance.enemy.patrolAirEnemy
        );

        boundary = new CircularShape();
    }
}
