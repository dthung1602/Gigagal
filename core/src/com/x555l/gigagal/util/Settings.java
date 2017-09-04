package com.x555l.gigagal.util;


public class Settings {
    // singleton
    public static final Settings instance = new Settings();

    private float brightness;

    private boolean soundEnable;
    private float soundVolume;

    private boolean musicEnable;
    private float musicVolume;

    // ensure there's only one instance
    private Settings() {
    }

    //--------------------------------------
    //               GETTERS
    //--------------------------------------

    public float getBrightness() {
        return brightness;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public boolean isSoundEnabled() {
        return soundEnable;
    }

    public boolean isMusicEnabled() {
        return musicEnable;
    }

    //--------------------------------------
    //               SETTERS
    //--------------------------------------

    public void setBrightness(float value) {
        brightness = value;
    }

    public void setSoundVolume(float value) {
        soundVolume = value;
    }

    public void setMusicVolume(float value) {
        musicVolume = value;
    }

    public void setSoundEnabled(boolean value) {
        soundEnable = value;
    }

    public void setMusicEnabled(boolean value) {
        musicEnable = value;
    }
}
