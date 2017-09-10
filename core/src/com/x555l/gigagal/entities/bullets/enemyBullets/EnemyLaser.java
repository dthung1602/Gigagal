package com.x555l.gigagal.entities.bullets.enemyBullets;


import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class EnemyLaser extends EnemyBullet {
    public EnemyLaser(Vector2 position, Vector2 target, Level level)  {
        super(
                position.x,
                position.y,
                target.x,
                target.y,
                Constants.EnemyBullet.ENEMY_BULLET_LASER_CENTER,
                Constants.EnemyBullet.ENEMY_BULLET_LASER_SPEED,
                Constants.EnemyBullet.ENEMY_BULLET_LASER_DAMAGE,
                Assets.instance.bullet.enemyLaser,
                level
        );
    }
}
