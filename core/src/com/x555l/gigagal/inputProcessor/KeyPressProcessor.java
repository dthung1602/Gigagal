package com.x555l.gigagal.inputProcessor;

import com.badlogic.gdx.Input.Keys;

public class KeyPressProcessor extends InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                leftKeyPressed = true;
                break;
            case Keys.RIGHT:
                rightKeyPressed = true;
                break;
            case Keys.Z:
                jumpKeyPressed = true;
                break;
            case Keys.X:
                shootKeyPressed = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                leftKeyPressed = false;
                break;
            case Keys.RIGHT:
                rightKeyPressed = false;
                break;
            case Keys.Z:
                jumpKeyPressed = false;
                break;
            case Keys.X:
                shootKeyPressed = false;
        }

        return true;
    }
}
