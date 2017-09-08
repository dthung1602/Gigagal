package com.x555l.gigagal.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.x555l.gigagal.entities.ExitPortal;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bonus.Bonus;
import com.x555l.gigagal.entities.enemies.Enemy;
import com.x555l.gigagal.entities.enemies.FollowPathEnemy;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Constructor;
import java.util.HashMap;


/**
 * Create a level using data from a json file
 */
public class LevelLoader {
    // for debugging
    private static final String TAG = LevelLoader.class.getName();

    /**
     * Maps from an object's gid to actual class name
     */

    private static final HashMap<Integer, String> gidToEntity = createMapping();

    /**
     * Create and return a hash map from gid (number string i.e "0", "1") to entity type (string)
     */
    private static HashMap<Integer, String> createMapping() {
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        long tileCount = 0;
        JSONObject tiles = null;

        try {
            // parse json file
            FileHandle fileHandle = Gdx.files.internal(Constants.LEVEL_TILESET_FILE);
            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(fileHandle.reader());
            tileCount = (Long) rootJsonObject.get("tilecount");
            tiles = (JSONObject) rootJsonObject.get("tiles");
        } catch (Exception ex) {
            Util.exitWithError(TAG, ex);
        }

        for (int i = 0; i < tileCount; i++) {
            String rawImageName = (String) ((JSONObject) tiles.get("" + i)).get("image");
            String entityName = rawImageName.substring("icon/".length(), rawImageName.indexOf("."));
            hashMap.put(i, entityName);
        }

        return hashMap;
    }

    /**
     * Create a level using data from a json file
     *
     * @param levelNum: integer specifies which number to load
     * @return newly created level
     */
    public static Level load(int levelNum) {

        Level level = new Level(levelNum);

        try {
            // path to json file
            String path = "levels/level" + levelNum + ".json";

            // parse json file
            FileHandle file = Gdx.files.internal(path);
            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());

            // get map height to reverse y axis
            float mapHeight = (Long) rootJsonObject.get("height") // number of vertical tile
                    * (Long) rootJsonObject.get("tileheight");   // height of 1 tile in px

            // get layers
            JSONArray layers = (JSONArray) rootJsonObject.get("layers");

            // create entities
            createPlatform(getObjectArray(layers, "platform"), mapHeight, level);
            createNonPlatformEnemy(getObjectArray(layers, "fly-enemy"), mapHeight, level);
            createBonus(getObjectArray(layers, "bonus"), mapHeight, level);
            createLevel(getObjectArray(layers, "level"), mapHeight, level);

        } catch (Exception ex) {
            Util.exitWithError(TAG, ex);
        }

        return level;
    }

    /**
     * Get "objects" array of a layer in layer array
     *
     * @param layerArray: json array of all layers
     * @param layerName:  name of the layer to get "objects"
     */
    private static JSONArray getObjectArray(JSONArray layerArray, String layerName) {
        for (Object object : layerArray) {
            JSONObject jsonObject = (JSONObject) object;
            if (layerName.equals(jsonObject.get("name"))) {
                return (JSONArray) jsonObject.get("objects");
            }
        }
        return null;
    }

    /**
     * Create platforms and platform patrol enemies defined in json array
     *
     * @param mapHeight: height of the map to reverse the y axis
     */
    private static void createPlatform(JSONArray platformJSONArray, float mapHeight, Level level) {
        Array<Platform> platforms = level.getPlatforms();
        DelayedRemovalArray<Enemy> enemies = level.getEnemies();

        for (Object object : platformJSONArray) {
            // entity contains all info
            Entity entity = new Entity((JSONObject) object, mapHeight);

            // get kind of platform by gid
            boolean passable = gidToEntity.get(entity.gid).equals("PlatformPassable");

            // create platform
            Platform platform = new Platform(
                    passable,
                    entity.x,
                    entity.y,
                    entity.width,
                    entity.height
            );
            platforms.add(platform);

            // create enemy (if any) using refection
            if (entity.type != null && entity.type.length() > 0)
                try {
                    String fullClassName = "com.x555l.gigagal.entities.enemies." + entity.type;
                    Class<?> cls = Class.forName(fullClassName);
                    Constructor<?> constructor = cls.getConstructor(Platform.class);
                    Enemy enemy = (Enemy) constructor.newInstance(platform);
                    enemies.add(enemy);
                } catch (Exception ex) {
                    Util.exitWithError(TAG, ex);
                }
        }
    }

    /**
     * Create enemies defined in json array
     *
     * @param mapHeight: height of the map to reverse the y axis
     */
    private static void createNonPlatformEnemy(JSONArray enemyJSONArray, float mapHeight, Level level) {
        DelayedRemovalArray<Enemy> enemies = level.getEnemies();

        for (Object object : enemyJSONArray) {
            // create entity to hold info
            Entity entity = new Entity((JSONObject) object, mapHeight);

            // DEBUG
            if (gidToEntity.get(entity.gid) == null) {
                enemies.add(new FollowPathEnemy(entity));
                return;
            }

            // use reflection to create bonus
            try {
                String fullClassName = "com.x555l.gigagal.entities.enemies." + gidToEntity.get(entity.gid);
                Class<?> cls = Class.forName(fullClassName);
                Constructor<?> constructor = cls.getConstructor(Entity.class);
                Enemy enemy = (Enemy) constructor.newInstance(entity);
                enemies.add(enemy);
            } catch (Exception ex) {
                Util.exitWithError(TAG, ex);
            }
        }
    }


    /**
     * Create upgrades defined in json array
     *
     * @param mapHeight: height of the map to reverse the y axis
     */
    private static void createBonus(JSONArray upgradeJSONArray, float mapHeight, Level level) {
        DelayedRemovalArray<Bonus> bonuses = level.getBonuses();

        for (Object object : upgradeJSONArray) {
            // create entity to hold info
            Entity entity = new Entity((JSONObject) object, mapHeight);

            // use reflection to create bonus
            try {
                String fullClassName = "com.x555l.gigagal.entities.bonus." + gidToEntity.get(entity.gid);
                Class<?> cls = Class.forName(fullClassName);
                Constructor<?> constructor = cls.getConstructor(float.class, float.class);
                Bonus bonus = (Bonus) constructor.newInstance(entity.x, entity.y);
                bonuses.add(bonus);
            } catch (Exception ex) {
                Util.exitWithError(TAG, ex);
            }
        }
    }

    /**
     * Create Gigagal and exit portal defined in json array
     *
     * @param mapHeight: height of the map to reverse the y axis
     */
    private static void createLevel(JSONArray array, float mapHeight, Level level) {
        for (Object object : array) {
            // entity to get info
            Entity entity = new Entity((JSONObject) object, mapHeight);
            String name = gidToEntity.get(entity.gid);

            // init gigagal
            if (name.equals("StartPoint")) {
                level.setGigagal(new GigaGal(
                        level,
                        entity.x + Constants.GIGAGAL_EYE_POSITION.x,
                        entity.y + Constants.GIGAGAL_EYE_POSITION.y
                ));
            }

            // exit portal
            if (name.equals("ExitPortal")) {
                level.setExitPortal(new ExitPortal(
                        entity.x + Constants.EXIT_PORTAL_RADIUS,
                        entity.y + Constants.EXIT_PORTAL_RADIUS
                ));
            }
        }
    }
}