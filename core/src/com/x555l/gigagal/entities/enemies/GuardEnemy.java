package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bullets.enemyBullets.EnemyLaser;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum;
import com.x555l.gigagal.util.Util;

/**
 * Enemy move & stop alternatively
 * When detect ggg, it chase after and shoot
 */

public class GuardEnemy extends PatrolPlatformEnemy {
    private long lastTimeStop; // last time enemy stopped
    private float stopTime; // how long enemy will stop next time
    private float nextStop; // next time enemy will stop (since lastTimeStop)

    public GuardEnemy(Level level, Platform platform) {
        super(
                level,
                platform,
                true,
                Constants.Enemy.GUARD_ENEMY_HEALTH,
                Constants.Enemy.GUARD_ENEMY_SPEED,
                Constants.Enemy.GUARD_ENEMY_CENTER,
                Assets.instance.enemy.guardEnemy
        );

        boundary = new CircularShape();
        attackCoolDown = Constants.Enemy.GUARD_ENEMY_COOLDOWN;

        createRandomStop();
    }

    @Override
    public void update(float delta) {
        // detect ggg
        if (ableToDetectGigagal && detectGigagal()) {
            attack(delta, level.getGigagal().position);
        }

        // stop and move
        float time = Util.secondsSince(lastTimeStop);
        if (time > nextStop) {
            if (time > nextStop + stopTime)
                createRandomStop();
        } else {
            position.x += velocity.x * delta;
        }

        // create floating effect
        time = Util.secondsSince(startTime);
        position.y = platform.yTop + center.y
                + (float) Math.sin(2 * Math.PI / Constants.Enemy.GUARD_ENEMY_FLOAT_PERIOD * time) * Constants.Enemy.GUARD_ENEMY_FLOAT_AMPLITUDE;

        // change direction at platform edges
        changeDirectionAtEdges();
    }

    private void createRandomStop() {
        lastTimeStop = TimeUtils.nanoTime();
        nextStop = MathUtils.random(Constants.Enemy.GUARD_ENEMY_NEXT_STOP_MIN, Constants.Enemy.GUARD_ENEMY_NEXT_STOP_MAX);
        stopTime = MathUtils.random(Constants.Enemy.GUARD_ENEMY_STOP_TIME_MIN, Constants.Enemy.GUARD_ENEMY_STOP_TIME_MAX);
    }

    @Override
    void attack(float delta, Vector2 gigagal) {
        push(Constants.Enemy.GUARD_ENEMY_SPEED_BOOST, delta, gigagal);
        if (readyToAttack()) {
            lastTimeAttack = TimeUtils.nanoTime();
            level.getBullets().add(new EnemyLaser(
                    position,
                    level.getGigagal().position,
                    level
            ));
        }
    }
}
