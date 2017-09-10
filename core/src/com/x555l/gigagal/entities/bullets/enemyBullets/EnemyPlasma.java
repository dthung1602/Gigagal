package com.x555l.gigagal.entities.bullets.enemyBullets;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class EnemyPlasma extends EnemyBullet {
    public EnemyPlasma(Vector2 position, Vector2 target, Level level) {
        super(
                position.x,
                position.y,
                target.x,
                target.y,
                Constants.ENEMY_BULLET_PLASMA_CENTER,
                Constants.ENEMY_BULLET_PLASMA_SPEED,
                Constants.ENEMY_BULLET_PLASMA_DAMAGE,
                Assets.instance.bullet.enemyPlasma,
                level
        );
    }
}
