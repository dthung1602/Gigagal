package com.x555l.gigagal.entities.bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.GigaGal;


public abstract class Bonus {
    public Vector2 position;
    TextureRegion region;

    public void render(SpriteBatch batch) {
        batch.draw(
                region,
                position.x,
                position.y
        );
    }

    public abstract void performAction(GigaGal gigaGal);
}
