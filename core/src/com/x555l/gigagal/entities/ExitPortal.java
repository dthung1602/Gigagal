package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

public class ExitPortal {
    Vector2 position;
    private long startTime;

    public ExitPortal(float x, float y) {
        position = new Vector2(x, y);
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        float time = Util.secondsSince(startTime);
        TextureRegion region = Assets.instance.exitPortal.animation.getKeyFrame(time);

        batch.draw(
                region,
                position.x - Constants.EXIT_PORTAL_RADIUS,
                position.y - Constants.EXIT_PORTAL_RADIUS
        );
    }
}
