package com.x555l.gigagal.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Gdx;


public class Assets implements Disposable, AssetErrorListener {
    // for debug
    private static final String TAG = Assets.class.getName();

    // singleton
    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    // hold assets of different objects
    public GigaGalAssets gigaGalAssets;
    public PlatformAssets platformAssets;
    public EnemyAssets enemyAssets;
    public BulletAssets bulletAssets;
    public ExplosionAssets explosionAssets;
    public PowerupAssets powerupAssets;

    // ensure singleton
    private Assets() {
    }

    public void init() {
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas textureAtlas = assetManager.get(Constants.TEXTURE_ATLAS);
        gigaGalAssets = new GigaGalAssets(textureAtlas);
        platformAssets = new PlatformAssets(textureAtlas);
        enemyAssets = new EnemyAssets(textureAtlas);
        bulletAssets = new BulletAssets(textureAtlas);
        explosionAssets = new ExplosionAssets(textureAtlas);
        powerupAssets = new PowerupAssets(textureAtlas);
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

        public AtlasRegion jumpingRight;
        public AtlasRegion jumpingLeft;

        public Animation walkingLeft;
        public Animation walkingRight;

        GigaGalAssets(TextureAtlas atlas) {
            standingRight = atlas.findRegion(Constants.STANDING_RIGHT);
            standingLeft = atlas.findRegion(Constants.STANDING_LEFT);

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
        public NinePatch ninePatch;

        PlatformAssets(TextureAtlas atlas) {
            TextureRegion region = atlas.findRegion(Constants.PLATFORM);
            ninePatch = new NinePatch(
                    region,
                    Constants.HORIZONTAL_BORDER,
                    Constants.HORIZONTAL_BORDER,
                    Constants.VERTICAL_BORDER,
                    Constants.VERTICAL_BORDER
            );
        }
    }

    public class EnemyAssets {
        public AtlasRegion region;

        EnemyAssets(TextureAtlas atlas) {
            region = atlas.findRegion(Constants.ENEMY);
        }
    }

    public class BulletAssets {
        public AtlasRegion leftBullet;
        public AtlasRegion rightBullet;

        BulletAssets(TextureAtlas atlas) {
            leftBullet = atlas.findRegion(Constants.BULLET_LEFT);
            rightBullet = atlas.findRegion(Constants.BULLET_RIGHT);
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

    public class PowerupAssets {
        public AtlasRegion region;

        PowerupAssets(TextureAtlas atlas) {
            region = atlas.findRegion(Constants.ENEMY);
        }
    }
}
