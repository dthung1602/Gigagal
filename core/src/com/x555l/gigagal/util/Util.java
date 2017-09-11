package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;


public class Util {
    private static Random random = new Random();

    public static float secondsSince(long startTime) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }

    public static void exitWithOutError() {
        // clean up
        Assets.instance.dispose();
        Configs.instance.save();

        // exit
        Gdx.app.exit();
        System.exit(0);
    }

    public static void exitWithError(String tag, Exception ex) {
        Gdx.app.log(tag, ex.getMessage());
        Gdx.app.exit();
        System.exit(1);
    }

    public static float randomFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
}
