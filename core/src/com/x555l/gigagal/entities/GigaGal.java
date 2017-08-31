package com.x555l.gigagal.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.bonus.Bonus;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.inputProcessors.InputProcessor;
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

        shootLastTime = 0;
    }

    public void update(float delta) {
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
//        System.out.println(prevPosition.x);
//        System.out.println(position.x);
//        System.out.println(prevPosition.x == position.x);

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

        // handle platform collision
        currentPlatform = null;

        System.out.println("-----------------------------");
        for (Platform platform : level.getPlatforms()) {
//            System.out.println(platform.x + " " + platform.y);

            if (landOnPlatform(platform)) {
                currentPlatform = platform;
                jumpState = JumpState.GROUNDED;
                velocity.set(0, 0);
                position.y = platform.yTop + Constants.GIGAGAL_EYE_POSITION.y;
//                break;
            }

            if (blockedByAbovePlatform(platform)) {
                jumpState = JumpState.FALLING;
                velocity.set(0, 0);
                position.y = platform.y;
//                break;
            }

            if (blockedByLeftPlatform(platform)) {
                System.exit(0);
                if (jumpState != JumpState.GROUNDED)
                    jumpState = JumpState.FALLING;
                velocity.x = 0;
                position.x = platform.xRight + Constants.GIGAGAL_STANCE_WIDTH/2;
//                break;
            }
        }

        // a rectangle to detect collision
        Rectangle gigagalBoundary = new Rectangle(
                position.x - Constants.GIGAGAL_EYE_POSITION.x,
                position.y - Constants.GIGAGAL_EYE_POSITION.y,
                Constants.GIGAGAL_STANCE_WIDTH,
                Constants.GIGAGAL_HEIGHT
        );

        // detect collision with enemy
        for (com.x555l.gigagal.entities.enemies.Enemy enemy : level.getEnemies()) {
            Rectangle enemyBoundary = new Rectangle(
                    enemy.position.x - Constants.ENEMY_RADIUS,
                    enemy.position.y - Constants.ENEMY_RADIUS,
                    Constants.ENEMY_RADIUS * 2,
                    Constants.ENEMY_RADIUS * 2
            );

            if (gigagalBoundary.overlaps(enemyBoundary)) {
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

        // handle input
        handleInput(delta);
    }

    private void handleInput(float delta) {
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

        // handle moving left/xRight key
        if (jumpState != JumpState.KNOCK_BACK)
            if (inputProcessor.leftKeyPressed)
                moveLeft(delta);
            else if (inputProcessor.rightKeyPressed)
                moveRight(delta);
            else
                endMoving();

        // handle shoot key
        if (inputProcessor.shootKeyPressed) {
            shoot();
        }
    }

    private void shoot() {
        if (Util.seccondsSince(shootLastTime) > Constants.BULLET_COOL_DOWN && bullet > 0) {
            shootLastTime = TimeUtils.nanoTime();
            bullet--;

            float xOffset = Constants.GIGAGAL_GUN_OFFSET.x;
            if (facing == Facing.LEFT)
                xOffset = -xOffset;

            level.addNewBullet(
                    position.x + xOffset,
                    position.y + Constants.GIGAGAL_GUN_OFFSET.y,
                    facing
            );
        }
    }

    private void knockBack(com.x555l.gigagal.entities.enemies.Enemy enemy) {
        if (jumpState != JumpState.KNOCK_BACK) {
            if (!loseHealth()) return;
            jumpState = JumpState.KNOCK_BACK;
        }

        // knock to the xRight
        velocity.set(Constants.KNOCK_BACK_VELOCITY);

        // knock to the left
        if (enemy.position.x > position.x) {
            velocity.x = -velocity.x;
        }
    }

    private boolean landOnPlatform(Platform platform) {
        // check if gigagal is falling pass top border of platform
        if (prevPosition.y - Constants.GIGAGAL_EYE_POSITION.y < platform.yTop      // prev.pos must be above
                || position.y - Constants.GIGAGAL_EYE_POSITION.y > platform.yTop)  // curr.pos must be below
            return false;

        float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

        // both feet on the left of platform
        if (rightFoot < platform.x)
            return false;

        // both feet on the right of platform
        if (leftFoot > platform.xRight)
            return false;

        return true;
    }

    private boolean blockedByAbovePlatform(Platform platform) {
        // check if gigagal is jumping pass bottom border of platform
        if (prevPosition.y > platform.y      // prev.pos must be below
                || position.y < platform.y)  // curr.pos must be above
            return false;

        float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        float rightFoot = position.x + Constants.GIGAGAL_STANCE_WIDTH / 2;

        // both feet on the left of platform
        if (rightFoot < platform.x)
            return false;

        // both feet on the right of platform
        if (leftFoot > platform.xRight)
            return false;

        return true;
    }

    private boolean blockedByLeftPlatform(Platform platform) {
        // check platforms with relevant heights only
        if (position.y < platform.y
                || position.y - Constants.GIGAGAL_EYE_POSITION.y > platform.yTop)
            return false;

        float leftFoot = position.x - Constants.GIGAGAL_STANCE_WIDTH / 2;
        float prevLeftFoot = prevPosition.x - Constants.GIGAGAL_STANCE_WIDTH / 2;

        // left foot cross right border of platform
        return (leftFoot < platform.xRight && platform.xRight < prevLeftFoot);
    }

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
        facing = Facing.LEFT;

        System.out.println(prevPosition.x);
        System.out.println("  " + position.x);
        position.x -= delta * Constants.GIGAGAL_SPEED;
        System.out.println("  " + position.x);
        System.out.println(position.x == prevPosition.x);
    }

    private void moveRight(float delta) {
        // save time if first time walking
        if (jumpState == JumpState.GROUNDED && walkState == WalkState.STANDING)
            walkStartTime = TimeUtils.nanoTime();

        walkState = WalkState.WALKING;
        facing = Facing.RIGHT;

        position.x += delta * Constants.GIGAGAL_SPEED;
    }

    private void endMoving() {
        velocity.set(0, velocity.y);
        walkState = WalkState.STANDING;
    }

    /**Return true if gigagal still alive, false otherwise*/
    private boolean loseHealth() {
        health--;
        if (health == 0) {
            die();
            return false;
        }
        return true;
    }

    /**Return true if gigagal still has extra life, false otherwise*/
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

    public void render(SpriteBatch batch) {
        TextureRegion region;

        float walkingTime = Util.seccondsSince(walkStartTime);

        if (facing == Facing.LEFT) {
            if (jumpState == JumpState.GROUNDED) {
                if (walkState == WalkState.WALKING) {
                    region = Assets.instance.gigaGalAssets.walkingLeft.getKeyFrame(walkingTime);
                } else {
                    region = Assets.instance.gigaGalAssets.standingLeft;
                }
            } else {
                region = Assets.instance.gigaGalAssets.jumpingLeft;
            }
        } else {
            if (jumpState == JumpState.GROUNDED) {
                if (walkState == WalkState.WALKING) {
                    region = Assets.instance.gigaGalAssets.walkingRight.getKeyFrame(walkingTime);
                } else {
                    region = Assets.instance.gigaGalAssets.standingRight;
                }
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

    public void setInputProcessor(InputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }
}
