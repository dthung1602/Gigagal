package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class Powerup {
    Vector2 position;

    public Powerup(float x, float y) {
        position = new Vector2(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                Assets.instance.powerupAssets.region,
                position.x,// - Constants.POWERUP_CENTER.x,
                position.y// - Constants.POWERUP_CENTER.y
        );
    }
}
