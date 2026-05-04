package com.distraction.glj9.utils;

import com.badlogic.gdx.math.Interpolation;

public class LauchInterpolation extends Interpolation {

    private final float strength;

    public LauchInterpolation(float strength) {
        this.strength = strength;
    }

    @Override
    public float apply(float a) {
        return (strength * a + 0.5f * -strength * 4f * a * a);
    }
}
