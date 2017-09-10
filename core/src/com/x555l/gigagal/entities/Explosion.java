package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

public class Explosion {
    private Vector2 position;
    private Animation<TextureRegion> animation;
    private long explosionStartTime;
    private float delayTime;
    public boolean finished;

    public Explosion(Vector2 position, boolean largeExplosion) {
        this.position = position.cpy();
        animation = largeExplosion ? Assets.instance.explosion.largeExplosion : Assets.instance.explosion.smallExplosion;
        explosionStartTime = TimeUtils.nanoTime();
        delayTime = 0;
        finished = false;
    }

    public void render(SpriteBatch batch) {
        float time = Util.secondsSince(explosionStartTime);

        if (time > delayTime) {
            if (animation.isAnimationFinished(time - delayTime))
                finished = true;
            else
                batch.draw(
                        animation.getKeyFrame(time),
                        position.x - Constants.EXPLOSION_CENTER.x,
                        position.y - Constants.EXPLOSION_CENTER.y
                );
        }
    }

    public void setDelayTime(float second) {
        delayTime = second;
    }
}
