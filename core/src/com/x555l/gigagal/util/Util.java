package com.x555l.gigagal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;


public class Util {
    public static float secondsSince(long startTime) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }

    public static void exit(int exitCode) {
        // clean up
        Assets.instance.dispose();
        Configs.instance.save();

        // exit
        Gdx.app.exit();
        System.exit(exitCode);
    }
}
