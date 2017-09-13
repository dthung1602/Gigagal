package com.x555l.gigagal.overlays;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;

public class GigagalHUD {
    private Viewport viewport;
    private BitmapFont font;

    public GigagalHUD() {
        viewport = new ExtendViewport(Constants.HUD.VIEWPORT_SIZE, Constants.HUD.VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(0.5f);
    }

    public void render(SpriteBatch batch, GigaGal gigagal) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        // life
        drawInfo(
                batch,
                Assets.instance.gigagal.standingRight,
                0.3f,
                gigagal.life,
                1
        );

        // health
        drawInfo(
                batch,
                Assets.instance.bonus.health,
                0.4f,
                gigagal.health,
                2
        );

        // bullet
        drawInfo(
                batch,
                Assets.instance.bullet.rightBullet,
                1f,
                gigagal.bullet,
                3
        );


        // FPS
        if (Configs.instance.isDebugFPSEnabled()) {
            int fps = Gdx.graphics.getFramesPerSecond();
            Color color;
            if (fps > 50)
                color = Color.GREEN;
            else if (fps > 40)
                color = Color.YELLOW;
            else if (fps > 30)
                color = Color.ORANGE;
            else color = Color.RED;

            font.setColor(color);
            font.draw(
                    batch,
                    "FPS: " + fps,
                    viewport.getWorldWidth() - Constants.HUD.FPS_OFFSET.x,
                    Constants.HUD.ICON_OFFSET.y
            );
        }

        batch.end();
    }

    private void drawInfo(SpriteBatch batch, TextureRegion icon, float scale, int quantity, int lineNum) {
        // icon
        batch.draw(
                icon,
                Constants.HUD.ICON_OFFSET.x,
                viewport.getWorldHeight() - Constants.HUD.ICON_OFFSET.y * lineNum,
                0,
                0,
                icon.getRegionWidth(),
                icon.getRegionHeight(),
                scale,
                scale,
                0
        );

        // text
        font.setColor(Constants.HUD.INFO_COLOR);
        font.draw(
                batch,
                "X " + quantity,
                Constants.HUD.TEXT_OFFSET.x,
                viewport.getWorldHeight() - Constants.HUD.TEXT_OFFSET.y * lineNum + Constants.HUD.Y_OFFSET
        );
    }

    public Viewport getViewport() {
        return viewport;
    }
}
