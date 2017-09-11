package com.x555l.gigagal.util;

/**
 * Provides enums for moving states of objects
 */

public class Enum {
    public enum Facing {
        LEFT,
        RIGHT,
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        KNOCK_BACK,
    }

    public enum WalkState {
        STANDING,
        FACE_UP,
        WALKING,
    }
}
