package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.x555l.gigagal.entities.GigaGal;


public class ChaseCamera {
    Camera camera;
    GigaGal gigaGal;

    boolean following;

    public ChaseCamera(Camera camera, GigaGal gigaGal) {
        this.camera = camera;
        this.gigaGal = gigaGal;

        following = true;
    }

    public void update(float delta) {
        // change camera mode if space is pressed
        if (Gdx.input.isKeyPressed(Keys.SPACE))
            following = !following;

        // follow target
        if (following) {
            camera.position.set(gigaGal.position, 0);
            return;
        }

        // debug camera
        if (Gdx.input.isKeyPressed(Keys.A))
            camera.position.x -= delta * Constants.CAMERA_SPEED;
        else if (Gdx.input.isKeyPressed(Keys.D))
            camera.position.x += delta * Constants.CAMERA_SPEED;
        else if (Gdx.input.isKeyPressed(Keys.W))
            camera.position.y += delta * Constants.CAMERA_SPEED;
        else if (Gdx.input.isKeyPressed(Keys.S))
            camera.position.y -= delta * Constants.CAMERA_SPEED;
    }
}
