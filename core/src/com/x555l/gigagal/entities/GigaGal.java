package com.x555l.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.bonus.Bonus;
import com.x555l.gigagal.entities.bullets.Bullet;
import com.x555l.gigagal.entities.bullets.gigagalBullets.GigagalBullet;
import com.x555l.gigagal.entities.enemies.Enemy;
import com.x555l.gigagal.inputProcessors.InputProcessor;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;
import com.x555l.gigagal.util.Enum.JumpState;
import com.x555l.gigagal.util.Enum.WalkState;
import com.x555l.gigagal.util.Util;


public class GigaGal {
    private Vector2 spawnPosition;
    public Vector2 position;
    private Vector2 prevPosition;
    private Vector2 velocity;

    private float leftFoot, rightFoot;
    private float prevLeftFoot, prevRightFoot;

    public int health;
    public int life;
    public int bullet;

    private long jumpStartTime;
    private long walkStartTime;
    private long shootLastTime;

    private Facing facing;
    private JumpState jumpState;
    private WalkState walkState;

    private Level level;
    private InputProcessor inputProcessor;

    private Platform currentPlatform; // platform ggg is standing on

    //-------------------------------------
    //            CONSTRUCTOR
    //-------------------------------------

    public GigaGal(Level level, float x, float y) {
        this(level, new Vector2(x, y));
    }

    public GigaGal(Level level, Vector2 spawnPosition) {
        this.level = level;
        this.spawnPosition = spawnPosition.cpy();
        life = Constants.INIT_LIFE;
        init();
    }

    private void init() {
        position = new Vector2(spawnPosition);
        prevPosition = new Vector2(position);
        velocity = new Vector2(0, 0);

        if (inputProcessor != null)
            inputProcessor.reset();

        facing = Facing.RIGHT;
        jumpState = JumpState.FALLING;
        walkState = WalkState.STANDING;

        health = Constants.INIT_HEALTH;
        bullet = Constants.INIT_BULLET;

        currentPlatform = null;

        leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;
        prevLeftFoot = leftFoot;
        prevRightFoot = rightFoot;

        shootLastTime = 0;
    }

    //-------------------------------------
    //          UPDATE & RENDER
    //-------------------------------------

    public void update(float delta) {
        commonUpdate(delta);
        handlePlatforms();
        handleCollisions();
        handleInput();
    }

    private void commonUpdate(float delta) {
        // check if gigagal win
        if (position.dst(level.getExitPortal().position) < Constants.EXIT_PORTAL_RADIUS) {
            level.victory = true;
            return;
        }

        // save current pos to prev. pos
        prevPosition.set(position.x, position.y);

        // gravity
        velocity.y -= delta * Constants.GRAVITY;

        // apply velocity to position
        position.mulAdd(velocity, delta);

        // calculate left, right foot
        leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;
        prevLeftFoot = prevPosition.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        prevRightFoot = prevPosition.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

        // death plane
        if (position.y < Constants.DEATH_DEPTH) {
            die();
            return;
        }

        // handle jumping states
        if (jumpState != JumpState.JUMPING) {
            if (jumpState != JumpState.KNOCK_BACK)
                jumpState = JumpState.FALLING;
        }
    }

    private void handlePlatforms() {
        // handle platform (y axis)
        currentPlatform = null;

        for (Platform platform : level.getPlatforms()) {

            // only check platforms that have relevant heights
            if (!betweenHorizontalBorder(platform))
                continue;

            if (landOnPlatform(platform)) {
                currentPlatform = platform;
                jumpState = JumpState.GROUNDED;
                velocity.set(0, 0);
                position.y = platform.yTop + Constants.GIGAGAL_EYE_POSITION.y;
                break;
            }

            if (blockedByAbovePlatform(platform)) {
                jumpState = JumpState.FALLING;
                velocity.set(0, 0);
                position.y = platform.y;
                break;
            }
        }

        // handle platform (x axis)
        for (Platform platform : level.getPlatforms()) {

            // only check platforms that have relevant width
            if (!betweenVerticalBorder(platform))
                continue;

            if (blockedByLeftPlatform(platform)) {
                if (jumpState != JumpState.GROUNDED)
                    jumpState = JumpState.FALLING;
                velocity.x = 0;
                position.x = platform.xRight + Constants.GIGAGAL_STANCE_WIDTH / 2 + 1;
                break;
            }

            if (blockedByRightPlatform(platform)) {
                if (jumpState != JumpState.GROUNDED)
                    jumpState = JumpState.FALLING;
                velocity.x = 0;
                position.x = platform.x - Constants.GIGAGAL_STANCE_WIDTH / 2 - 1;
                break;
            }
        }
    }

    private void handleCollisions() {
        // a rectangle to detect collision
        Rectangle gigagalBoundary = new Rectangle(
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y,
                Constants.GIGAGAL_STANCE_WIDTH,
                Constants.GIGAGAL_HEIGHT
        );

        // detect collision with enemy
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.boundary.hitByGigagal(gigagalBoundary)) {
                knockBack(enemy);
                break;
            }
        }

        // detect bonus
        for (Bonus bonus : level.getBonuses()) {
            if (gigagalBoundary.contains(bonus.position)) {
                level.getBonuses().removeValue(bonus, true);
                bonus.performAction(this);
                break;
            }
        }

        // detect bullets
        for (Bullet bullet : level.getBullets()) {
            if (gigagalBoundary.contains(bullet.position)) {
                bullet.active = false;
                knockBack(bullet);
            }
        }
    }

    private void handleInput() {
        // handle jumping key
        if (inputProcessor.jumpKeyPressed) {
            handleJumping();
        } else {
            endJumping();
        }

        // handle down key
        if (inputProcessor.downKeyPressed) {
            if (jumpState == JumpState.GROUNDED
                    && currentPlatform != null && currentPlatform.passable) {
                position.y -= 2;
            }
        }

        // handle moving left/right key
        if (jumpState != JumpState.KNOCK_BACK) {
            if (inputProcessor.upKeyPressed) {
                walkState = WalkState.FACE_UP;
            } else {
                walkState = WalkState.WALKING;

                if (inputProcessor.leftKeyPressed)
                    moveLeft();
                else if (inputProcessor.rightKeyPressed)
                    moveRight();
                else
                    endMoving();
            }
        }
        // handle shoot key
        if (inputProcessor.shootKeyPressed) {
            shoot();
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion region;

        float walkingTime = Util.secondsSince(walkStartTime);

        if (facing == Facing.LEFT) {
            if (jumpState == JumpState.GROUNDED) {
                switch (walkState) {
                    case WALKING:
                        region = Assets.instance.gigagal.walkingLeft.getKeyFrame(walkingTime);
                        break;
                    case STANDING:
                        region = Assets.instance.gigagal.standingLeft;
                        break;
                    default:
                        region = Assets.instance.gigagal.standingUpLeft;
                }
            } else {
                region = Assets.instance.gigagal.jumpingLeft;
            }
        } else {
            if (jumpState == JumpState.GROUNDED) {
                switch (walkState) {
                    case WALKING:
                        region = Assets.instance.gigagal.walkingRight.getKeyFrame(walkingTime);
                        break;
                    case STANDING:
                        region = Assets.instance.gigagal.standingRight;
                        break;
                    default:
                        region = Assets.instance.gigagal.standingUpRight;
                }
            } else {
                region = Assets.instance.gigagal.jumpingRight;
            }
        }


        batch.draw(
                region,
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y
        );
    }

    //-------------------------------------
    //             PLATFORMS
    //-------------------------------------

    private boolean betweenHorizontalBorder(Platform platform) {
        return (rightFoot > platform.x          // both feet on the left of platform
                && leftFoot < platform.xRight); // both feet on the right of platform
    }

    private boolean betweenVerticalBorder(Platform platform) {
        return ((!platform.passable)          // platform mustn't be passable
                && position.y >= platform.y     // head must be higher than bottom border of platform
                && position.y - Constants.GIGAGAL_EYE_POSITION.y <= platform.yTop);  // feet must be lower than top border of platform

    }

    private boolean landOnPlatform(Platform platform) {
        // check if gigagal is falling pass top border of platform
        return (prevPosition.y - Constants.GIGAGAL_EYE_POSITION.y >= platform.yTop  // prev.pos must be above
                && position.y - Constants.GIGAGAL_EYE_POSITION.y <= platform.yTop);  // curr.pos must be below
    }

    private boolean blockedByAbovePlatform(Platform platform) {
        // check if gigagal is jumping pass bottom border of platform
        return ((!platform.passable)             // platform mustn't be passable
                && prevPosition.y < platform.y   // prev.pos must be below
                && platform.y < position.y);     // curr.pos must be above
    }

    private boolean blockedByLeftPlatform(Platform platform) {
        // left foot cross right border of platform
        return (leftFoot < platform.xRight && platform.xRight < prevLeftFoot);
    }

    private boolean blockedByRightPlatform(Platform platform) {
        // right foot cross left border of platform
        return (prevRightFoot < platform.x && platform.x < rightFoot);
    }

    //-------------------------------------
    //              JUMPING
    //-------------------------------------

    private void handleJumping() {
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

        float jumpTime = Util.secondsSince(jumpStartTime);

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

    //-------------------------------------
    //               MOVING
    //-------------------------------------

    private void moveLeft() {
        // save time if first time walking
        if (jumpState == JumpState.GROUNDED && walkState == WalkState.STANDING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        facing = Facing.LEFT;

        velocity.x = -Constants.GIGAGAL_SPEED;
    }

    private void moveRight() {
        // save time if first time walking
        if (jumpState == JumpState.GROUNDED && walkState == WalkState.STANDING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        facing = Facing.RIGHT;

        velocity.x = Constants.GIGAGAL_SPEED;
    }

    private void endMoving() {
        velocity.set(0, velocity.y);
        walkState = WalkState.STANDING;
    }

    //-------------------------------------
    //               OTHERS
    //-------------------------------------

    private void shoot() {
        if (Util.secondsSince(shootLastTime) > Constants.GGG_BULLET_COOL_DOWN && bullet > 0) {
            shootLastTime = TimeUtils.nanoTime();
            bullet--;

            float xOffset = Constants.GIGAGAL_GUN_OFFSET.x;
            if (facing == Facing.LEFT)
                xOffset = -xOffset;

            Facing bulletFacing = null;

            if (walkState != WalkState.FACE_UP)
                bulletFacing = facing;

            level.getBullets().add(new GigagalBullet(
                    position.x + xOffset,
                    position.y + Constants.GIGAGAL_GUN_OFFSET.y,
                    bulletFacing,
                    level
            ));
        }

    }

    private void knockBack(Enemy enemy) {
        if (jumpState != JumpState.KNOCK_BACK) {
            if (!loseHealth(Constants.ENEMY_KNOCK_BACK_DAMAGE)) return;
            jumpState = JumpState.KNOCK_BACK;
        }

        // knock to the right
        velocity.set(Constants.KNOCK_BACK_BY_ENEMY_VELOCITY);

        // knock to the left
        if (enemy.position.x > position.x) {
            velocity.x = -velocity.x;
        }
    }

    private void knockBack(Bullet bullet) {
        if (jumpState != JumpState.KNOCK_BACK) {
            if (!loseHealth(bullet.damage)) return;
            jumpState = JumpState.KNOCK_BACK;
        }

        // knock to the right
        velocity.set(Constants.KNOCK_BACK_BY_BULLET_VELOCITY);

        // knock to the left
        if (bullet.position.x > position.x) {
            velocity.x = -velocity.x;
        }
    }

    /**
     * Return true if gigagal still alive, false otherwise
     */
    private boolean loseHealth(int damage) {
        health -= damage;
        if (health == 0) {
            die();
            return false;
        }
        return true;
    }

    /**
     * Return true if gigagal still has extra life, false otherwise
     */
    private boolean die() {
        life--;
        if (life == 0) {
            level.gameover = true;
            return false;
        } else {
            init();
            return true;
        }
    }

    //-------------------------------------
    //         GETTERS & SETTERS
    //-------------------------------------

    public void setInputProcessor(InputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public Platform getCurrentPlatform() {
        return currentPlatform;
    }
}
