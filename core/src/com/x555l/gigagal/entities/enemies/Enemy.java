package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.x555l.gigagal.entities.Explosion;
import com.x555l.gigagal.level.Level;
import com.x555l.gigagal.util.Enum.Facing;

/**
 * Abstract class for all enemies
 */
public abstract class Enemy {
    Level level;

    public Vector2 position;
    Vector2 velocity;
    Vector2 center;

    public EnemyShape boundary;

    public int health;
    long startTime;

    boolean ableToDetectGigagal;
    boolean gigagalDetected;

    Facing facing;
    TextureRegion textureRegion;

    Enemy(Level level) {
        this.level = level;
        startTime = TimeUtils.nanoTime();
        facing = Facing.RIGHT;
    }

    /**
     * Return true if enemy detect ggg and false otherwise
     * Also set gigagalDetected accordingly
     * Return true by default
     */
    boolean detectGigagal() {
        gigagalDetected = true;
        return true;
    }

    /**
     * Enemy try to attack the given target
     * There are 2 convenient implementations of this method: push and shoot
     * Enemy does nothing by default
     */
    void attack(float delta, Vector2 target) {

    }

    /**
     * Enemy shoots bullets at the given target
     */
    void shoot(float delta, Vector2 target) {

    }

    /**
     * Enemy rush to target and push
     */
    void push(float delta, Vector2 target) {

    }

    public void die() {
        // remove enemy from enemy list
        level.getEnemies().removeValue(this, true);
        // new explosion
        level.getExplosions().add(new Explosion(position, true));
    }

    abstract public void update(float delta);

    public void render(SpriteBatch batch) {
        batch.draw(
                textureRegion,
                position.x - center.x,
                position.y - center.y
        );
    }

    //-------------------------------------------
    //            NESTED CLASSES
    //-------------------------------------------

    /**
     * Contains methods and properties to detect bullet/gigagal collision
     */

    public abstract class EnemyShape {

        abstract public boolean hitByBullet(Vector2 bulletPosition);

        abstract public boolean hitByGigagal(Rectangle gigagalBoundary);
    }

    /**
     * Class for enemies with circular boundary
     * Requires radius
     */

    class CircularShape extends EnemyShape {
        private float radius;

        CircularShape() {
            radius = (center.x + center.y) / 2;
        }

        @Override
        public boolean hitByBullet(Vector2 bulletPosition) {
            return position.dst(bulletPosition) < radius;
        }

        @Override
        public boolean hitByGigagal(Rectangle gigagalBoundary) {
            Rectangle extendRectangle = new Rectangle(
                    gigagalBoundary.x - radius,
                    gigagalBoundary.y - radius,
                    gigagalBoundary.width + radius * 2,
                    gigagalBoundary.height + radius * 2);
            return extendRectangle.contains(position.x, position.y);
        }
    }

    /**
     * Class for enemy with rectangular boundary
     * Requires width and height
     */
    class RectangularShape extends EnemyShape {
        private float width;
        private float height;

        RectangularShape() {
            width = textureRegion.getRegionWidth();
            height = textureRegion.getRegionHeight();
        }

        @Override
        public boolean hitByBullet(Vector2 bulletPosition) {
            return getBoundary().contains(bulletPosition.x, bulletPosition.y);
        }

        @Override
        public boolean hitByGigagal(Rectangle gigagalBoundary) {
            return getBoundary().overlaps(gigagalBoundary);
        }

        private Rectangle getBoundary() {
            return new Rectangle(
                    position.x - center.x,
                    position.y - center.y,
                    width,
                    height
            );
        }
    }
}
