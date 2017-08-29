package com.x555l.gigagal.inputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.overlays.OnscreenControl;
import com.x555l.gigagal.overlays.OnscreenControl.Button;

public class TouchProcessor extends InputProcessor {
    private final String TAG = "INPUT";

    private Button buttons[];
    /*
     *   0: left
     *   1: right
     *   2: jump
     *   3: shoot
    */
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
                        shootKeyPressed = true;
                        break;
                }
                buttons[i].pointer = pointer;
                break;
            }
        }

        return true;
    }

//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        Vector2 position = viewport.unproject(new Vector2(screenX, screenY));
//
//        int touchUpButton = -1;
//        int lastTouchedButton = -1;
//
//        for (int i = 0; i < buttons.length; i++) {
//            if (buttons[i].touched(position))
//                touchUpButton = i;
//            if (buttons[i].pointer == pointer)
//                lastTouchedButton = i;
//        }
//
//        int buttonToTurnOff;
//
//        if (touchUpButton >= 0)
//            // when touch up at a button -> stop the function of that button
//            buttonToTurnOff = touchUpButton;
//        else
//            // touch up at nowhere = player drag from a button to outside -> stop that last button
//            buttonToTurnOff = lastTouchedButton;
//
//        // turn off button
//        switch (buttonToTurnOff) {
//            case 0:
//            case 1:
//                rightKeyPressed = false;
//                leftKeyPressed = false;
//                buttons[0].pointer = buttons[1].pointer = 0;
//                break;
//            case 2:
//            case 3:
//                jumpKeyPressed = false;
//                shootKeyPressed = false;
//                buttons[2].pointer = buttons[3].pointer = 0;
//        }
//
//        return true;
//    }

//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        Vector2 position = viewport.unproject(new Vector2(screenX, screenY));
//
//        // handle touch drag from move left button -> move right button
//        if (buttons[1].touched(position) && buttons[0].pointer == pointer) {
//            leftKeyPressed = false;
//            rightKeyPressed = true;
//            buttons[0].pointer = 0;
//            buttons[1].pointer = pointer;
//            return true;
//        }
//
//        // handle touch drag from move right button -> move left button
//        if (buttons[0].touched(position) && buttons[1].pointer == pointer) {
//            rightKeyPressed = false;
//            leftKeyPressed = true;
//            buttons[1].pointer = 0;
//            buttons[0].pointer = pointer;
//            return true;
//        }
//
//        // handle touch drag from jump button -> shoot button
//        if (buttons[3].touched(position) && buttons[2].pointer == pointer) {
//            jumpKeyPressed = false;
//            shootKeyPressed = true;
//            buttons[2].pointer = 0;
//            buttons[3].pointer = pointer;
//            return true;
//        }
//
//        // handle touch drag from shoot button -> jump button
//        if (buttons[2].touched(position) && buttons[1].pointer == pointer) {
//            shootKeyPressed = false;
//            jumpKeyPressed = true;
//            buttons[3].pointer = 0;
//            buttons[2].pointer = pointer;
//            return true;
//        }
//
//        return true;
//    }

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

        Gdx.app.log(TAG, lastTouchedButton + "");
        Gdx.app.log(TAG, currentlyTouchedButton + "");
        Gdx.app.log(TAG, "*");
        Gdx.app.log(TAG, pointer + "");
        Gdx.app.log(TAG, "*");
        for (Button button : buttons)
            Gdx.app.log(TAG, button.pointer + "");
        Gdx.app.log(TAG, "----------------------------");

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
        switch (num) {
            case 0:
                leftKeyPressed = true;
//                rightKeyPressed = false;
//                buttons[0].pointer = pointer;
//                buttons[1].pointer = 0;
                break;
            case 1:
                rightKeyPressed = true;
//                leftKeyPressed = false;
//                buttons[1].pointer = pointer;
//                buttons[0].pointer = 0;
                break;
            case 2:
                jumpKeyPressed = true;
//                shootKeyPressed = false;
//                buttons[2].pointer = pointer;
//                buttons[3].pointer = 0;
                break;
            case 3:
                shootKeyPressed = true;
//                jumpKeyPressed = false;
//                buttons[3].pointer = pointer;
//                buttons[2].pointer = 0;
        }
        buttons[num].pointer = pointer;
    }

    private void deactivateButton(int num) {
        switch (num) {
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
                shootKeyPressed = false;
                break;
        }
        buttons[num].pointer = -1;
    }
}
