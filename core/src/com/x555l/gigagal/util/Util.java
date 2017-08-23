package com.x555l.gigagal.util;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Util {
    public static float seccondsSince(long startTime) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }
}
