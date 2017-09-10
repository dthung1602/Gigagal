package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bullets.enemyBullets.EnemyLaser;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

/**
 * Enemy patrol a platform and shoot at ggg when detected
 */

public class ShootEnemy extends PatrolPlatformEnemy {
    public ShootEnemy(Level level, Platform platform) {
        super(
                level,
                platform,
                true,
                Constants.ENEMY_SHOOT_HEALTH,
                Constants.ENEMY_SHOOT_SPEED,
                Constants.ENEMY_SHOOT_CENTER,
                Assets.instance.enemy.shootEnemy
        );

        boundary = new RectangularShape();
        attackCoolDown = Constants.ENEMY_SHOOT_COOLDOWN;
    }

    /**
     * Shoot laser at ggg when detects ggg
     */
    @Override
    void attack(float delta, Vector2 target) {
        if (readyToAttack()) {
            lastTimeAttack = TimeUtils.nanoTime();
            level.getBullets().add(new EnemyLaser(
                    position,
                    level.getGigagal().position.cpy().sub(0, 5),
                    level
            ));
        }
    }
}
