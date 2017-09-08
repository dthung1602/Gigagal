package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.util.Enum.Facing;
import com.x555l.gigagal.util.Util;


abstract class NonPlatformEnemy extends Enemy {
    private Vector2[] vertices; // save absolute positions of vertices of the path
    private Vector2[] velocities; // save speed of enemy on each vertex
    private float[] travelTime; // save relative time since the begin of circle that enemy reach vertex
    private Facing[] facings; // save facing state of each edge
    private int edgeNumber; // number of edge in the path
    private float totalTime; // total time enemy transverse the polygon

    /**
     * Constructor for fly enemy that does NOT uses vertices to mark its path
     */
    NonPlatformEnemy(int health, Vector2 center, TextureRegion textureRegion) {
        super();
        this.health = health;
        this.center = center;
        this.textureRegion = textureRegion;
    }

    /**
     * Constructor for enemy that follow a path defined by a polygon
     *
     * @param vertices: vertices of the polygon
     * @param speed:    speed of enemy on the whole path
     */
    NonPlatformEnemy(Vector2[] vertices, int health, float speed, Vector2 center, TextureRegion textureRegion) {
        super();
        this.vertices = vertices;
        this.health = health;
        this.center = center;
        this.textureRegion = textureRegion;

        position = vertices[0].cpy();

        edgeNumber = vertices.length - 1; // last vertex is also the first

        velocities = new Vector2[edgeNumber];
        travelTime = new float[edgeNumber + 1];
        facings = new Facing[edgeNumber];

        totalTime = 0;
        travelTime[0] = 0;

        for (int i = 0; i < edgeNumber; i++) {
            Vector2 edge = vertices[i + 1].cpy().sub(vertices[i]);
            facings[i] = (edge.x > 0) ? Facing.RIGHT : Facing.LEFT;

            totalTime += edge.len() / speed;
            travelTime[i + 1] = totalTime;

            velocities[i] = edge.setLength(speed);
        }
    }

    @Override
    public void update(float delta) {
        // time since beginning of the cycle
        float time = Util.secondsSince(startTime) % totalTime;

        // find which edge enemy is on
        int edge = 0;
        for (int i = 0; i < edgeNumber; i++) {
            if (travelTime[i + 1] > time) {
                edge = i;
                break;
            }
        }

        // set position
        position.set(vertices[edge]).mulAdd(velocities[edge], time - travelTime[edge]);

        // TODO update texture region base on facings[edge]
    }
}
