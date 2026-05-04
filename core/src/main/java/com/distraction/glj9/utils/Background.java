package com.distraction.glj9.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Entity;

public class Background extends Entity {

    private static final int W = Constants.WIDTH;
    public static final int H = Constants.HEIGHT;

    private final TextureRegion image;
    private final float dx, dy, ox, oy;
    private final int numRows, numCols;

    public Background(Context context, TextureRegion image, float dx, float dy, float ox, float oy) {
        super(context);
        this.image = image;
        this.dx = dx;
        this.dy = dy;
        this.ox = ox;
        this.oy = oy;

        this.numRows = (int) (H / oy) + 1;
        this.numCols = (int) (W / ox) + 1;
    }

    @Override
    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
        x %= 2 * ox;
        y %= 2 * oy;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = -2; row < numRows; row++) {
            for (int col = -2; col < numCols; col++) {
                sb.draw(image, x + col * ox + ((row & 1) == 0 ? 0 : ox / 2), y + row * oy);
            }
        }
    }

}
