package com.x555l.gigagal.util;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {
    // world
    public static final int WORLD_SIZE = 200;
    public static final Color BACKGROUND_COLOR = Color.SKY;
    static final String GIGAGAL_ATLAS = "images/gigagal.pack.atlas";
    static final String BACKGROUND_ATLAS = "images/background.pack.atlas";
    public static final float DEATH_DEPTH = -150;
    static final float CAMERA_SPEED = 64;

    // gigagal
    static final String STANDING_RIGHT = "standing-right";
    static final String STANDING_LEFT = "standing-left";
    static final String STANDING_UP_RIGHT = "standing-up-right";
    static final String STANDING_UP_LEFT = "standing-up-left";
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

    public static final int MAX_HEALTH = 5;
    public static final int INIT_HEALTH = 3;

    public static final int MAX_LIFE = 5;
    public static final int INIT_LIFE = 3;

    public static final int MAX_BULLET = 500;
    public static final int BONUS_BULLET_AMOUNT = 10;
    public static final int INIT_BULLET = 25;

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
    static final String BULLET_UP = "bullet-up";
    public static final Vector2 BULLET_CENTER = new Vector2(2, 3);
    public static final float BULLET_SPEED = 150;
    public static final float BULLET_COOL_DOWN = 0.4f;

    // explosion
    static final String EXPLOSION_SMALL = "explosion-small";
    static final String EXPLOSION_MEDIUM = "explosion-medium";
    static final String EXPLOSION_LARGE = "explosion-large";
    static final String EXPLOSION_SMALL_X = "explosion-small-x";
    static final String EXPLOSION_MEDIUM_X = "explosion-medium-x";
    static final String EXPLOSION_LARGE_X = "explosion-large-x";
    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);
    static final float EXPLOSION_DURATION = 0.05f;

    // bonus
    static final String BONUS_HEALTH = "powerup";
    // TODO change these
    static final String BONUS_LIFE = "powerup";
    static final String BONUS_BULLET = "powerup";
    public static final Vector2 BONUS_CENTER = new Vector2(7, 5);

    // exit portal
    static final String EXIT_PORTAL_SPRITE_1 = "exit-portal-1";
    static final String EXIT_PORTAL_SPRITE_2 = "exit-portal-2";
    static final String EXIT_PORTAL_SPRITE_3 = "exit-portal-3";
    static final String EXIT_PORTAL_SPRITE_4 = "exit-portal-4";
    static final String EXIT_PORTAL_SPRITE_5 = "exit-portal-5";
    static final String EXIT_PORTAL_SPRITE_6 = "exit-portal-6";

    static final float EXIT_PORTAL_FRAME_DURATION = 0.1f;
    public static final float EXIT_PORTAL_RADIUS = 30;

    // level
    public static final int MAX_LEVEL = 15;

    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level";

    public static final String LEVEL_LAYER_KEY = "layers";
    public static final String LEVEL_OBJECT_KEY = "objects";

    public static final String LEVEL_TILE_HEIGHT_KEY = "tileheight";

    public static final String LEVEL_TYPE_KEY = "type";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";

    public static final String LEVEL_START_TAG = "start";
    public static final String LEVEL_END_TAG = "end";
    public static final String LEVEL_PLATFORM_TAG = "platform";
    public static final String LEVEL_ENEMY_TAG = "platform-enemy";
    public static final String LEVEL_BONUS_HEALTH_TAG = "health";
    public static final String LEVEL_BONUS_LIFE_TAG = "life";
    public static final String LEVEL_BONUS_BULLET_TAG = "bullet";

    // HUD
    public static final float HUD_VIEWPORT_SIZE = 160;
    public static final float HUD_LIFE_ICON_SIZE = 15;
    public static final Vector2 HUD_HEALTH_OFFSET = new Vector2(5, 5);
    public static final Vector2 HUD_BULLET_OFFSET = new Vector2(5, 13);

    // VictoryOverlay/Game Over screens
    public static final float LEVEL_END_DURATION = 5;
    public static final String VICTORY_MESSAGE = "Level completed!";
    public static final String GAME_OVER_MESSAGE = "Game Over, Gal";
    public static final int EXPLOSION_COUNT = 600;
    public static final int ENEMY_COUNT = 300;
    public static final String FONT_FILE = "font/header.fnt";

    // onscreen control asset
    static final String BUTTON_LEFT = "button-move-left";
    static final String BUTTON_RIGHT = "button-move-right";
    static final String BUTTON_UP = "button-up";
    static final String BUTTON_DOWN = "button-down";

    static final String BUTTON_JUMP = "button-jump";
    static final String BUTTON_SHOOT = "button-shoot";

    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 180;
    public static final float BUTTON_RADIUS = 16;

    // main menu
    public static final float MAIN_MENU_WORLD_SIZE = 180;
    public static final float SELECT_LEVEL_WORLD_SIZE = 180;
    public static final float SETTING_WORLD_SIZE = 180;
    static final String BG_MAIN_MENU = "main-menu";
    static final String BG_SELECT_LEVEL = "select-level";
    static final String BG_SETTING = "setting";

    // select level
    public static final int SELECT_LEVEL_NUMBER_OF_COLUMN = 6;
    public static final Vector2 SELECT_LEVEL_SIZE = new Vector2(30, 20);
}