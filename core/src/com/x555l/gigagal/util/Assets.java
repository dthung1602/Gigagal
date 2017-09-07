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
        skin = new Skin(Gdx.files.internal("skin/skin.json"));

        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.GIGAGAL_ATLAS, TextureAtlas.class);
        assetManager.load(Constants.BACKGROUND_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas gigagalAtlas = assetManager.get(Constants.GIGAGAL_ATLAS);
        TextureAtlas backgroundAtlas = assetManager.get(Constants.BACKGROUND_ATLAS);

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

        public Animation walkingLeft;
        public Animation walkingRight;

        GigaGalAssets(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);

            standingUpRight = atlas.findRegion(Constants.STANDING_UP_RIGHT);
            standingUpLeft = atlas.findRegion(Constants.STANDING_UP_LEFT);
            
            jumpingLeft = atlas.findRegion(Constants.JUMPING_LEFT);
            jumpingRight = atlas.findRegion(Constants.JUMPING_RIGHT);

            Array<AtlasRegion> regions = new Array<AtlasRegion>();
            regions.add(atlas.findRegion(Constants.WALK_1_LEFT));
            regions.add(atlas.findRegion(Constants.WALK_2_LEFT));
            regions.add(atlas.findRegion(Constants.WALK_3_LEFT));
            walkingLeft = new Animation(Constants.WALK_LOOP_DURATION, regions, Animation.PlayMode.LOOP_PINGPONG);

            regions.clear();
            regions.add(atlas.findRegion(Constants.WALK_1_RIGHT));
            regions.add(atlas.findRegion(Constants.WALK_2_RIGHT));
            regions.add(atlas.findRegion(Constants.WALK_3_RIGHT));
            walkingRight = new Animation(Constants.WALK_LOOP_DURATION, regions, Animation.PlayMode.LOOP_PINGPONG);
        }
    }

    public class PlatformAssets {
        public NinePatch passablePlatform;
        public NinePatch solidPlatform;

        PlatformAssets(TextureAtlas atlas) {
            TextureRegion region = atlas.findRegion(Constants.PLATFORM_PASSABLE);
            passablePlatform = new NinePatch(
                    region,
                    Constants.HORIZONTAL_BORDER,
                    Constants.HORIZONTAL_BORDER,
                    Constants.VERTICAL_BORDER,
                    Constants.VERTICAL_BORDER
            );

            region = atlas.findRegion(Constants.PLATFORM_SOLID);
            solidPlatform = new NinePatch(
                    region,
                    Constants.HORIZONTAL_BORDER,
                    Constants.HORIZONTAL_BORDER,
                    Constants.VERTICAL_BORDER,
                    Constants.VERTICAL_BORDER
            );
        }
    }

    public class EnemyAssets {
        public AtlasRegion basicEnemy;
        public AtlasRegion strongEnemy;
        public AtlasRegion fly8Enemy;

        EnemyAssets(TextureAtlas atlas) {
            basicEnemy = atlas.findRegion(Constants.ENEMY_BASIC);
            strongEnemy = atlas.findRegion(Constants.ENEMY_STRONG);
            fly8Enemy = atlas.findRegion(Constants.ENEMY_FLY_8);
        }
    }

    public class BulletAssets {
        public AtlasRegion leftBullet;
        public AtlasRegion rightBullet;
        public AtlasRegion upBullet;

        BulletAssets(TextureAtlas atlas) {
            leftBullet = atlas.findRegion(Constants.BULLET_LEFT);
            rightBullet = atlas.findRegion(Constants.BULLET_RIGHT);
            upBullet = atlas.findRegion(Constants.BULLET_UP);
        }
    }

    public class ExplosionAssets {
        private Array<TextureRegion> smallArray;
        private Array<TextureRegion> largeArray;

        ExplosionAssets(TextureAtlas atlas) {
            smallArray = new Array<TextureRegion>();

            smallArray.add(atlas.findRegion(Constants.EXPLOSION_SMALL));
            smallArray.add(atlas.findRegion(Constants.EXPLOSION_MEDIUM));
            smallArray.add(atlas.findRegion(Constants.EXPLOSION_LARGE));

            largeArray = new Array<TextureRegion>();

            largeArray.add(atlas.findRegion(Constants.EXPLOSION_SMALL_X));
            largeArray.add(atlas.findRegion(Constants.EXPLOSION_MEDIUM_X));
            largeArray.add(atlas.findRegion(Constants.EXPLOSION_LARGE_X));
        }

        public Animation getExplosionLoop(boolean largeExplosion) {
            if (largeExplosion)
                return new Animation(Constants.EXPLOSION_DURATION, largeArray, Animation.PlayMode.NORMAL);
            return new Animation(Constants.EXPLOSION_DURATION, smallArray, Animation.PlayMode.NORMAL);
        }
    }

    public class BonusAssets {
        public AtlasRegion health;
        public AtlasRegion life;
        public AtlasRegion bullet;

        BonusAssets(TextureAtlas atlas) {
            health = atlas.findRegion(Constants.BONUS_HEALTH);
            life = atlas.findRegion(Constants.BONUS_LIFE);
            bullet = atlas.findRegion(Constants.BONUS_BULLET);
        }
    }

    public class ExitPortalAssets {
        public Animation animation;

        ExitPortalAssets(TextureAtlas atlas) {
            Array<TextureRegion> array = new Array<TextureRegion>();

            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_1));
            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_2));
            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_3));
            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_4));
            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_5));
            array.add(atlas.findRegion(Constants.EXIT_PORTAL_SPRITE_6));

            animation = new Animation(
                    Constants.EXIT_PORTAL_FRAME_DURATION,
                    array,
                    Animation.PlayMode.LOOP_REVERSED
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
            leftButton = atlas.findRegion(Constants.BUTTON_LEFT);
            rightButton = atlas.findRegion(Constants.BUTTON_RIGHT);
            upButton = atlas.findRegion(Constants.BUTTON_UP);
            downButton = atlas.findRegion(Constants.BUTTON_DOWN);
            
            jumpButton = atlas.findRegion(Constants.BUTTON_JUMP);
            shootButton = atlas.findRegion(Constants.BUTTON_SHOOT);
        }
    }

    public class backgroundAssets {
        public TextureRegion mainMenu;
        public TextureRegion selectLevel;
        public TextureRegion setting;
        public TextureRegion overlay;

        backgroundAssets(TextureAtlas atlas){
            mainMenu = atlas.findRegion(Constants.BG_MAIN_MENU);
            selectLevel = atlas.findRegion(Constants.BG_SELECT_LEVEL);
            setting = atlas.findRegion(Constants.BG_SETTING);
            overlay= atlas.findRegion(Constants.BG_OVERLAY);
        }
    }
}