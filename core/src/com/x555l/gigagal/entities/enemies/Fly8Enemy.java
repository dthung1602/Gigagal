package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

/**
 * Enemy fly in a 8 path
 */

public class Fly8Enemy extends FlyEnemy {
    private boolean vertical;
    private Vector2 path8Center;

    public Fly8Enemy(float x, float y, boolean vertical) {
        super(
                Constants.ENEMY_FLY_8_HEALTH,
                Constants.ENEMY_FLY_8_CENTER,
                Assets.instance.enemy.fly8Enemy
        );

        this.vertical = vertical;
        this.position = new Vector2(x, y);
        boundary = new CircularShape();
        path8Center = new Vector2(x, y);
    }

    @Override
    public void update(float delta) {
        float time = Util.secondsSince(startTime);

        if (vertical) {
            position.y = path8Center.y +
                    (float) Math.sin(2 * Math.PI / Constants.ENEMY_FLY_8_PERIOD * time)
                            * Constants.ENEMY_FLY_8_LONG_AMPLITUDE;
            position.x = path8Center.x +
                    (float) Math.sin(4 * Math.PI / Constants.ENEMY_FLY_8_PERIOD * time)
                            * Constants.ENEMY_FLY_8_SHORT_AMPLITUDE;
        } else {
            position.x = path8Center.x +
                    (float) Math.sin(2 * Math.PI / Constants.ENEMY_FLY_8_PERIOD * time)
                            * Constants.ENEMY_FLY_8_LONG_AMPLITUDE;
            position.y = path8Center.y +
                    (float) Math.sin(4 * Math.PI / Constants.ENEMY_FLY_8_PERIOD * time)
                            * Constants.ENEMY_FLY_8_SHORT_AMPLITUDE;
        }
    }
}
