package com.x555l.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.x555l.gigagal.util.Assets;


public class Platform extends Rectangle {
    public float xRight;
    public float yTop;

    private NinePatch ninePatch;
    boolean passable;

    public Platform(boolean passable, float x, float y, float width, float height) {
        this.passable = passable;
        if (passable) {
            ninePatch = Assets.instance.platform.passablePlatform;
        } else {
            ninePatch = Assets.instance.platform.solidPlatform;
        }

        this.x = x;
        this.xRight = x + width;
        this.y = y;
        this.yTop = y + height;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch) {
        ninePatch.draw(
                batch,
                x, y,
                width, height
        );
    }
}
