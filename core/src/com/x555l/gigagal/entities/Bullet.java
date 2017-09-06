package com.x555l.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.enemies.Enemy;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;


public class Bullet {
    private Vector2 position;
    private Vector2 velocity;

    public boolean active;

    private Level level;
    private TextureRegion region;

    public Bullet(float x, float y, Facing direction, Level level) {
        this.level = level;
        active = true;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        if (direction == null) {
            // up bullet
            region = Assets.instance.bullet.upBullet;
            velocity.y = Constants.BULLET_SPEED;
        } else if (direction == Facing.LEFT) {
            // left bullet
            region = Assets.instance.bullet.leftBullet;
            velocity.x = -Constants.BULLET_SPEED;
        } else {
            // right bullet
            region = Assets.instance.bullet.rightBullet;
            velocity.x = Constants.BULLET_SPEED;
        }
    }

    public void update(float delta) {
        // move the bullet
        position.mulAdd(velocity, delta);

        // check if bullet out of screen
        if (velocity.y == 0) {
            // for left/right bullet
            final float halfWorldWidth = level.getViewport().getWorldWidth() / 2;
            final float cameraX = level.getViewport().getCamera().position.x;

            if (position.x < cameraX - halfWorldWidth
                    || position.x > cameraX + halfWorldWidth) {
                active = false;
                return;
            }
        } else {
            // for up bullet
            final float halfWorldHeight = level.getViewport().getWorldHeight() / 2;
            final float cameraY = level.getViewport().getCamera().position.y;

            if (position.y > cameraY + halfWorldHeight) {
                active = false;
                return;
            }
        }

        // detect collision with non-passable platforms
        for (Platform platform : level.getPlatforms()) {
            if ((!platform.passable) && platform.contains(position)) {
                active = false;
                return;
            }
        }

        // detect collision with enemy
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.boundary.hitByBullet(position)) {
                enemy.health--;
                level.addNewExplosion(position, false);
                active = false;
                return;
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                position.x - Constants.BULLET_CENTER.x,
                position.y - Constants.BULLET_CENTER.y
        );
    }
}
