package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.GigaGal;


public class ChaseCamera {
    private Camera camera;
    private GigaGal gigagal;

    private boolean following;

    private long lastTimeSpacePressed;

    public ChaseCamera(Camera camera, GigaGal gigagal) {
        this.camera = camera;
        this.gigagal = gigagal;
        following = true;
        lastTimeSpacePressed = TimeUtils.nanoTime();
    }

    public void update(float delta) {
        // change camera mode if space is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE)
                && Util.secondsSince(lastTimeSpacePressed) > Constants.GameWorld.SPACE_PRESSED_DELAY) {
            lastTimeSpacePressed = TimeUtils.nanoTime();
            following = !following;
        }

        // follow target
        if (following) {
            camera.position.set(gigagal.position, 0);
            return;
        }

        // debugging camera
        float speed = delta * Constants.GameWorld.CAMERA_SPEED;
        if (Gdx.input.isKeyPressed(Keys.A))
            camera.position.x -= speed;
        if (Gdx.input.isKeyPressed(Keys.D))
            camera.position.x += speed;
        if (Gdx.input.isKeyPressed(Keys.W))
            camera.position.y += speed;
        if (Gdx.input.isKeyPressed(Keys.S))
            camera.position.y -= speed;
    }
}
