package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * A singleton class that contains:
 * - player's progress: max unlock level
 * - settings: brightness, fx sound, music
 * Also includes methods for loading and saving config json file
 */

public class Configs {
    // tag for debugging
    private static final String TAG = Configs.class.getName();

    // singleton
    public static final Configs instance = new Configs();

    // ensure there's only one instance
    private Configs() {
    }

    // root json object
    JSONObject rootObject;

    /**
     * Load config data from json file
     */
    public void load() {
        try {
            // open file
            FileHandle configFile = Gdx.files.local(Constants.CONFIG_FILE);

            // check if file exists
            if (!configFile.exists())
                newConfigFile();

            // parse file
            JSONParser parser = new JSONParser();
            rootObject = (JSONObject) parser.parse(configFile.reader());

        } catch (Exception ex) {
            logError(ex);
        }
    }

    /**
     * Save config data to json file
     */
    public void save() {
        try {
            // open file
            FileHandle configFile = Gdx.files.local(Constants.CONFIG_FILE);

            // write
            configFile.writeString(rootObject.toJSONString(), false);

        } catch (Exception ex) {
            logError(ex);
        }
    }

    /**
     * Create/set config file to factory-state
     * by overwritten config.json by default json file
     */
    public void newConfigFile() {
        try {
            FileHandle source = Gdx.files.internal(Constants.CONFIG_FILE_DEFAULT);
            FileHandle destination = Gdx.files.local(Constants.CONFIG_FILE);
            source.copyTo(destination);
        } catch (Exception ex) {
            logError(ex);
        }
    }

    /**
     * Log errors
     */
    private void logError(Exception ex) {
        ex.printStackTrace();
        Gdx.app.error(TAG, ex.getMessage());
        Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
    }

    //--------------------------------------
    //               GETTERS
    //--------------------------------------

    public int getCurrentLevel() {
        return ((Number) rootObject.get(Constants.CONFIG_CURRENT_LEVEL)).intValue();
    }

    public float getBrightness() {
        return ((Number) rootObject.get(Constants.CONFIG_BRIGHTNESS)).floatValue();
    }

    public float getSoundVolume() {
        return ((Number) rootObject.get(Constants.CONFIG_SOUND_VOLUME)).floatValue();
    }

    public float getMusicVolume() {
        return ((Number) rootObject.get(Constants.CONFIG_MUSIC_VOLUME)).floatValue();
    }

    public boolean isSoundEnabled() {
        return (Boolean) rootObject.get(Constants.CONFIG_SOUND_ENABLE);
    }

    public boolean isMusicEnabled() {
        return (Boolean) rootObject.get(Constants.CONFIG_MUSIC_ENABLE);
    }

    //--------------------------------------
    //               SETTERS
    //--------------------------------------

    public void setCurrentLevel(int value) {
        rootObject.put(Constants.CONFIG_CURRENT_LEVEL, value);
    }

    public void setBrightness(float value) {
        rootObject.put(Constants.CONFIG_BRIGHTNESS, value);
    }

    public void setSoundVolume(float value) {
        rootObject.put(Constants.CONFIG_SOUND_VOLUME, value);
    }

    public void setMusicVolume(float value) {
        rootObject.put(Constants.CONFIG_MUSIC_VOLUME, value);
    }

    public void setSoundEnabled(boolean value) {
        rootObject.put(Constants.CONFIG_SOUND_ENABLE, value);
    }

    public void setMusicEnabled(boolean value) {
        rootObject.put(Constants.CONFIG_MUSIC_ENABLE, value);
    }
}
