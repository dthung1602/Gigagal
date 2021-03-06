package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * A singleton class that contains:
 * - player's progress: max unlocked level
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
        loadingError = false;
    }

    // root json object
    private JSONObject rootObject;

    // error flag
    private boolean loadingError;

    /**
     * Load config data from json file
     */
    public void load() {
        try {
            // open file
            FileHandle configFile = Gdx.files.local(Constants.Config.CONFIG_FILE);

            // check if file exists
            if (!configFile.exists())
                newConfigFile();

            // parse file
            JSONParser parser = new JSONParser();
            rootObject = (JSONObject) parser.parse(configFile.reader());

        } catch (Exception ex) {
            // if error happens again, exit with error
            if (loadingError)
                Util.exitWithError(TAG, ex);

            // if any error occurs for the first time, try to reset configs
            loadingError = true;
            Gdx.app.log(TAG, "Error loading config file. Try to reset config to default");
            newConfigFile();

            // reload
            load();
        }
    }

    /**
     * Save config data to json file
     */
    public void save() {
        try {
            // open file
            FileHandle configFile = Gdx.files.local(Constants.Config.CONFIG_FILE);

            // write
            configFile.writeString(rootObject.toJSONString(), false);

        } catch (Exception ex) {
            Util.exitWithError(TAG, ex);
        }
    }

    /**
     * Create/set config file to factory-state
     * by overwritten config.json by default json file
     */
    private void newConfigFile() {
        try {
            FileHandle source = Gdx.files.internal(Constants.Config.CONFIG_FILE_DEFAULT);
            FileHandle destination = Gdx.files.local(Constants.Config.CONFIG_FILE);
            source.copyTo(destination);
        } catch (Exception ex) {
            Util.exitWithError(TAG, ex);
        }
    }


    //--------------------------------------
    //               GETTERS
    //--------------------------------------

    public int getCurrentLevel() {
        return ((Number) rootObject.get(Constants.Config.CURRENT_LEVEL)).intValue();
    }

    public float getBrightness() {
        return ((Number) rootObject.get(Constants.Config.BRIGHTNESS)).floatValue();
    }

    public float getSoundVolume() {
        return ((Number) rootObject.get(Constants.Config.SOUND_VOLUME)).floatValue();
    }

    public float getMusicVolume() {
        return ((Number) rootObject.get(Constants.Config.MUSIC_VOLUME)).floatValue();
    }

    public boolean isSoundEnabled() {
        return (Boolean) rootObject.get(Constants.Config.SOUND_ENABLE);
    }

    public boolean isMusicEnabled() {
        return (Boolean) rootObject.get(Constants.Config.MUSIC_ENABLE);
    }

    public boolean isDebugScreenLayoutEnabled() {
        return (Boolean) rootObject.get(Constants.Config.DEBUG_SCREEN_LAYOUT);
    }

    public boolean isDebugFPSEnabled() {
        return (Boolean) rootObject.get(Constants.Config.DEBUG_FPS);
    }

    public boolean isDebugOnScreenControlEnabled() {
        return (Boolean) rootObject.get(Constants.Config.DEBUG_ONSCREEN_CONTROL);
    }

    //--------------------------------------
    //               SETTERS
    //--------------------------------------

    public void setCurrentLevel(int value) {
        rootObject.put(Constants.Config.CURRENT_LEVEL, value);
    }

    public void setBrightness(float value) {
        rootObject.put(Constants.Config.BRIGHTNESS, value);
    }

    public void setSoundVolume(float value) {
        rootObject.put(Constants.Config.SOUND_VOLUME, value);
        AudioManager.instance.updateBackgroundMusic();
    }

    public void setMusicVolume(float value) {
        rootObject.put(Constants.Config.MUSIC_VOLUME, value);
        AudioManager.instance.updateBackgroundMusic();
    }

    public void setSoundEnabled(boolean value) {
        rootObject.put(Constants.Config.SOUND_ENABLE, value);
        AudioManager.instance.updateBackgroundMusic();
    }

    public void setMusicEnabled(boolean value) {
        rootObject.put(Constants.Config.MUSIC_ENABLE, value);
        AudioManager.instance.updateBackgroundMusic();
    }

    public void setDebugScreenLayoutEnabled(boolean value) {
        rootObject.put(Constants.Config.DEBUG_SCREEN_LAYOUT, value);
    }

    public void setDebugFPSEnabled(boolean value) {
        rootObject.put(Constants.Config.DEBUG_FPS, value);
    }

    public void setDebugOnScreenControl(boolean value) {
        rootObject.put(Constants.Config.DEBUG_ONSCREEN_CONTROL, value);
    }
}
