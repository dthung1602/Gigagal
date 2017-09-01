package com.x555l.gigagal.inputProcessors;

import com.badlogic.gdx.Input.Keys;


public class KeyPressProcessor extends InputProcessor {

    @Override
    public boolean keyDown(int keycode) {
        setKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        setKey(keycode, false);
        return true;
    }
    
    private void setKey(int keycode, boolean value) {
        switch (keycode) {
            case Keys.LEFT:
                leftKeyPressed = value;
                break;
            case Keys.RIGHT:
                rightKeyPressed = value;
                break;
            case Keys.DOWN:
                downKeyPressed = value;
                break;
            case Keys.UP:
                upKeyPressed = value;
                break;
            case Keys.Z:
                jumpKeyPressed = value;
                break;
            case Keys.X:
                shootKeyPressed = value;
        }
    }
}
