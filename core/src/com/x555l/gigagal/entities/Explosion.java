package com.x555l.gigagal.entities;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

public class Explosion {
    private Vector2 position;
    private Animation animation;
    private long explosionStartTime;
    private float delayTime;
    public boolean finished;

    public Explosion(Vector2 position, boolean largeExplosion) {
        this.position = position.cpy();
        animation = Assets.instance.explosionAssets.getExplosionLoop(largeExplosion);
        explosionStartTime = TimeUtils.nanoTime();
        delayTime = 0;
        finished = false;
    }

    public void render(SpriteBatch batch) {
        float time = Util.seccondsSince(explosionStartTime);

        if (time < delayTime)
            return;
        else if (animation.isAnimationFinished(time - delayTime))
            finished = true;
        else
            batch.draw(
                    animation.getKeyFrame(time),
                    position.x - Constants.EXPLOSION_CENTER.x,
                    position.y - Constants.EXPLOSION_CENTER.y
            );

//        System.out.println("time = " + time);
//        System.out.println("dtime = " + delayTime);
//        System.out.println("animation done = " + finished);
    }

    public void setDelayTime(float second) {
        delayTime = second;
    }
}
