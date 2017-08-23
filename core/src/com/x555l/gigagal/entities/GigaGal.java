package com.x555l.gigagal.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.*;
import com.x555l.gigagal.util.Util;

public class GigaGal {
    private Vector2 spawnPosition;
    public Vector2 position;
    private Vector2 prevPosition;
    private Vector2 velocity;

    private long jumpStartTime;
    private long walkStartTime;

    private Facing facing;
    private JumpState jumpState;
    private WalkState walkState;

    public GigaGal(Vector2 spawnPosition) {
        this.spawnPosition = spawnPosition;
        init();
    }

    public void init() {
        position = new Vector2(spawnPosition);
        prevPosition = new Vector2(position);
        velocity = new Vector2(0, 0);

        facing = Facing.RIGHT;
        jumpState = JumpState.FALLING;
        walkState = WalkState.STANDING;
    }

    public void update(float delta, Array<Platform> platforms) {
        // save current pos to prev. pos
        prevPosition.set(position);

        // gravity
        velocity.y -= delta * Constants.GRAVITY;

        // apply velocity to position
        position.mulAdd(velocity, delta);

        // death plane
        if (position.y < Constants.DEATH_DEPTH) {
            init();
            return;
        }

        // handle jumping states and landing on platforms
        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            for (Platform platform : platforms) {
                if (landOnPlatform(platform)) {
                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    position.y = platform.top + Constants.GIGAGAL_EYE_POSITION.y;
                    break;
                }
            }
        }


        // handle jumping key
        if (Gdx.input.isKeyPressed(Keys.Z)) {
            switch (jumpState) {
                case GROUNDED:
                    startJumping();
                    break;
                case JUMPING:
                    continueJumping();
                    break;
                case FALLING:
                    break;
            }
        } else {
            endJumping();
        }

        // handle moving left/right key
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            moveLeft(delta);
        else if (Gdx.input.isKeyPressed(Keys.RIGHT))
            moveRight(delta);
        else
            walkState = WalkState.STANDING;

    }

    private boolean landOnPlatform(Platform platform) {
        // check if gigagal is falling pass top border of platform
        if (prevPosition.y - Constants.GIGAGAL_EYE_POSITION.y < platform.top      // prev.pos must be above
                || position.y - Constants.GIGAGAL_EYE_POSITION.y > platform.top)  // curr.pos must be below
            return false;

        float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

        // both feet on the left of platform
        if (rightFoot < platform.left)
            return false;

        // both feet on the right of platform
        if (leftFoot > platform.right)
            return false;

        return true;
    }

    private void startJumping() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();

        continueJumping();
    }

    private void continueJumping() {
        if (jumpState != JumpState.JUMPING) {
            return;
        }

        float jumpTime = Util.seccondsSince(jumpStartTime);

        if (jumpTime < Constants.GIGAGAL_MAX_JUMP_DURATION) {
            velocity.y = Constants.GIGAGAL_JUMP_SPEED;
        } else {
            endJumping();
        }
    }

    private void endJumping() {
        if (jumpState == JumpState.JUMPING)
            jumpState = JumpState.FALLING;
    }

    private void moveLeft(float delta) {
        // save time if first time walking
        if (jumpState == JumpState.GROUNDED && walkState == WalkState.STANDING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        position.x -= delta * Constants.GIGAGAL_SPEED;
        facing = Facing.LEFT;
    }

    private void moveRight(float delta) {
        // save time if first time walking
        if (jumpState == JumpState.GROUNDED && walkState == WalkState.STANDING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        position.x += delta * Constants.GIGAGAL_SPEED;
        facing = Facing.RIGHT;
    }

    public void render(SpriteBatch batch) {
        TextureRegion region;

        float walkingTime = Util.seccondsSince(walkStartTime);

        if (facing == Facing.LEFT) {
            if (jumpState == JumpState.GROUNDED) {
                if (walkState == WalkState.WALKING)
                    region = Assets.instance.gigaGalAssets.walkingLeft.getKeyFrame(walkingTime);
                else
                    region = Assets.instance.gigaGalAssets.standingLeft;
            } else {
                region = Assets.instance.gigaGalAssets.jumpingLeft;
            }
        } else {
            if (jumpState == JumpState.GROUNDED) {
                if (walkState == WalkState.WALKING)
                    region = Assets.instance.gigaGalAssets.walkingRight.getKeyFrame(walkingTime);
                else
                    region = Assets.instance.gigaGalAssets.standingRight;
            } else {
                region = Assets.instance.gigaGalAssets.jumpingRight;
            }
        }

        batch.draw(
                region,
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y
        );
    }
}
