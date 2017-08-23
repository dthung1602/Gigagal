package com.x555l.gigagal;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.x555l.gigagal.entities.Enemy;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;

class Level {
    GigaGal gigagal;
    private Array<Platform> platforms;
    private DelayedRemovalArray<Enemy> enemies;

    Level() {
        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();

        initDebugLevel();
    }

    void update(float delta) {
        gigagal.update(delta, platforms);

        for (Enemy enemy : enemies) {
            enemy.update(delta);
        }
    }

    void render(SpriteBatch batch) {
        batch.begin();

        for (Platform platform : platforms) {
            platform.render(batch);
        }

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        gigagal.render(batch);

        batch.end();
    }

    void initDebugLevel() {
        gigagal = new GigaGal(new Vector2(80, 80));

        platforms.add(new Platform(30, 8, 100, 30));
        platforms.add(new Platform(60, 50, 50, 10));
        platforms.add(new Platform(20, 80, 10, 50));
        platforms.add(new Platform(100, 80, 80, 20));
        platforms.add(new Platform(80, 120, 40, 20));
        platforms.add(new Platform(40, 160, 40, 20));
        platforms.add(new Platform(20, 200, 40, 20));
        platforms.add(new Platform(200, 40, 50, 30));
        platforms.add(new Platform(250, 90, 150, 10));

        enemies.add(new Enemy(platforms.get(8)));
    }
}
