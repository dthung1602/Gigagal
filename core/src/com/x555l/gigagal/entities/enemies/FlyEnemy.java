package com.x555l.gigagal.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.util.Enum.Facing;
import com.x555l.gigagal.util.Util;


abstract class FlyEnemy extends Enemy {
    private Vector2[] vertices; // save absolute positions of vertices of the path
    private Vector2[] velocities; // save speed of enemy on each vertex
    private float[] travelTime; // save relative time since the begin of circle that enemy reach vertex
    private Facing[] facings; // save facing state of each edge
    private int edgeNumber; // number of edge in the path
    private float totalTime; // total time enemy transverse the polygon

    /**
     * Constructor for fly enemy that does not uses vertices to mark its path
     */
    FlyEnemy(int health, Vector2 offset, TextureRegion textureRegion) {
        super();
        this.health = health;
        this.center = offset;
        this.textureRegion = textureRegion;
    }

    /**
     * Constructor for enemy that follow a path defined by a polygon
     *
     * @param vertices: vertices of the polygon
     * @param speed:    speed of enemy on the whole path
     */
    FlyEnemy(Vector2[] vertices, int health, float speed, Vector2 offset, TextureRegion textureRegion) {
        this.vertices = vertices;
        this.health = health;
        this.center = offset;
        this.textureRegion = textureRegion;

        edgeNumber = vertices.length - 1; // last vertex is also the first

        velocities = new Vector2[edgeNumber];
        travelTime = new float[edgeNumber];
        facings = new Facing[edgeNumber];

        totalTime = 0;

        for (int i = 0; i < edgeNumber; i++) {
            Vector2 edge = vertices[i + 1].sub(vertices[i]);
            facings[i] = (edge.x > 0) ? Facing.RIGHT : Facing.LEFT;

            travelTime[i] = totalTime;
            totalTime += edge.len() / speed;

            velocities[i] = edge.setLength(speed);
        }
    }

    @Override
    public void update(float delta) {
        // time since beginning of the cycle
        float time = Util.secondsSince(startTime) % totalTime;

        // find which edge enemy is on
        int edge = 0;
        for (int i = 1; i < edgeNumber; i++) {
            if (travelTime[i] > time) {
                edge = i - 1;
            }
        }

        // set position
        position = vertices[edge].cpy().mulAdd(velocities[edge], delta);

        // TODO update texture region base on facings[edge]
    }
}
