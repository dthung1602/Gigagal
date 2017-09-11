package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum;

/**
 * Enemies move quickly and are able to detect when ggg is on platform
 */

public class FastEnemy extends PatrolPlatformEnemy {
    public FastEnemy(Level level, Platform platform) {
        super(
                level,
                platform,
                true,
                Constants.Enemy.FAST_ENEMY_HEALTH,
                Constants.Enemy.FAST_ENEMY_SPEED,
                Constants.Enemy.FAST_ENEMY_CENTER,
                Assets.instance.enemy.fastEnemy
        );

        boundary = new CircularShape();
    }

    @Override
    void attack(float delta, Vector2 gigagalPosition) {
        push(Constants.Enemy.FAST_ENEMY_SPEED_BOOST, delta, gigagalPosition);
    }

    @Override
    public void render(SpriteBatch batch) {
        float angle;
        if (gigagalDetected)
            angle = Constants.Enemy.FAST_ENEMY_ANGLE * (facing == Enum.Facing.RIGHT ? 1 : -1);
        else
            angle = 0;

        batch.draw(
                textureRegion,
                position.x - center.x,
                position.y - center.y,
                center.x,
                center.y,
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                1,
                1,
                angle
        );
    }
}
