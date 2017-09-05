package com.x555l.gigagal.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.x555l.gigagal.entities.ExitPortal;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.bonus.BonusBullet;
import com.x555l.gigagal.entities.bonus.BonusHealth;
import com.x555l.gigagal.entities.bonus.BonusLife;
import com.x555l.gigagal.entities.enemies.BasicEnemy;
import com.x555l.gigagal.util.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Create a level using data from a json file
 */

public class LevelLoader {
    private static final String TAG = LevelLoader.class.getName();

    /**
     * Create a level using data from a json file
     *
     * @param levelNum: integer specifies which number to load
     * @return newly created level
     */
    public static Level load(int levelNum) {
        // path to json file
        String path = "levels/level" + levelNum + ".json";

        Level level = new Level(levelNum);

        try {
            // parse json file
            FileHandle file = Gdx.files.internal(path);
            JSONParser parser = new JSONParser();
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());

            // get map height to reverse y axis
            float mapHeight = (Long) rootJsonObject.get(Constants.LEVEL_HEIGHT_KEY) // number of vertical tile
                    * (Long) rootJsonObject.get(Constants.LEVEL_TILE_HEIGHT_KEY);   // height of 1 tile in px

            // get object array
            JSONObject platformLayer = (JSONObject) ((JSONArray) rootJsonObject.get(Constants.LEVEL_LAYER_KEY)).get(1);
            JSONArray objectArray = (JSONArray) platformLayer.get(Constants.LEVEL_OBJECT_KEY);

            for (Object object : objectArray) {
                // get json object properties
                JSONObject jsonObject = (JSONObject) object;
                float width = getNumber(jsonObject, Constants.LEVEL_WIDTH_KEY);
                float height = getNumber(jsonObject, Constants.LEVEL_HEIGHT_KEY);
                float x = getNumber(jsonObject, Constants.LEVEL_X_KEY);
                float y = mapHeight - getNumber(jsonObject, Constants.LEVEL_Y_KEY) - height; // reverse y axis

                String type = (String) jsonObject.get(Constants.LEVEL_TYPE_KEY);

                // create entity according to type
                if (type.equals(Constants.LEVEL_PLATFORM_TAG))
                    newPlatform(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_ENEMY_TAG))
                    newPlatformWithEnemy(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_BONUS_HEALTH_TAG))
                    newBonusHealth(level, x, y);
                else if (type.equals(Constants.LEVEL_BONUS_BULLET_TAG))
                    newBonusBullet(level, x, y);
                else if (type.equals(Constants.LEVEL_BONUS_LIFE_TAG))
                    newBonusLife(level, x, y);
                else if (type.equals(Constants.LEVEL_START_TAG))
                    newStartPlatform(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_END_TAG))
                    newExitPortal(level, x, y);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }

    private static float getNumber(JSONObject jsonObject, String propertyName) {
        Number number = (Number) jsonObject.get(propertyName);
        return number.floatValue();
    }

    private static void newPlatform(Level level, float x, float y, float width, float height) {
        level.getPlatforms().add(new Platform(true, x, y, width, height));
    }

    private static void newPlatformWithEnemy(Level level, float x, float y, float width, float height) {
        Platform platform = new Platform(false, x, y, width, height);
        level.getPlatforms().add(platform);
        level.getEnemies().add(new BasicEnemy(platform));
    }

    private static void newStartPlatform(Level level, float x, float y, float width, float height) {
        Platform platform = new Platform(true, x, y, width, height);
        level.getPlatforms().add(platform);
        level.setGigagal(new GigaGal(
                level,
                x + Constants.GIGAGAL_EYE_POSITION.x,
                y + height + Constants.GIGAGAL_EYE_POSITION.y * 2
        ));
    }

    private static void newBonusHealth(Level level, float x, float y) {
        level.getBonuses().add(new BonusHealth(x, y));
    }

    private static void newBonusLife(Level level, float x, float y) {
        level.getBonuses().add(new BonusLife(x, y));
    }

    private static void newBonusBullet(Level level, float x, float y) {
        level.getBonuses().add(new BonusBullet(x, y));
    }

    private static void newExitPortal(Level level, float x, float y) {
        level.setExitPortal(new ExitPortal(x, y));
    }
}
