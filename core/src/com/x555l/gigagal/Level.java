package com.x555l.gigagal;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.*;
import com.x555l.gigagal.entities.*;
import com.x555l.gigagal.util.Enum.Facing;
import com.x555l.gigagal.util.Constants;


public class Level {
    public int levelNum;

    private GigaGal gigagal;
    private ExitPortal exitPortal;
    private Array<Platform> platforms;
    private DelayedRemovalArray<Enemy> enemies;
    private DelayedRemovalArray<Bullet> bullets;
    private DelayedRemovalArray<Explosion> explosions;
    private DelayedRemovalArray<Powerup> powerups;

    private Viewport viewport;

    public boolean victory;
    public boolean gameover;

    public Level(int levelNum) {
        this.levelNum = levelNum;

        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();
        bullets = new DelayedRemovalArray<Bullet>();
        explosions = new DelayedRemovalArray<Explosion>();
        powerups = new DelayedRemovalArray<Powerup>();

        victory = false;
        gameover = false;
    }

    void update(float delta) {
        // freeze everything when victory or gameover for endLevelLayer
        if (victory || gameover)
            return;

        gigagal.update(delta);

        for (Enemy enemy : enemies) {
            if (enemy.health >= 1) {
                enemy.update(delta);
            } else {
                enemies.removeValue(enemy, true);
                addNewExplosion(enemy.position, true);
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

    void render(SpriteBatch batch) {
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

        for (Powerup powerup : powerups) {
            powerup.render(batch);
        }

        exitPortal.render(batch);

        gigagal.render(batch);

        batch.end();
    }

    void initDebugLevel() {
        gigagal = new GigaGal(this, new Vector2(80, 80));

        platforms.add(new Platform(30, 8, 100, 30));
        platforms.add(new Platform(60, 50, 50, 10));
        platforms.add(new Platform(20, 80, 10, 50));
        platforms.add(new Platform(100, 80, 80, 20));
        platforms.add(new Platform(80, 120, 40, 20));
        platforms.add(new Platform(40, 160, 40, 20));
        platforms.add(new Platform(20, 200, 40, 20));
        platforms.add(new Platform(200, 40, 50, 30));
        platforms.add(new Platform(250, 90, 150, 10));

        for (int i = 5; i < 9; i++)
            enemies.add(new Enemy(platforms.get(i)));

        powerups.add(new Powerup(30, 120));
        powerups.add(new Powerup(50, 140));
        powerups.add(new Powerup(150, 170));
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

    public DelayedRemovalArray<Enemy> getEnemies() {
        return enemies;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public DelayedRemovalArray<Powerup> getPowerups() {
        return powerups;
    }

    public ExitPortal getExitPortal() {
        return exitPortal;
    }

    public void setGigagal(GigaGal gigagal) {
        this.gigagal = gigagal;
    }

    public void setExitPortal(ExitPortal exitPortal) {
        this.exitPortal = exitPortal;
    }

    public Level getNewDebugLevel() {
        Level level = new Level(0);
        level.initDebugLevel();
        return level;
    }
}
