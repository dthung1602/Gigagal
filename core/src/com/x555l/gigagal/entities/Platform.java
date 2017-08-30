package com.x555l.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.x555l.gigagal.util.Assets;


public class Platform {
    private float width, height;
    public float left, right;
    public float bottom, top;

    public Platform(float left, float bottom, float width, float height) {
        this.left = left;
        this.right = left + width;
        this.bottom = bottom;
        this.top = bottom + height;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch) {
        Assets.instance.platformAssets.ninePatch.draw(
                batch,
                left, bottom,
                width, height
        );
    }
}
