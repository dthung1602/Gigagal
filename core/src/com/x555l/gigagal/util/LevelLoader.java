package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.Level;
import com.x555l.gigagal.entities.Enemy;
import com.x555l.gigagal.entities.ExitPortal;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.entities.Platform;
import com.x555l.gigagal.entities.Powerup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class LevelLoader {
    private static final String TAG = LevelLoader.class.getName();

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
                float width = (Long) jsonObject.get(Constants.LEVEL_WIDTH_KEY);
                float height = (Long) jsonObject.get(Constants.LEVEL_HEIGHT_KEY);
                float x = (Long) jsonObject.get(Constants.LEVEL_X_KEY);
                float y = mapHeight - (Long) jsonObject.get(Constants.LEVEL_Y_KEY) - height; // reverse y axis

                String type = (String) jsonObject.get(Constants.LEVEL_TYPE_KEY);

//                System.out.println(type + " " + x + " " + y + " " + width + " " + height);

                // create entity according to type
                if (type.equals(Constants.LEVEL_PLATFORM_TAG))
                    newPlatform(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_ENEMY_TAG))
                    newPlatformWithEnemy(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_POWERUP_TAG))
                    newPowerup(level, x, y);
                else if (type.equals(Constants.LEVEL_START_TAG))
                    newStartPlatform(level, x, y, width, height);
                else if (type.equals(Constants.LEVEL_END_TAG))
                    newExitPortal(level, x, y);
            }

        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }

    private static void newPlatform(Level level, float x, float y, float width, float height) {
        level.getPlatforms().add(new Platform(x, y, width, height));
    }

    private static void newPlatformWithEnemy(Level level, float x, float y, float width, float height) {
        Platform platform = new Platform(x, y, width, height);
        level.getPlatforms().add(platform);
        level.getEnemies().add(new Enemy(platform));
    }

    private static void newStartPlatform(Level level, float x, float y, float width, float height) {
        Platform platform = new Platform(x, y, width, height);
        level.getPlatforms().add(platform);
        level.setGigagal(new GigaGal(
                level,
                x + Constants.GIGAGAL_EYE_POSITION.x,
                y + Constants.GIGAGAL_EYE_POSITION.y * 2
        ));
    }

    private static void newPowerup(Level level, float x, float y) {
        level.getPowerups().add(new Powerup(x, y));
    }

    private static void newExitPortal(Level level, float x, float y) {
        level.setExitPortal(new ExitPortal(x, y));
    }
}
