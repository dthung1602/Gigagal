package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;

public class Bullet {
    public Vector2 position;
    private Facing direction;

    public boolean active;

    private Level level;

    public Bullet(float x, float y, Facing direction, Level level) {
        position = new Vector2(x, y);
        this.direction = direction;
        this.level = level;
        active = true;
    }

    public void update(float delta) {
        // move the bullet
        if (direction == Facing.LEFT) {
            position.x -= delta * Constants.BULLET_SPEED;
        } else {
            position.x += delta * Constants.BULLET_SPEED;
        }

        // check if bullet out of screen
        final float halfWorldWidth = level.getViewport().getWorldWidth() / 2;

        final float cameraX = level.getViewport().getCamera().position.x;

        if (position.x < cameraX - halfWorldWidth
                || position.x > cameraX + halfWorldWidth) {
            active = false;
        }

        // detect collision with enemy
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.position.dst(position) < Constants.ENEMY_HIT_RADIUS) {
                enemy.health--;
                level.addNewExplosion(position, false);
                active = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region;

        if (direction == Facing.LEFT)
            region = Assets.instance.bulletAssets.leftBullet;
        else
            region = Assets.instance.bulletAssets.rightBullet;

        batch.draw(
                region,
                position.x - Constants.BULLET_CENTER.x,
                position.y - Constants.BULLET_CENTER.y
        );
    }
}
