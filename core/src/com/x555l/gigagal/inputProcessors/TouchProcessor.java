package com.x555l.gigagal.inputProcessors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.OnscreenControl.Button;


public class TouchProcessor extends InputProcessor {
    /**
     * 0: left     *   1: right
     * 2: up       *   3: down
     * 4: jump     *   5: shoot
     */
    private Button buttons[];

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
                activateButton(i, pointer);
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
                deactivateButton(i);
                return true;
            }
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 position = viewport.unproject(new Vector2(screenX, screenY));
        int lastTouchedButton = -1;
        int currentlyTouchedButton = -1;

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].pointer == pointer) {
                lastTouchedButton = i;
            }
            if (buttons[i].touched(position)) {
                currentlyTouchedButton = i;
            }
        }

        // drag from nowhere
        if (lastTouchedButton == -1) {
            // to a button: activate that button
            if (currentlyTouchedButton >= 0)
                activateButton(currentlyTouchedButton, pointer);
        }

        // drag from a button
        else {
            // first deactivate that button
            deactivateButton(lastTouchedButton);

            // to another button: activate that button
            if (currentlyTouchedButton >= 0) {
                activateButton(currentlyTouchedButton, pointer);
            }
        }

        return true;
    }

    private void activateButton(int num, int pointer) {
        setButton(num, true, pointer);
    }

    private void deactivateButton(int num) {
        setButton(num, false, -1);
    }

    private void setButton(int num, boolean value, int pointer) {
        switch (num) {
            case 0:
                leftKeyPressed = value;
                break;
            case 1:
                rightKeyPressed = value;
                break;
            case 2:
                upKeyPressed = value;
                break;
            case 3:
                downKeyPressed = value;
                break;
            case 4:
                jumpKeyPressed = value;
                break;
            case 5:
                shootKeyPressed = value;
                break;
        }
        buttons[num].pointer = pointer;
    }
}
