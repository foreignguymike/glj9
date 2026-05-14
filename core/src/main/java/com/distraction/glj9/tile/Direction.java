package com.distraction.glj9.tile;

public enum Direction {
    UP(90),
    LEFT(180),
    DOWN(-90),
    RIGHT(0);

    public final float deg;
    Direction(float deg) {
        this.deg = deg;
    }
}
