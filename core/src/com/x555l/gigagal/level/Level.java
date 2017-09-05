package com.x555l.gigagal.level;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.entities.Bullet;
import com.x555l.gigagal.entities.bonus.BonusHealth;
import com.x555l.gigagal.entities.enemies.BasicEnemy;
import com.x555l.gigagal.entities.ExitPortal;
import com.x555l.gigagal.entities.Explosion;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bonus.Bonus;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Enum.Facing;


public class Level {
    public int levelNum;

    private GigaGal gigagal;
    private ExitPortal exitPortal;
    private Array<Platform> platforms;
    private DelayedRemovalArray<BasicEnemy> enemies;
    private DelayedRemovalArray<Bullet> bullets;
    private DelayedRemovalArray<Explosion> explosions;
    private DelayedRemovalArray<Bonus> bonuses;

    private Viewport viewport;

    public boolean victory;
    public boolean gameover;

    Level(int levelNum) {
        this.levelNum = levelNum;

        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<BasicEnemy>();
        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        bonuses = new DelayedRemovalArray<Bonus>();

        victory = false;
        gameover = false;
    }

    public void update(float delta) {
        // freeze everything when victory or gameover for endLevelLayer
        if (victory || gameover)
            return;

        gigagal.update(delta);

        for (BasicEnemy basicEnemy : enemies) {
            if (basicEnemy.health >= 1) {
                basicEnemy.update(delta);
            } else {
                enemies.removeValue(basicEnemy, true);
                addNewExplosion(basicEnemy.position, true);
            }
        }

        for (Bullet bullet : bullets) {
            if (bullet.active)
                bullet.update(delta);
            else
                bullets.removeValue(bullet, true);
        }

        for (Explosion explosion : explosions) {
            if (explosion.finished)
                explosions.removeValue(explosion, true);
        }
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        for (Platform platform : platforms) {
            platform.render(batch);
        }

        for (BasicEnemy basicEnemy : enemies) {
            basicEnemy.render(batch);
        }

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }

        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }

        for (Bonus powerup : bonuses) {
            powerup.render(batch);
        }

        exitPortal.render(batch);

        gigagal.render(batch);

        batch.end();
    }

    private void initDebugLevel() {
        gigagal = new GigaGal(this, new Vector2(80, 80));

        platforms.add(new Platform(true, 30, 8, 100, 30));
        platforms.add(new Platform(true, 60, 50, 50, 10));
        platforms.add(new Platform(true, 20, 80, 10, 50));
        platforms.add(new Platform(true, 100, 80, 80, 20));
        platforms.add(new Platform(true, 80, 120, 40, 20));
        platforms.add(new Platform(true, 40, 160, 40, 20));
        platforms.add(new Platform(true, 20, 200, 40, 20));
        platforms.add(new Platform(true, 200, 40, 50, 30));
        platforms.add(new Platform(true, 250, 90, 150, 10));

        for (int i = 5; i < 9; i++)
            enemies.add(new BasicEnemy(platforms.get(i)));

        bonuses.add(new BonusHealth(30, 120));
        bonuses.add(new BonusHealth(50, 140));
        bonuses.add(new BonusHealth(150, 170));
    }

    public void addNewBullet(float x, float y, Facing direction) {
        bullets.add(new Bullet(x, y, direction, this));
    }

    public void addNewExplosion(Vector2 position, boolean largeExplosion) {
        explosions.add(new Explosion(position, largeExplosion));
    }

    public GigaGal getGigagal() {
        return gigagal;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public DelayedRemovalArray<BasicEnemy> getEnemies() {
        return enemies;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public DelayedRemovalArray<Bonus> getBonuses() {
        return bonuses;
    }

    public ExitPortal getExitPortal() {
        return exitPortal;
    }

    public void setGigagal(GigaGal gigagal) {
        this.gigagal = gigagal;
    }

    void setExitPortal(ExitPortal exitPortal) {
        this.exitPortal = exitPortal;
    }

    public static Level getNewDebugLevel() {
        Level level = new Level(0);
        level.initDebugLevel();
        return level;
    }
}
