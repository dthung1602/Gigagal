package com.x555l.gigagal.entities.bullets.gigagalBullets;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.Explosion;
import com.x555l.gigagal.entities.bullets.Bullet;
import com.x555l.gigagal.entities.enemies.Enemy;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;


public class GigagalBullet extends Bullet {

    public GigagalBullet(float x, float y, Facing direction, Level level) {
        super(
                x,
                y,
                Constants.GigagalBullet.GGG_BULLET_DAMAGE,
                Constants.GigagalBullet.GGG_BULLET_CENTER,
                level
        );

        velocity = new Vector2(0, 0);

        if (direction == null) {
            // up bullet
            textureRegion = Assets.instance.bullet.upBullet;
            velocity.y = Constants.GigagalBullet.GGG_BULLET_SPEED;
        } else if (direction == Facing.LEFT) {
            // left bullet
            textureRegion = Assets.instance.bullet.leftBullet;
            velocity.x = -Constants.GigagalBullet.GGG_BULLET_SPEED;
        } else {
            // right bullet
            textureRegion = Assets.instance.bullet.rightBullet;
            velocity.x = Constants.GigagalBullet.GGG_BULLET_SPEED;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        checkHitTarget();
    }

    private void checkHitTarget() {
        // detect collision with enemies
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.boundary.hitByBullet(position)) {
                enemy.health -= damage;
                level.getExplosions().add(new Explosion(position, false));
                active = false;
                return;
            }
        }
    }
}
