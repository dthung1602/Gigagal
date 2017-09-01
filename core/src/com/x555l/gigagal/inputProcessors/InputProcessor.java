package com.x555l.gigagal.inputProcessors;

import com.badlogic.gdx.InputAdapter;


public abstract class InputProcessor extends InputAdapter {
    public boolean leftKeyPressed;
    public boolean rightKeyPressed;

    public boolean downKeyPressed;
    public boolean upKeyPressed;

    public boolean jumpKeyPressed;
    public boolean shootKeyPressed;

    public void reset() {
        leftKeyPressed = false;
        rightKeyPressed = false;

        downKeyPressed = false;
        upKeyPressed = false;

        jumpKeyPressed = false;
        shootKeyPressed = false;
    }
}
