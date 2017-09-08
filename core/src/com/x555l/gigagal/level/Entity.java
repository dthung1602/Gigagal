package com.x555l.gigagal.level;

import com.badlogic.gdx.math.Vector2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * A class loads and contains all property of an json object
 */
public class Entity {
    public float x, y;
    public float width, height;
    public int gid;
    public String type;
    public Vector2[] polygon;
    public Vector2[] polyline;

    Entity(JSONObject jsonObject, float mapHeight) {
        width = getFloat(jsonObject, "width");
        height = getFloat(jsonObject, "height");

        x = getFloat(jsonObject, "x");
        y = mapHeight - getFloat(jsonObject, "y"); // reverse y axis

        gid = getInt(jsonObject, "gid") - 1; // for some reason gid in tileset file is 1 off from gid in level.json
        type = (String) jsonObject.get("type");

        polygon = getPolygon(jsonObject, x, y, mapHeight);
        polyline = getPolyline(jsonObject, x, y, mapHeight);
    }

    /**
     * Get float value of a property from a JSON object
     */
    private float getFloat(JSONObject jsonObject, String propertyName) {
        if (!jsonObject.containsKey(propertyName))
            return -1;
        Number number = (Number) jsonObject.get(propertyName);
        return number.floatValue();
    }

    /**
     * Get int value of a property from a JSON object
     */
    private int getInt(JSONObject jsonObject, String propertyName) {
        if (!jsonObject.containsKey(propertyName))
            return -1;
        Number number = (Number) jsonObject.get(propertyName);
        return number.intValue();
    }

    /**
     * Get and prepare polygon for enemy path
     */
    private Vector2[] getPolygon(JSONObject jsonObject, float x, float y, float mapHeight) {
        if (!jsonObject.containsKey("polygon"))
            return null;
        JSONArray polygonArray = (JSONArray) jsonObject.get("polygon");

        // add first vertex to the last position
        polygonArray.add(polygonArray.get(0));

        Vector2[] polygon = convertToArray(polygonArray);

        for (Vector2 vertex : polygon) {
            vertex.x += x;
            vertex.y = y - vertex.y;
        }

        return polygon;
    }

    /**
     * Get and prepare polyline for enemy path
     */
    private Vector2[] getPolyline(JSONObject jsonObject, float x, float y, float mapHeight) {
        if (!jsonObject.containsKey("polyline"))
            return null;
        JSONArray polylineArray = (JSONArray) jsonObject.get("polyline");

        // reverse the polyline and add to itself to create a round-trip
        int length = polylineArray.size();
        for (int i = length - 2; i >= 0; i--) {
            polylineArray.add(polylineArray.get(i));
        }

        Vector2[] polyline = convertToArray(polylineArray);

        for (Vector2 vertex : polyline) {
            vertex.x += x;
            vertex.y = y - vertex.y;
        }

        return polyline;
    }

    /**
     * Converts a JSON array to a normal java array of Vector2
     */
    private Vector2[] convertToArray(JSONArray jsonArray) {
        int size = jsonArray.size();
        Vector2[] array = new Vector2[size];
        for (int i = 0; i < size; i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            array[i] = new Vector2(
                    getInt(jsonObject, "x"),
                    getInt(jsonObject, "y")
            );
        }
        return array;
    }
}
