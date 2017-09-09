package com.x555l.gigagal.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.entities.ExitPortal;
import com.x555l.gigagal.entities.Explosion;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bonus.Bonus;
import com.x555l.gigagal.entities.bullets.Bullet;
import com.x555l.gigagal.entities.enemies.Enemy;
import com.x555l.gigagal.util.Constants;


public class Level {
    public int levelNum;

    private GigaGal gigagal;
    private ExitPortal exitPortal;
    private Array<Platform> platforms;
    private DelayedRemovalArray<Enemy> enemies;
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
        enemies = new DelayedRemovalArray<Enemy>();
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

        for (Enemy enemy : enemies) {
            if (enemy.health >= 1) {
                enemy.update(delta);
            } else {
                enemy.die();
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

        for (Enemy enemy : enemies) {
            enemy.render(batch);
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

    //---------------------------------------------------------------
    //                         GETTERS
    //---------------------------------------------------------------

    public GigaGal getGigagal() {
        return gigagal;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public DelayedRemovalArray<Enemy> getEnemies() {
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

    public DelayedRemovalArray<Bullet> getBullets() {
        return bullets;
    }

    public DelayedRemovalArray<Explosion> getExplosions() {
        return explosions;
    }

    //---------------------------------------------------------------
    //                          SETTERS
    //---------------------------------------------------------------

    void setGigagal(GigaGal gigagal) {
        this.gigagal = gigagal;
    }

    void setExitPortal(ExitPortal exitPortal) {
        this.exitPortal = exitPortal;
    }
}
