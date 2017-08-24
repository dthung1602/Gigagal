package com.x555l.gigagal.util;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    // world
    public static final int WORLD_SIZE = 256;
    public static final Color BACKGROUND_COLOR = Color.SKY;
    static final String TEXTURE_ATLAS = "images/gigagal.pack.atlas";
    public static final float DEATH_DEPTH = -50;
    static final float CAMERA_SPEED = 64;

    // gigagal
    static final String STANDING_RIGHT = "standing-right";
    static final String STANDING_LEFT = "standing-left";
    static final String JUMPING_LEFT = "jumping-left";
    static final String JUMPING_RIGHT = "jumping-right";
    static final String WALK_1_LEFT = "walk-1-left";
    static final String WALK_1_RIGHT = "walk-1-right";
    static final String WALK_2_LEFT = "walk-2-left";
    static final String WALK_2_RIGHT = "walk-2-right";
    static final String WALK_3_LEFT = "walk-3-left";
    static final String WALK_3_RIGHT = "walk-3-right";

    static final float WALK_LOOP_DURATION = 0.30f;

    public static final Vector2 GIGAGAL_EYE_POSITION = new Vector2(10, 24);
    public static final float GIGAGAL_STANCE_WIDTH = 15;
    public static final float GIGAGAL_HEIGHT = 23;
    public static final Vector2 GIGAGAL_GUN_OFFSET = new Vector2(12, -13);

    public static final float GIGAGAL_SPEED = 64;
    public static final float GIGAGAL_JUMP_SPEED = 256;
    public static final float GIGAGAL_MAX_JUMP_DURATION = 0.20f;
    public static final float GRAVITY = 1024;

    public static final Vector2 KNOCK_BACK_VELOCITY = new Vector2(128, 256);

    // platform
    static final String PLATFORM = "platform";
    static final int HORIZONTAL_BORDER = 8;
    static final int VERTICAL_BORDER = 8;

    // enemy
    static final String ENEMY = "enemy";
    public static final Vector2 ENEMY_CENTER = new Vector2(14, 22);
    public static final float ENEMY_SPEED = 10f;
    public static final float FLOAT_AMPLITUDE = 4f;
    public static final float FLOAT_PERIOD = 3f;
    public static final float ENEMY_RADIUS = 15f;
    public static final float ENEMY_HIT_RADIUS = 17f;
    public static final int ENEMY_HEALTH = 3;

    // bullet
    static final String BULLET_LEFT = "bullet-left";
    static final String BULLET_RIGHT = "bullet-right";
    public static final Vector2 BULLET_CENTER = new Vector2(2, 3);
    public static final float BULLET_SPEED = 150;
    public static final float BULLET_COOL_DOWN = 0.75f;

    // explosion
    static final String EXPLOSION_SMALL = "explosion-small";
    static final String EXPLOSION_MEDIUM = "explosion-medium";
    static final String EXPLOSION_LARGE = "explosion-large";
    static final String EXPLOSION_SMALL_X = "explosion-small-x";
    static final String EXPLOSION_MEDIUM_X = "explosion-medium-x";
    static final String EXPLOSION_LARGE_X = "explosion-large-x";
    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);
    static final float EXPLOSION_DURATION = 0.05f;

    // powerup
    static final String POWERUP = "powerup";
    public static final Vector2 POWERUP_CENTER = new Vector2(7, 5);
}
