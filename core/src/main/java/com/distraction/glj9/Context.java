package com.distraction.glj9;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.screens.ScreenManager;
import com.distraction.glj9.tile.LevelData;

public class Context {

    private static final String ATLAS = "glj9.atlas";
    private static final String FONT = "fonts/m5x7_16.fnt";

    public AssetManager assets;

    public ScreenManager sm;
    public SpriteBatch sb;

    public boolean pixelPerfect = false;

    public int speed = 1;
    public final boolean[] completedLevels = new boolean[LevelData.levels.length];

    public int page;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT, BitmapFont.class);
        assets.finishLoading();

        for (Texture t : assets.get(ATLAS, TextureAtlas.class).getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        sb = new SpriteBatch();
        sm = new ScreenManager(new com.distraction.glj9.screens.LevelSelectScreen(this));
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
    }

    public BitmapFont getFont() {
        return assets.get(FONT, BitmapFont.class);
    }

    public void dispose() {
        sb.dispose();
    }

}
