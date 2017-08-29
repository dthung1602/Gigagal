package com.x555l.gigagal.overlays;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class OnscreenControl {
    private Viewport viewport;

    private Button[] buttons;

    public OnscreenControl() {
        viewport = new ExtendViewport(
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE,
                Constants.ONSCREEN_CONTROLS_VIEWPORT_SIZE
        );

        buttons = new Button[4];
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for (Button button : buttons) {
            button.render(batch);
        }

        batch.end();
    }

    public void calculateButtonPosition() {
        float radius = Constants.BUTTON_RADIUS;
        float width = viewport.getWorldWidth();
        // TODO farther buttons
        // leftButton
        buttons[0] = new Button(radius * 4 / 3, radius * 5 / 2, Assets.instance.onscreenControlAssets.leftButton);
        // rightButton
        buttons[1] = new Button(radius * 3.5f, radius * 1.25f, Assets.instance.onscreenControlAssets.rightButton);
        // jumpButton
        buttons[2] = new Button(width - radius * 4 / 3, radius * 5 / 2, Assets.instance.onscreenControlAssets.jumpButton);
        // shootButton
        buttons[3] = new Button(width - radius * 3.5f, radius * 1.25f, Assets.instance.onscreenControlAssets.shootButton);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public Button[] getButtons() {
        return buttons;
    }

    public class Button {
        private Vector2 position;
        private TextureRegion region;

        public int pointer;

        Button(float x, float y, TextureRegion region) {
            this.position = new Vector2(x, y);
            this.region = region;
            pointer = -1;
        }

        void render(SpriteBatch batch) {
            batch.draw(
                    region,
                    position.x - Constants.BUTTON_RADIUS,
                    position.y - Constants.BUTTON_RADIUS
            );
        }

        public boolean touched(Vector2 touchPosition) {
            return (position.dst(touchPosition) <= Constants.BUTTON_RADIUS);
        }
    }
}
