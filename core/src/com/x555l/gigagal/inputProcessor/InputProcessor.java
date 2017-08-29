package com.x555l.gigagal.inputProcessor;


import com.badlogic.gdx.InputAdapter;

public abstract class InputProcessor extends InputAdapter {
    public boolean leftKeyPressed;
    public boolean rightKeyPressed;
    public boolean jumpKeyPressed;
    public boolean shootKeyPressed;

    public void reset() {
        leftKeyPressed = false;
        rightKeyPressed = false;
        jumpKeyPressed = false;
        shootKeyPressed = false;
    }
}
