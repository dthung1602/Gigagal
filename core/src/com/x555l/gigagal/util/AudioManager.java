package com.x555l.gigagal.util;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;


public class AudioManager {

    // singleton
    public static final AudioManager instance = new AudioManager();

    // ensure singleton
    private AudioManager() {
    }

    private void play(Sound sound) {
        if (Configs.instance.isSoundEnabled())
            sound.play(Configs.instance.getSoundVolume(), 1, 0);
    }

    private void playRandomPitch(Sound sound) {
        if (Configs.instance.isSoundEnabled())
            sound.play(
                    Configs.instance.getSoundVolume(),
                    MathUtils.random(1, 1.1f), // make repeated sound less boring
                    0
            );
    }

    public void bonusBulletSound() {
        playRandomPitch(Assets.instance.audio.bonusBullet);
    }

    public void bonusLifeSound() {
        playRandomPitch(Assets.instance.audio.bonusLife);
    }

    public void bonusHealthSound() {
        playRandomPitch(Assets.instance.audio.bonusHealth);
    }

    public void shootSound() {
        play(Assets.instance.audio.bulletShoot);
    }

    public void hitEnemySound() {
        play(Assets.instance.audio.bulletHit);
    }

    public void explosionSound() {
        playRandomPitch(Assets.instance.audio.explosion);
    }

    public void jumpSound() {
        play(Assets.instance.audio.jump);
    }

    public void landOnPlatformSound() {
        play(Assets.instance.audio.land);
    }

    public void enemyLaserSound() {
        playRandomPitch(Assets.instance.audio.enemyLaser);
    }

    public void enemyPlasmaSound() {
        playRandomPitch(Assets.instance.audio.enemyPlasma);
    }
}
