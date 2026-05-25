package com.distraction.glj9.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCenteredOutline(Context context, SpriteBatch sb, TextureRegion image, Color c, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        context.useFillShader(sb, c);
        sb.draw(image, x - w / 2 - 1, y - h / 2, w, h);
        sb.draw(image, x - w / 2 + 1, y - h / 2, w, h);
        sb.draw(image, x - w / 2, y - h / 2 - 1, w, h);
        sb.draw(image, x - w / 2, y - h / 2 + 1, w, h);
        sb.setShader(null);
        sb.setColor(Color.WHITE);
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h) {
        sb.draw(image, x - w / 2, y - h / 2, w, h);
    }

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y, boolean flipped) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        float nx = flipped ? x + w / 2 : x - w / 2;
        float nw = flipped ? -w : w;
        sb.draw(image, nx, y - h / 2, nw, h);
    }

    public static void drawCenteredRotated(SpriteBatch sb, TextureRegion image, float x, float y, float deg) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();

        sb.draw(
            image,
            x - w / 2, y - h / 2,
            w / 2, h / 2,
            w, h,
            1, 1,
            deg
        );
    }

    public static void drawCenteredRotated(SpriteBatch sb, TextureRegion image, float x, float y, float w, float h, float deg) {
        sb.draw(
            image,
            x - w / 2, y - h / 2,
            w / 2, h / 2,
            w, h,
            1, 1,
            deg
        );
    }


    public static int[][] flip(int[][] tiles) {
        int[][] ret = new int[tiles.length][tiles[0].length];
        for (int row = 0; row < tiles.length; row++) {
            ret[tiles.length - row - 1] = tiles[row];
        }
        return ret;
    }

    public static TextureRegion[] flat(TextureRegion[][] tileset) {
        TextureRegion[] ret = new TextureRegion[tileset.length * tileset[0].length];
        int cols = tileset.length;
        for (int row = 0; row < tileset.length; row++) {
            for (int col = 0; col < tileset[0].length; col++) {
                ret[row * cols + col] = tileset[row][col];
            }
        }
        return ret;
    }

    public static Color getBgColor(int level) {
        if (Utils.isSecretLevel(level)) return Constants.SECRET_BG;
        else if (level <= 12) return Constants.LEVEL_BG;
        else if (level <= 24) return Constants.LEVEL_BG_2;
        else return Constants.LEVEL_BG_3;
    }

    public static Color getBgImageColor(int level) {
        if (Utils.isSecretLevel(level)) return Constants.SECRET_BG_IMAGE;
        else if (level <= 12) return Constants.LEVEL_BG_IMAGE;
        else if (level <= 24) return Constants.LEVEL_BG_IMAGE_2;
        else return Constants.LEVEL_BG_IMAGE_3;
    }

    public static String getBgImage(int level) {
        int hintIndex = getHintIndex(level);
        if (hintIndex != -1) {
            return Constants.BG_SEQUENCE[hintIndex];
        } else {
            if (Utils.isSecretLevel(level)) return "bgsecret";
            else if (level <= 12) return "bgeasy";
            else if (level <= 24) return "bghard";
            else return "bgtricky";
        }
    }

    public static boolean isHintLevel(int level) {
        return getHintIndex(level) != -1;
    }

    private static int getHintIndex(int level) {
        for (int i = 0; i < Constants.LEVEL_SEQUENCE.length; i++) {
            if (level == Constants.LEVEL_SEQUENCE[i]) return i;
        }
        return -1;
    }

    public static String getMusicKey(int level) {
        if (Utils.isSecretLevel(level)) return "secret";
        else if (level <= 12) return "easy";
        else if (level <= 24) return "hard";
        else return "tricky";
    }

    public static boolean isSecretLevel(int level) {
        return level > 36;
    }

}
