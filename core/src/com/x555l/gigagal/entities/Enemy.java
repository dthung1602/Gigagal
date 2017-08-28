package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;

public class Enemy {
    private Platform platform;
    private float floatTime;
    private Facing direction;

    public Vector2 position;
    public int health;

    public Enemy(Platform platform) {
        this.platform = platform;
        position = new Vector2(platform.left, platform.top + Constants.ENEMY_CENTER.y);
        direction = Facing.RIGHT;
        floatTime = 0;
        health = Constants.ENEMY_HEALTH;
    }

    public void update(float delta) {
        // move the enemy
        if (direction == Facing.RIGHT) {
            position.x += delta * Constants.ENEMY_SPEED;
        } else {
            position.x -= delta * Constants.ENEMY_SPEED;
        }

        // create floating effect
        floatTime += delta;
        position.y = platform.top
                + Constants.ENEMY_CENTER.y
                + (float) Math.sin(2 * Math.PI / Constants.FLOAT_PERIOD * floatTime) * Constants.FLOAT_AMPLITUDE;

        // change direction
        if (position.x < platform.left) {
            direction = Facing.RIGHT;
        } else if (position.x > platform.right) {
            direction = Facing.LEFT;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                Assets.instance.enemyAssets.region,
                position.x - Constants.ENEMY_CENTER.x,
                position.y - Constants.ENEMY_CENTER.y
        );
    }
}
