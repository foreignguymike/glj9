package com.distraction.glj9.tile;

import com.distraction.glj9.Context;

public class Collectible extends Entity {

    public Collectible(Context context, int row, int col) {
        super(context, row, col, Direction.UP);
    }

    @Override
    public void move(float percent) {

    }
}
