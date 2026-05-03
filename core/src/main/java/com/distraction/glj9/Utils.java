package com.distraction.glj9;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, boolean flipped) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float nx = flipped ? x + w / 2 : x - w / 2;
        float nw = flipped ? -w : w;
        sb.draw(image, nx, y - h / 2, nw, h);
    }

}
