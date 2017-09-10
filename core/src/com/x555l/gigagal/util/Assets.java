package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
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


    public backgroundAssets background;

    public Skin skin;

    // ensure singleton
    private Assets() {
    }

    public void load() {
        skin = new Skin(Gdx.files.internal(Constants.Asset.SKIN));

        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.Asset.GIGAGAL_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.Asset.BACKGROUND_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas gigagalAtlas = assetManager.get(Constants.Asset.GIGAGAL_ATLAS);
        TextureAtlas backgroundAtlas = assetManager.get(Constants.Asset.BACKGROUND_ATLAS);

        gigagal = new GigaGalAssets(gigagalAtlas);
        platform = new PlatformAssets(gigagalAtlas);
        enemy = new EnemyAssets(gigagalAtlas);
        bullet = new BulletAssets(gigagalAtlas);
        explosion = new ExplosionAssets(gigagalAtlas);
        bonus = new BonusAssets(gigagalAtlas);
        exitPortal = new ExitPortalAssets(gigagalAtlas);
        onscreenControl = new OnscreenControlAssets(gigagalAtlas);

        background = new backgroundAssets(backgroundAtlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Can not load assets: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    /**
     * Create and return animation base on given parameters
     */
    private Animation<TextureRegion> createAnimation(TextureAtlas atlas, String name, int frameCount, float frameDuration) {
        Array<TextureRegion> regions = new Array<TextureRegion>();

        for (int i = 1; i < frameCount; i++)
            regions.add(atlas.findRegion(name + i));

        return new Animation<TextureRegion>(
                frameDuration,
                regions,
                Animation.PlayMode.LOOP_PINGPONG
        );
    }

    //------------------------------------------------------------//
    //                       INNER CLASSES                        //
    //------------------------------------------------------------//

    public class GigaGalAssets {
        public AtlasRegion standingRight;
        public AtlasRegion standingLeft;
        public AtlasRegion standingUpLeft;
        public AtlasRegion standingUpRight;

        public AtlasRegion jumpingRight;
        public AtlasRegion jumpingLeft;

        public Animation<TextureRegion> walkingLeft;
        public Animation<TextureRegion> walkingRight;

        GigaGalAssets(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.Asset.STANDING_RIGHT);
            standingLeft = atlas.findRegion(Constants.Asset.STANDING_LEFT);

            standingUpRight = atlas.findRegion(Constants.Asset.STANDING_UP_RIGHT);
            standingUpLeft = atlas.findRegion(Constants.Asset.STANDING_UP_LEFT);

            jumpingLeft = atlas.findRegion(Constants.Asset.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.Asset.JUMPING_RIGHT);

            walkingLeft = createAnimation(
                    atlas,
                    Constants.Asset.WALK_LEFT,
                    Constants.Asset.WALK_FRAME_COUNT,
                    Constants.Asset.WALK_FRAME_DURATION
            );

            walkingRight = createAnimation(
                    atlas,
                    Constants.Asset.WALK_RIGHT,
                    Constants.Asset.WALK_FRAME_COUNT,
                    Constants.Asset.WALK_FRAME_DURATION
            );
        }
    }

    public class PlatformAssets {
        public NinePatch passablePlatform;
        public NinePatch solidPlatform;

        PlatformAssets(TextureAtlas atlas) {
            TextureRegion region = atlas.findRegion(Constants.Asset.PLATFORM_PASSABLE);
            passablePlatform = new NinePatch(
                    region,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER
            );

            region = atlas.findRegion(Constants.Asset.PLATFORM_SOLID);
            solidPlatform = new NinePatch(
                    region,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_HORIZONTAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER,
                    Constants.Asset.PLATFORM_VERTICAL_BORDER
            );
        }
    }

    public class EnemyAssets {
        public AtlasRegion basicEnemy;
        public AtlasRegion strongEnemy;
        public AtlasRegion fly8Enemy;
        public AtlasRegion followPathEnemy;
        public AtlasRegion patrolAirEnemy;
        public AtlasRegion fastEnemy;
        public AtlasRegion shootEnemy;

        EnemyAssets(TextureAtlas atlas) {
            basicEnemy = atlas.findRegion(Constants.Asset.ENEMY_BASIC);
            strongEnemy = atlas.findRegion(Constants.Asset.ENEMY_STRONG);
            fly8Enemy = atlas.findRegion(Constants.Asset.ENEMY_FLY_8);
            followPathEnemy = atlas.findRegion(Constants.Asset.ENEMY_FOLLOW_PATH);
            patrolAirEnemy = atlas.findRegion(Constants.Asset.ENEMY_PATROL_AIR);
            fastEnemy = atlas.findRegion(Constants.Asset.ENEMY_FAST);
            shootEnemy = atlas.findRegion(Constants.Asset.ENEMY_SHOOT);
        }
    }

    public class BulletAssets {
        public AtlasRegion leftBullet;
        public AtlasRegion rightBullet;
        public AtlasRegion upBullet;

        public AtlasRegion enemyLaser;
        public Animation<TextureRegion> enemyPlasma;

        BulletAssets(TextureAtlas atlas) {
            leftBullet = atlas.findRegion(Constants.Asset.GGG_BULLET_LEFT);
            rightBullet = atlas.findRegion(Constants.Asset.GGG_BULLET_RIGHT);
            upBullet = atlas.findRegion(Constants.Asset.GGG_BULLET_UP);

            enemyLaser = atlas.findRegion(Constants.Asset.ENEMY_BULLET_LASER);

            Array<TextureRegion> textureRegionArray = new Array<TextureRegion>();
            for (int i = 1; i <= Constants.Asset.ENEMY_BULLET_PLASMA_FRAME_COUNT; i++)
                textureRegionArray.add(atlas.findRegion(Constants.Asset.ENEMY_BULLET_PLASMA + i));
            enemyPlasma = new Animation<TextureRegion>(
                    Constants.Asset.ENEMY_BULLET_PLASMA_FRAME_DURATION,
                    textureRegionArray,
                    Animation.PlayMode.LOOP_PINGPONG
            );
        }
    }

    public class ExplosionAssets {
        private Array<TextureRegion> smallArray;
        private Array<TextureRegion> largeArray;

        ExplosionAssets(TextureAtlas atlas) {
            smallArray = new Array<TextureRegion>();

            smallArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_SMALL));
            smallArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_MEDIUM));
            smallArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_LARGE));

            largeArray = new Array<TextureRegion>();

            largeArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_SMALL_X));
            largeArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_MEDIUM_X));
            largeArray.add(atlas.findRegion(Constants.Asset.EXPLOSION_LARGE_X));
        }

        public Animation<TextureRegion> getExplosionLoop(boolean largeExplosion) {
            if (largeExplosion)
                return new Animation<TextureRegion>(Constants.Asset.EXPLOSION_DURATION, largeArray, Animation.PlayMode.NORMAL);
            return new Animation<TextureRegion>(Constants.Asset.EXPLOSION_DURATION, smallArray, Animation.PlayMode.NORMAL);
        }
    }

    public class BonusAssets {
        public AtlasRegion health;
        public AtlasRegion life;
        public AtlasRegion bullet;

        BonusAssets(TextureAtlas atlas) {
            health = atlas.findRegion(Constants.Asset.BONUS_HEALTH);
            life = atlas.findRegion(Constants.Asset.BONUS_LIFE);
            bullet = atlas.findRegion(Constants.Asset.BONUS_BULLET);
        }
    }

    public class ExitPortalAssets {
        public Animation<TextureRegion> animation;

        ExitPortalAssets(TextureAtlas atlas) {
            animation = createAnimation(
                    atlas,
                    Constants.Asset.EXIT_PORTAL,
                    Constants.Asset.EXIT_PORTAL_FRAME_COUNT,
                    Constants.Asset.EXIT_PORTAL_FRAME_DURATION
            );
        }
    }

    public class OnscreenControlAssets {
        public AtlasRegion leftButton;
        public AtlasRegion rightButton;
        public AtlasRegion upButton;
        public AtlasRegion downButton;

        public AtlasRegion jumpButton;
        public AtlasRegion shootButton;

        OnscreenControlAssets(TextureAtlas atlas) {
            leftButton = atlas.findRegion(Constants.Asset.BUTTON_LEFT);
            rightButton = atlas.findRegion(Constants.Asset.BUTTON_RIGHT);
            upButton = atlas.findRegion(Constants.Asset.BUTTON_UP);
            downButton = atlas.findRegion(Constants.Asset.BUTTON_DOWN);

            jumpButton = atlas.findRegion(Constants.Asset.BUTTON_JUMP);
            shootButton = atlas.findRegion(Constants.Asset.BUTTON_SHOOT);
        }
    }

    public class backgroundAssets {
        public TextureRegion mainMenu;
        public TextureRegion selectLevel;
        public TextureRegion setting;
        public TextureRegion overlay;

        backgroundAssets(TextureAtlas atlas) {
            mainMenu = atlas.findRegion(Constants.Asset.BG_MAIN_MENU);
            selectLevel = atlas.findRegion(Constants.Asset.BG_SELECT_LEVEL);
            setting = atlas.findRegion(Constants.Asset.BG_SETTING);
            overlay = atlas.findRegion(Constants.Asset.BG_OVERLAY);
        }
    }
}