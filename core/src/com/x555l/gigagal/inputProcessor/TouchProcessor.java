package com.x555l.gigagal.inputProcessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.OnscreenControl.Button;

public class TouchProcessor extends InputProcessor {
    private Button buttons [];
    private Viewport viewport;

    public TouchProcessor(OnscreenControl control) {
        buttons = control.getButtons();
        viewport = control.getViewport();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 position = viewport.unproject(new Vector2(screenX, screenY));

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].touched(position)) {
                switch (i) {
                    case 0:
                        leftKeyPressed = true;
                        break;
                    case 1:
                        rightKeyPressed = true;
                        break;
                    case 2:
                        jumpKeyPressed = true;
                        break;
                    case 3:
                        shootKeyPressed=  true;
                        break;
                }
                break;
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 position = viewport.unproject(new Vector2(screenX, screenY));

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].touched(position)) {
                switch (i) {
                    case 0:
                        leftKeyPressed = false;
                        break;
                    case 1:
                        rightKeyPressed = false;
                        break;
                    case 2:
                        jumpKeyPressed = false;
                        break;
                    case 3:
                        shootKeyPressed=  false;
                        break;
                }
                break;
            }
        }

        return true;
    }
}
