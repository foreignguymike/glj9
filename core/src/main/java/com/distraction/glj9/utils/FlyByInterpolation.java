package com.distraction.glj9.utils;

import com.badlogic.gdx.math.Interpolation;

public class FlyByInterpolation extends Interpolation {
    @Override
    public float apply(float a) {
        if (a <= 0.5f) {
            a = 1f - a * 2f;
            a *= a * a;
            return 1f - a;
        } else {
            a = (a - 0.5f) * 2f;
            a *= a * a;
            return 1f + a;
        }
    }
}
