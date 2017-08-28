package com.x555l.gigagal.overlays;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class EndLevelOverlay {
    Viewport viewport;
    BitmapFont font;

    public abstract void init();

    public abstract void render(SpriteBatch batch);

    public Viewport getViewport() {
        return viewport;
    }
}
