package com.x555l.gigagal.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


/**
 * Contains all constants for the game
 */

public class Constants {

    /**
     * Constants for desktop platform
     */
    public static class DeskTop {
        public static final float SCREEN_RATIO = 16f / 9f;
        public static final int SCREEN_WIDTH = 1000;
        public static final int SCREEN_HEIGHT = (int) (SCREEN_WIDTH / SCREEN_RATIO);
    }


    /**
     * Constants for the game world
     */
    public static class GameWorld {
        public static final int GAME_WORLD_SIZE = 256;
        public static final int STAGE_WORLD_SIZE = 256;
        public static final float DEATH_DEPTH = -150;
        public static final float GRAVITY = 1024;
        public static final Color BACKGROUND_COLOR = Color.SKY;
        public static final Vector2 MENU_OVERLAY_PADDING = new Vector2(25, 25);

        // camera
        static final float CAMERA_SPEED = 64;
        static final float SPACE_PRESSED_DELAY = 0.3f;
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
        static final String GGG_STANDING_RIGHT = "standing-right";
        static final String GGG_STANDING_LEFT = "standing-left";
        static final String GGG_STANDING_UP_RIGHT = "standing-up-right";
        static final String GGG_STANDING_UP_LEFT = "standing-up-left";
        static final String GGG_JUMPING_LEFT = "jumping-left";
        static final String GGG_JUMPING_RIGHT = "jumping-right";
        static final String GGG_WALK_LEFT = "walk-left-";
        static final String GGG_WALK_RIGHT = "walk-right-";
        static final int GGG_WALK_FRAME_COUNT = 3;
        static final float GGG_WALK_FRAME_DURATION = 0.30f;

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
        static final String ENEMY_GUARD = "enemy-guard";

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
        static final String BUTTON_PAUSE = "button-pause";

        // backgrounds
        static final String BG_MAIN_MENU = "main-menu";
        static final String BG_SELECT_LEVEL = "select-level";
        static final String BG_SETTING = "setting";
        static final String BG_OVERLAY = "overlay";

        // audio
        static final String AUDIO_BACKGROUND = "audio/background.mp3";
        static final String AUDIO_BONUS_BULLET = "audio/bonus-bullet.mp3";
        static final String AUDIO_BONUS_HEALTH = "audio/bonus-health.wav";
        static final String AUDIO_BONUS_LIFE = "audio/bonus-life.wav";
        static final String AUDIO_BULLET_SHOOT = "audio/bullet-shoot.mp3";
        static final String AUDIO_BULLET_HIT = "audio/bullet-hit.wav";
        static final String AUDIO_EXPLOSION = "audio/explosion.mp3";
        static final String AUDIO_JUMP = "audio/jump.wav";
        static final String AUDIO_LAND = "audio/land.mp3";
        static final String AUDIO_ENEMY_LASER = "audio/laser.mp3";
        static final String AUDIO_ENEMY_PLASMA = "audio/plasma.mp3";
        static final String[] SOUND_FILES = new String[]{
                AUDIO_BONUS_BULLET,
                AUDIO_BONUS_HEALTH,
                AUDIO_BONUS_LIFE,
                AUDIO_BULLET_SHOOT,
                AUDIO_BULLET_HIT,
                AUDIO_EXPLOSION,
                AUDIO_JUMP,
                AUDIO_LAND,
                AUDIO_ENEMY_LASER,
                AUDIO_ENEMY_PLASMA
        };
    }

    /**
     * Constants for Gigagal
     */
    public static class Gigagal {
        //position
        public static final Vector2 CENTER = new Vector2(8, 15);
        public static final Vector2 GUN_POSITION = new Vector2(12, -7);

        // size
        public static final float WIDTH = 15;
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
        public static final int INIT_BULLET = 25;
        public static final int BONUS_BULLET_AMOUNT = 15;
    }

    /**
     * Constants for enemies
     */
    public static class Enemy {
        // common constants
        public static final int KNOCK_BACK_DAMAGE = 1;

        // basic enemy
        public static final Vector2 BASIC_ENEMY_CENTER = new Vector2(14, 22);
        public static final float BASIC_ENEMY_SPEED = 10f;
        public static final float BASIC_ENEMY_FLOAT_AMPLITUDE = 4f;
        public static final float BASIC_ENEMY_FLOAT_PERIOD = 3f;
        public static final int BASIC_ENEMY_HEALTH = 3;

        // strong enemy
        public static final Vector2 STRONG_ENEMY_CENTER = new Vector2(14, 22);
        public static final float STRONG_ENEMY_SPEED = 15f;
        public static final float STRONG_ENEMY_FLOAT_AMPLITUDE = 3f;
        public static final float STRONG_ENEMY_FLOAT_PERIOD = 2f;
        public static final int STRONG_ENEMY_HEALTH = 9;

        // fly 8 enemy
        public static final Vector2 FLY_8_ENEMY_CENTER = new Vector2(14, 22);
        public static final float FLY_8_ENEMY_SHORT_AMPLITUDE = 40f;
        public static final float FLY_8_ENEMY_LONG_AMPLITUDE = 80f;
        public static final float FLY_8_ENEMY_PERIOD = 8;
        public static final int FLY_8_ENEMY_HEALTH = 4;

        // follow path
        public static final Vector2 FOLLOW_PATH_ENEMY_CENTER = new Vector2(14, 22);
        public static final float FOLLOW_PATH_ENEMY_SPEED = 50;
        public static final int FOLLOW_PATH_ENEMY_HEALTH = 5;

        // patrol air
        public static final Vector2 PATROL_AIR_ENEMY_CENTER = new Vector2(14, 22);
        public static final float PATROL_AIR_ENEMY_SPEED = 50;
        public static final int PATROL_AIR_ENEMY_HEALTH = 6;

        // fast enemy
        public static final Vector2 FAST_ENEMY_CENTER = new Vector2(14, 22);
        public static final float FAST_ENEMY_SPEED = 35f;
        public static final float FAST_ENEMY_SPEED_BOOST = 2f;
        public static final int FAST_ENEMY_HEALTH = 4;
        public static final float FAST_ENEMY_ANGLE = 20f;

        // shoot enemy
        public static final Vector2 SHOOT_ENEMY_CENTER = new Vector2(14, 22);
        public static final float SHOOT_ENEMY_SPEED = 25f;
        public static final int SHOOT_ENEMY_HEALTH = 4;
        public static final float SHOOT_ENEMY_COOLDOWN = 0.5f;

        // guard enemy
        public static final Vector2 GUARD_ENEMY_CENTER = new Vector2(14, 22);
        public static final float GUARD_ENEMY_SPEED = 20f;
        public static final int GUARD_ENEMY_HEALTH = 3;
        public static final float GUARD_ENEMY_SPEED_BOOST = 1.5f;
        public static final float GUARD_ENEMY_COOLDOWN = 0.75f;
        public static final float GUARD_ENEMY_STOP_TIME_MIN = 1;
        public static final float GUARD_ENEMY_STOP_TIME_MAX = 3;
        public static final float GUARD_ENEMY_NEXT_STOP_MIN = 2;
        public static final float GUARD_ENEMY_NEXT_STOP_MAX = 7;
        public static final float GUARD_ENEMY_FLOAT_AMPLITUDE = 6f;
        public static final float GUARD_ENEMY_FLOAT_PERIOD = 2f;
    }

    /**
     * Constants for enemies' bullets
     */
    public static class EnemyBullet {
        // laser
        public static final Vector2 LASER_CENTER = new Vector2(8, 3);
        public static final int LASER_DAMAGE = 1;
        public static final float LASER_SPEED = 150;

        // plasma
        public static final Vector2 PLASMA_CENTER = new Vector2(10, 7);
        public static final int PLASMA_DAMAGE = 2;
        public static final float PLASMA_SPEED = 120;
    }

    /**
     * Constants for Gigagal's bullets
     */
    public static class GigagalBullet {
        public static final Vector2 BULLET_CENTER = new Vector2(2, 3);
        public static final float BULLET_SPEED = 200;
        public static final float BULLET_COOL_DOWN = 0.35f;
        public static final int BULLET_DAMAGE = 1;
    }

    /**
     * Constants for HUD
     */
    public static class HUD {
        public static final float VIEWPORT_SIZE = 160;
        public static final Color INFO_COLOR = Color.BLUE;
        public static final float Y_OFFSET = 6;
        public static final Vector2 ICON_OFFSET = new Vector2(5, 10);
        public static final Vector2 TEXT_OFFSET = new Vector2(16, 10);
        public static final Vector2 FPS_OFFSET = new Vector2(30, 13);
    }

    /**
     * Constants for level loading & level choosing
     */
    public static class Level {
        // level loading
        public static final int MAX_LEVEL = 2;
        public static final String TILESET_FILE = "levels/tileset.json";

        // level selecting
        public static final int SELECT_LEVEL_NUMBER_OF_COLUMN = 6;
        public static final Vector2 SELECT_LEVEL_BUTTON_SIZE = new Vector2(30, 20);
    }


    /**
     * Constants for EndLevel overlays: Victory and Gameover overlay
     */
    public static class EndLevelOverlay {
        // common
        public static final String FONT_FILE = "font/header.fnt";
        public static final float DURATION = 3;

        // victory
        public static final String VICTORY_MESSAGE = "Level completed!";
        public static final int EXPLOSION_COUNT = 600;

        // gameover
        public static final String GAME_OVER_MESSAGE = "Game Over, Gal";
        public static final int ENEMY_COUNT = 300;
    }

    /**
     * Constants for onscreen control for mobile
     */
    public static class OnscreenControl {
        public static final float VIEWPORT_SIZE = 180;
        public static final float BUTTON_RADIUS = 16;
    }

    /**
     * Constants for main menu
     */
    public static class MainMenu {
        public static final float MAIN_MENU_WORLD_SIZE = 256;
        public static final float SELECT_LEVEL_WORLD_SIZE = 256;
        public static final float SETTING_WORLD_SIZE = 256;
    }

    /**
     * Constants for loading & saving config file
     */
    static class Config {
        static final String CONFIG_FILE = "config.json";
        static final String CONFIG_FILE_DEFAULT = "config-default.json";
        static final String CURRENT_LEVEL = "level";
        static final String BRIGHTNESS = "brightness";
        static final String SOUND_ENABLE = "sound-enable";
        static final String MUSIC_ENABLE = "music-enable";
        static final String SOUND_VOLUME = "sound-volume";
        static final String MUSIC_VOLUME = "music-volume";

        static final String DEBUG_SCREEN_LAYOUT = "debug-screen-layout";
        static final String DEBUG_FPS = "debug-fps";
        static final String DEBUG_ONSCREEN_CONTROL = "debug-onscreen-control";
    }

    public static final Vector2 EXPLOSION_CENTER = new Vector2(8, 8);
    public static final float EXIT_PORTAL_RADIUS = 30;
}