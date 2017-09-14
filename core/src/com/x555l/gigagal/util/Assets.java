package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable, AssetErrorListener {
    // for debugging
    private static final String TAG = Assets.class.getName();

    // singleton
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    // hold assets of different objects
    public GigaGalAssets gigagal;
    public PlatformAssets platform;
    public EnemyAssets enemy;
    public BulletAssets bullet;
    public ExplosionAssets explosion;
    public BonusAssets bonus;
    public ExitPortalAssets exitPortal;
    public OnscreenControlAssets onscreenControl;

    public BackgroundAssets background;
    public Skin skin;
    public AudioAssets audio;

    // ensure singleton
    private Assets() {
    }

    public void load() {

        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.Asset.GIGAGAL_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.Asset.BACKGROUND_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.Asset.SKIN, Skin.class);
        loadAudio(assetManager);
        assetManager.finishLoading();

        TextureAtlas gigagalAtlas = assetManager.get(Constants.Asset.GIGAGAL_ATLAS);
        TextureAtlas backgroundAtlas = assetManager.get(Constants.Asset.BACKGROUND_ATLAS);

        skin = assetManager.get(Constants.Asset.SKIN);
        audio = new AudioAssets(assetManager);

        try {
            gigagal = new GigaGalAssets(gigagalAtlas);
            platform = new PlatformAssets(gigagalAtlas);
            enemy = new EnemyAssets(gigagalAtlas);
            bullet = new BulletAssets(gigagalAtlas);
            explosion = new ExplosionAssets(gigagalAtlas);
            bonus = new BonusAssets(gigagalAtlas);
            exitPortal = new ExitPortalAssets(gigagalAtlas);
            onscreenControl = new OnscreenControlAssets(gigagalAtlas);
            background = new BackgroundAssets(backgroundAtlas);
        } catch (AssetsContainer.AtlasRegionNotFound ex) {
            Util.exitWithError(TAG, ex);
        }
    }

    /**
     * Add sound and music files for asset manager to load
     */
    private void loadAudio(AssetManager assetManager) {
        // load sounds
        for (String file : Constants.Asset.SOUND_FILES) {
            assetManager.load(file, Sound.class);
        }
        // load music
        assetManager.load(Constants.Asset.AUDIO_BACKGROUND, Music.class);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Can not load assets: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    //------------------------------------------------------------//
    //                       INNER CLASSES                        //
    //------------------------------------------------------------//

    /**
     * Abstract base class for all other inner classes
     * Ensure all instance variables are not null
     */
    private abstract class AssetsContainer {
        /**
         * Find a region in an atlas region
         * Ensure that region is found
         */
        AtlasRegion findRegion(TextureAtlas atlas, String regionName) throws AtlasRegionNotFound {
            AtlasRegion region = atlas.findRegion(regionName);
            if (region != null)
                return region;
            else
                throw new AtlasRegionNotFound(regionName);
        }

        /**
         * Create and return animation base on given parameters
         */
        Animation<TextureRegion> createAnimation(TextureAtlas atlas, String name, int frameCount, float frameDuration, PlayMode playMode) throws AtlasRegionNotFound {
            Array<TextureRegion> regions = new Array<TextureRegion>();

            for (int i = 1; i <= frameCount; i++)
                regions.add(findRegion(atlas, name + i));

            return new Animation<TextureRegion>(frameDuration, regions, playMode);
        }

        /**
         * Custom exception
         */
        class AtlasRegionNotFound extends Exception {
            AtlasRegionNotFound(String regionName) {
                super("ERROR: Atlas region '" + regionName + "' not found");
            }
        }
    }

    public class GigaGalAssets extends AssetsContainer {
        public AtlasRegion standingRight;
        public AtlasRegion standingLeft;
        public AtlasRegion standingUpLeft;
        public AtlasRegion standingUpRight;

        public AtlasRegion jumpingRight;
        public AtlasRegion jumpingLeft;

        public Animation<TextureRegion> walkingLeft;
        public Animation<TextureRegion> walkingRight;

        GigaGalAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            standingRight = findRegion(atlas, Constants.Asset.GGG_STANDING_RIGHT);
            standingLeft = findRegion(atlas, Constants.Asset.GGG_STANDING_LEFT);

            standingUpRight = findRegion(atlas, Constants.Asset.GGG_STANDING_UP_RIGHT);
            standingUpLeft = findRegion(atlas, Constants.Asset.GGG_STANDING_UP_LEFT);

            jumpingLeft = findRegion(atlas, Constants.Asset.GGG_JUMPING_LEFT);
            jumpingRight = findRegion(atlas, Constants.Asset.GGG_JUMPING_RIGHT);

            walkingLeft = createAnimation(
                    atlas,
                    Constants.Asset.GGG_WALK_LEFT,
                    Constants.Asset.GGG_WALK_FRAME_COUNT,
                    Constants.Asset.GGG_WALK_FRAME_DURATION,
                    PlayMode.LOOP_PINGPONG
            );

            walkingRight = createAnimation(
                    atlas,
                    Constants.Asset.GGG_WALK_RIGHT,
                    Constants.Asset.GGG_WALK_FRAME_COUNT,
                    Constants.Asset.GGG_WALK_FRAME_DURATION,
                    PlayMode.LOOP_PINGPONG
            );
        }
    }

    public class PlatformAssets extends AssetsContainer {
        public NinePatch passablePlatform;
        public NinePatch solidPlatform;

        PlatformAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            TextureRegion region = findRegion(atlas, Constants.Asset.PLATFORM_PASSABLE);
            passablePlatform = new NinePatch(
                    region,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER
            );

            region = findRegion(atlas, Constants.Asset.PLATFORM_SOLID);
            solidPlatform = new NinePatch(
                    region,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER
            );
        }
    }

    public class EnemyAssets extends AssetsContainer {
        public AtlasRegion basicEnemy;
        public AtlasRegion strongEnemy;
        public AtlasRegion fly8Enemy;
        public AtlasRegion followPathEnemy;
        public AtlasRegion patrolAirEnemy;
        public AtlasRegion fastEnemy;
        public AtlasRegion shootEnemy;
        public AtlasRegion guardEnemy;

        EnemyAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            basicEnemy = findRegion(atlas, Constants.Asset.ENEMY_BASIC);
            strongEnemy = findRegion(atlas, Constants.Asset.ENEMY_STRONG);
            fly8Enemy = findRegion(atlas, Constants.Asset.ENEMY_FLY_8);
            followPathEnemy = findRegion(atlas, Constants.Asset.ENEMY_FOLLOW_PATH);
            patrolAirEnemy = findRegion(atlas, Constants.Asset.ENEMY_PATROL_AIR);
            fastEnemy = findRegion(atlas, Constants.Asset.ENEMY_FAST);
            shootEnemy = findRegion(atlas, Constants.Asset.ENEMY_SHOOT);
            guardEnemy = findRegion(atlas, Constants.Asset.ENEMY_GUARD);
        }
    }

    public class BulletAssets extends AssetsContainer {
        public AtlasRegion leftBullet;
        public AtlasRegion rightBullet;
        public AtlasRegion upBullet;

        public AtlasRegion enemyLaser;
        public Animation<TextureRegion> enemyPlasma;

        BulletAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            leftBullet = findRegion(atlas, Constants.Asset.GGG_BULLET_LEFT);
            rightBullet = findRegion(atlas, Constants.Asset.GGG_BULLET_RIGHT);
            upBullet = findRegion(atlas, Constants.Asset.GGG_BULLET_UP);

            enemyLaser = findRegion(atlas, Constants.Asset.ENEMY_BULLET_LASER);
            enemyPlasma = createAnimation(
                    atlas,
                    Constants.Asset.ENEMY_BULLET_PLASMA,
                    Constants.Asset.ENEMY_BULLET_PLASMA_FRAME_COUNT,
                    Constants.Asset.ENEMY_BULLET_PLASMA_FRAME_DURATION,
                    PlayMode.LOOP
            );
        }
    }

    public class ExplosionAssets extends AssetsContainer {
        public Animation<TextureRegion> smallExplosion;
        public Animation<TextureRegion> largeExplosion;

        ExplosionAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            smallExplosion = createAnimation(
                    atlas,
                    Constants.Asset.EXPLOSION_SMALL,
                    Constants.Asset.EXPLOSION_FRAME_COUNT,
                    Constants.Asset.EXPLOSION_FRAME_DURATION,
                    PlayMode.NORMAL
            );
            largeExplosion = createAnimation(
                    atlas,
                    Constants.Asset.EXPLOSION_LARGE,
                    Constants.Asset.EXPLOSION_FRAME_COUNT,
                    Constants.Asset.EXPLOSION_FRAME_DURATION,
                    PlayMode.NORMAL
            );
        }
    }

    public class BonusAssets extends AssetsContainer {
        public AtlasRegion health;
        public AtlasRegion life;
        public AtlasRegion bullet;

        BonusAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            health = findRegion(atlas, Constants.Asset.BONUS_HEALTH);
            life = findRegion(atlas, Constants.Asset.BONUS_LIFE);
            bullet = findRegion(atlas, Constants.Asset.BONUS_BULLET);
        }
    }

    public class ExitPortalAssets extends AssetsContainer {
        public Animation<TextureRegion> animation;

        ExitPortalAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            animation = createAnimation(
                    atlas,
                    Constants.Asset.EXIT_PORTAL,
                    Constants.Asset.EXIT_PORTAL_FRAME_COUNT,
                    Constants.Asset.EXIT_PORTAL_FRAME_DURATION,
                    PlayMode.LOOP
            );
        }
    }

    public class OnscreenControlAssets extends AssetsContainer {
        public AtlasRegion leftButton;
        public AtlasRegion rightButton;
        public AtlasRegion upButton;
        public AtlasRegion downButton;

        public AtlasRegion jumpButton;
        public AtlasRegion shootButton;

        public AtlasRegion pauseButton;

        OnscreenControlAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            leftButton = findRegion(atlas, Constants.Asset.BUTTON_LEFT);
            rightButton = findRegion(atlas, Constants.Asset.BUTTON_RIGHT);
            upButton = findRegion(atlas, Constants.Asset.BUTTON_UP);
            downButton = findRegion(atlas, Constants.Asset.BUTTON_DOWN);

            jumpButton = findRegion(atlas, Constants.Asset.BUTTON_JUMP);
            shootButton = findRegion(atlas, Constants.Asset.BUTTON_SHOOT);

            pauseButton = findRegion(atlas, Constants.Asset.BUTTON_PAUSE);
        }
    }

    public class BackgroundAssets extends AssetsContainer {
        public TextureRegion mainMenu;
        public TextureRegion selectLevel;
        public TextureRegion setting;
        public TextureRegion overlay;

        BackgroundAssets(TextureAtlas atlas) throws AtlasRegionNotFound {
            mainMenu = findRegion(atlas, Constants.Asset.BG_MAIN_MENU);
            selectLevel = findRegion(atlas, Constants.Asset.BG_SELECT_LEVEL);
            setting = findRegion(atlas, Constants.Asset.BG_SETTING);
            overlay = findRegion(atlas, Constants.Asset.BG_OVERLAY);
        }
    }

    public class AudioAssets {

        public Music background;

        public Sound bonusBullet;
        public Sound bonusHealth;
        public Sound bonusLife;

        public Sound bulletShoot;
        public Sound bulletHit;
        public Sound explosion;

        public Sound jump;
        public Sound land;

        public Sound enemyLaser;
        public Sound enemyPlasma;

        AudioAssets(AssetManager assetManager) {
            try {
                background = assetManager.get(Constants.Asset.AUDIO_BACKGROUND, Music.class);

                bonusBullet = assetManager.get(Constants.Asset.AUDIO_BONUS_BULLET, Sound.class);
                bonusHealth = assetManager.get(Constants.Asset.AUDIO_BONUS_HEALTH, Sound.class);
                bonusLife = assetManager.get(Constants.Asset.AUDIO_BONUS_LIFE, Sound.class);

                bulletShoot = assetManager.get(Constants.Asset.AUDIO_BULLET_SHOOT, Sound.class);
                bulletHit = assetManager.get(Constants.Asset.AUDIO_BULLET_HIT, Sound.class);
                explosion = assetManager.get(Constants.Asset.AUDIO_EXPLOSION, Sound.class);

                jump = assetManager.get(Constants.Asset.AUDIO_JUMP, Sound.class);
                land = assetManager.get(Constants.Asset.AUDIO_LAND, Sound.class);

                enemyLaser = assetManager.get(Constants.Asset.AUDIO_ENEMY_LASER, Sound.class);
                enemyPlasma = assetManager.get(Constants.Asset.AUDIO_ENEMY_PLASMA, Sound.class);

            } catch (Exception ex) {
                Util.exitWithError(TAG, ex);
            }
        }
    }
}