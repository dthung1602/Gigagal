package com.x555l.gigagal.overlays;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class GigagalHUD {
    private Viewport viewport;
    private BitmapFont font;

    public GigagalHUD() {
        viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(0.5f);
        font.setColor(Color.BLUE);
    }

    public void render(SpriteBatch batch, GigaGal gigagal) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        // health
        font.draw(
                batch,
                "Health: " + gigagal.health,
                Constants.HUD_HEALTH_OFFSET.x,
                viewport.getWorldHeight() - Constants.HUD_HEALTH_OFFSET.y
        );

        // bullet left
        font.draw(
                batch,
                "Bullet: " + gigagal.bullet,
                Constants.HUD_BULLET_OFFSET.x,
                viewport.getWorldHeight() - Constants.HUD_BULLET_OFFSET.y
        );

        // life
        for (int i = 0; i < gigagal.life; i++) {
            TextureRegion region = Assets.instance.gigagal.standingRight;
            batch.draw(
                    region,
                    viewport.getWorldWidth() - (i + 1) * Constants.HUD_LIFE_ICON_SIZE,
                    viewport.getWorldHeight() - Constants.HUD_LIFE_ICON_SIZE,
                    0,
                    0,
                    region.getRegionWidth(),
                    region.getRegionHeight(),
                    0.5f,
                    0.5f,
                    0
            );
        }

        batch.end();
    }

    public Viewport getViewport() {
        return viewport;
    }
}
