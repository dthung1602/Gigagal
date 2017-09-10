package com.x555l.gigagal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


/**
 * Contains all constants for the game
 */

public class Constants {

    /**
     * Constants for the game world
     */
    public static class GameWorld {
        public static final int WORLD_SIZE = 256;
        public static final Color BACKGROUND_COLOR = Color.SKY;
        public static final float DEATH_DEPTH = -150;
        public static final float CAMERA_SPEED = 64;
        public static final float GRAVITY = 1024;
    }

    /**
     * Constants for loading assets
     */
    static class Asset {
        // path to assets
        static final String GIGAGAL_ATLAS = "images/gigagal.pack.atlas";
        static final String BACKGROUND_ATLAS = "images/background.pack.atlas";
        static final String SKIN = "skin/skin.json";

        // gigagal assets
        static final String STANDING_RIGHT = "standing-right";
        static final String STANDING_LEFT = "standing-left";
        static final String STANDING_UP_RIGHT = "standing-up-right";
        static final String STANDING_UP_LEFT = "standing-up-left";
        static final String JUMPING_LEFT = "jumping-left";
        static final String JUMPING_RIGHT = "jumping-right";
        static final String WALK_LEFT = "walk-left-";
        static final String WALK_RIGHT = "walk-right-";
        static final int WALK_FRAME_COUNT = 3;
        static final float WALK_FRAME_DURATION = 0.30f;

        // platform assets
        static final String PLATFORM_PASSABLE = "platform-passable";
        static final String PLATFORM_SOLID = "platform-solid";
        static final int PLATFORM_HORIZONTAL_BORDER = 8;
        static final int PLATFORM_VERTICAL_BORDER = 8;

        // enemies
        static final String ENEMY_BASIC = "enemy-basic";
        static final String ENEMY_STRONG = "enemy-strong";
        static final String ENEMY_FLY_8 = "enemy-fly-8";
        static final String ENEMY_FOLLOW_PATH = "enemy-follow-path";
        static final String ENEMY_PATROL_AIR = "enemy-patrol-air";
        static final String ENEMY_FAST = "enemy-fast";
        static final String ENEMY_SHOOT = "enemy-shoot";

        // gigagal's bullets
        static final String GGG_BULLET_LEFT = "bullet-left";
        static final String GGG_BULLET_RIGHT = "bullet-right";
        static final String GGG_BULLET_UP = "bullet-up";

        // enemies's bullets
        static final String ENEMY_BULLET_PLASMA = "plasma-";
        static final String ENEMY_BULLET_LASER = "laser";
        static final int ENEMY_BULLET_PLASMA_FRAME_COUNT = 2;
        static final float ENEMY_BULLET_PLASMA_FRAME_DURATION = 0.1f;

        // explosions
        static final String EXPLOSION_SMALL = "explosion-small-";
        static final String EXPLOSION_LARGE = "explosion-large-";
        static final int EXPLOSION_FRAME_COUNT = 3;
        static final float EXPLOSION_FRAME_DURATION = 0.05f;

        // bonuses
        static final String BONUS_HEALTH = "health";
        static final String BONUS_LIFE = "life";
        static final String BONUS_BULLET = "powerup";

        // exit portal
        static final String EXIT_PORTAL = "exit-portal-";
        static final int EXIT_PORTAL_FRAME_COUNT = 6;
        static final float EXIT_PORTAL_FRAME_DURATION = 0.1f;

        // on screen controls
        static final String BUTTON_LEFT = "button-move-left";
        static final String BUTTON_RIGHT = "button-move-right";
        static final String BUTTON_UP = "button-up";
        static final String BUTTON_DOWN = "button-down";
        static final String BUTTON_JUMP = "button-jump";
        static final String BUTTON_SHOOT = "button-shoot";

        // backgrounds
        static final String BG_MAIN_MENU = "main-menu";
        static final String BG_SELECT_LEVEL = "select-level";
        static final String BG_SETTING = "setting";
        static final String BG_OVERLAY = "overlay";


    }

    /**
     * Constants for Gigagal
     */
    public static class Gigagal {
        //position
        public static final Vector2 CENTER = new Vector2(10, 24);
        public static final Vector2 GUN_POSITION = new Vector2(12, -13);

        // size
        public static final float STANCE_WIDTH = 15;
        public static final float HEIGHT = 23;

        // movement
        public static final float SPEED = 64;
        public static final float JUMP_SPEED = 256;
        public static final float MAX_JUMP_DURATION = 0.20f;
        public static final Vector2 KNOCK_BACK_BY_ENEMY_VELOCITY = new Vector2(128, 256);
        public static final Vector2 KNOCK_BACK_BY_BULLET_VELOCITY = new Vector2(64, 128);

        // health
        public static final int MAX_HEALTH = 5;
        public static final int INIT_HEALTH = 3;

        // life
        public static final int MAX_LIFE = 5;
        public static final int INIT_LIFE = 3;

        // bullet
        public static final int MAX_BULLET = 500;
        public static final int BONUS_BULLET_AMOUNT = 10;
        public static final int INIT_BULLET = 25;
    }

    /**
     * Constants for enemies
     */
    public static class Enemy {
        // common constants
        public static final float SPEED_BOOST = 3f;
        public static final int KNOCK_BACK_DAMAGE = 1;

        // basic enemy
        public static final Vector2 ENEMY_BASIC_CENTER = new Vector2(14, 22);
        public static final float ENEMY_BASIC_SPEED = 10f;
        public static final float ENEMY_BASIC_FLOAT_AMPLITUDE = 4f;
        public static final float ENEMY_BASIC_FLOAT_PERIOD = 3f;
        public static final int ENEMY_BASIC_HEALTH = 3;

        // strong enemy
        public static final Vector2 ENEMY_STRONG_CENTER = new Vector2(14, 22);
        public static final float ENEMY_STRONG_SPEED = 15f;
        public static final float ENEMY_STRONG_FLOAT_AMPLITUDE = 3f;
        public static final float ENEMY_STRONG_FLOAT_PERIOD = 2f;
        public static final int ENEMY_STRONG_HEALTH = 9;

        // fly 8 enemy
        public static final Vector2 ENEMY_FLY_8_CENTER = new Vector2(14, 22);
        public static final float ENEMY_FLY_8_SHORT_AMPLITUDE = 40f;
        public static final float ENEMY_FLY_8_LONG_AMPLITUDE = 80f;
        public static final float ENEMY_FLY_8_PERIOD = 8;
        public static final int ENEMY_FLY_8_HEALTH = 4;

        // follow path
        public static final Vector2 ENEMY_FOLLOW_PATH_CENTER = new Vector2(14, 22);
        public static final float ENEMY_FOLLOW_PATH_SPEED = 50;
        public static final int ENEMY_FOLLOW_PATH_HEALTH = 6;

        // patrol air
        public static final Vector2 ENEMY_PATROL_AIR_CENTER = new Vector2(14, 22);
        public static final float ENEMY_PATROL_AIR_SPEED = 50;
        public static final int ENEMY_PATROL_AIR_HEALTH = 6;

        // fast enemy
        public static final Vector2 ENEMY_FAST_CENTER = new Vector2(14, 22);
        public static final float ENEMY_FAST_SPEED = 35f;
        public static final int ENEMY_FAST_HEALTH = 4;
        public static final float ENEMY_FAST_ANGLE = 20f;

        // shoot enemy
        public static final Vector2 ENEMY_SHOOT_CENTER = new Vector2(14, 22);
        public static final float ENEMY_SHOOT_SPEED = 25f;
        public static final int ENEMY_SHOOT_HEALTH = 4;
        public static final float ENEMY_SHOOT_COOLDOWN = 0.5f;
    }

    /**
     * Constants for enemies' bullets
     */
    public static class EnemyBullet {
        // laser
        public static final Vector2 ENEMY_BULLET_LASER_CENTER = new Vector2(8, 3);
        public static final int ENEMY_BULLET_LASER_DAMAGE = 1;
        public static final float ENEMY_BULLET_LASER_SPEED = 150;

        // plasma
        public static final Vector2 ENEMY_BULLET_PLASMA_CENTER = new Vector2(10, 7);
        public static final int ENEMY_BULLET_PLASMA_DAMAGE = 2;
        public static final float ENEMY_BULLET_PLASMA_SPEED = 120;
    }

    /**
     * Constants for Gigagal's bullets
     */
    public static class GigagalBullet {
        public static final Vector2 GGG_BULLET_CENTER = new Vector2(2, 3);
        public static final float GGG_BULLET_SPEED = 200;
        public static final float GGG_BULLET_COOL_DOWN = 0.35f;
        public static final int GGG_BULLET_DAMAGE = 1;
    }

    // enemy bullet


    // explosion

    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);


    // bonus

    // exit portal

    public static final float EXIT_PORTAL_RADIUS = 30;

    // level
    public static final int MAX_LEVEL = 2;

    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level";

    public static final String LEVEL_TILESET_FILE = "levels/tileset.json";

    // HUD
    public static final float HUD_VIEWPORT_SIZE = 160;
    public static final float HUD_LIFE_ICON_SIZE = 15;
    public static final Vector2 HUD_HEALTH_OFFSET = new Vector2(5, 5);
    public static final Vector2 HUD_BULLET_OFFSET = new Vector2(5, 13);

    // VictoryOverlay/Game Over screens
    public static final float LEVEL_END_DURATION = 3;
    public static final String VICTORY_MESSAGE = "Level completed!";
    public static final String GAME_OVER_MESSAGE = "Game Over, Gal";
    public static final int EXPLOSION_COUNT = 600;
    public static final int ENEMY_COUNT = 300;
    public static final String FONT_FILE = "font/header.fnt";

    // onscreen control asset
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 180;
    public static final float BUTTON_RADIUS = 16;

    // main menu
    public static final float MAIN_MENU_WORLD_SIZE = 180;
    public static final float SELECT_LEVEL_WORLD_SIZE = 180;
    public static final float SETTING_WORLD_SIZE = 180;


    // select level
    public static final int SELECT_LEVEL_NUMBER_OF_COLUMN = 6;
    public static final Vector2 SELECT_LEVEL_SIZE = new Vector2(30, 20);

    // confirm overlay
    public static final Vector2 CONFIRM_OVERLAY_PADDING = new Vector2(25, 25);

    // Config
    static final String CONFIG_FILE = "config.json";
    static final String CONFIG_FILE_DEFAULT = "config-default.json";
    static final String CONFIG_CURRENT_LEVEL = "level";
    static final String CONFIG_BRIGHTNESS = "brightness";
    static final String CONFIG_SOUND_ENABLE = "sound-enable";
    static final String CONFIG_MUSIC_ENABLE = "music-enable";
    static final String CONFIG_SOUND_VOLUME = "sound-volume";
    static final String CONFIG_MUSIC_VOLUME = "music-volume";
}