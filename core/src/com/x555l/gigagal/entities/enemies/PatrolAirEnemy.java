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
                Constants.ENEMY_PATROL_AIR_HEALTH,
                Constants.ENEMY_PATROL_AIR_SPEED,
                Constants.ENEMY_PATROL_AIR_CENTER,
                Assets.instance.enemy.patrolAirEnemy
        );

        boundary = new CircularShape();
    }
}
