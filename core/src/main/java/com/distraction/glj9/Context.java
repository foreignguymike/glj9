package com.distraction.glj9;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.audio.AudioHandler;
import com.distraction.glj9.screens.ScreenManager;
import com.distraction.glj9.tile.LevelData;

public class Context {

    private static final String ATLAS = "glj9.atlas";
    private static final String FONT = "fonts/m5x7_16.fnt";
    private static final String PREFS = "pokopuzzle";
    private static final String KEY_COMPLETED = "completed";

    public final AssetManager assets;
    public final AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    public boolean pixelPerfect = false;

    public int speed = 1;
    private final boolean[] completed = new boolean[LevelData.levels.length];

    public int page;

    private final BitmapFont font;

    private final Preferences prefs;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT, BitmapFont.class);
        assets.finishLoading();

        font = assets.get(FONT, BitmapFont.class);
        font.getData().markupEnabled = true;

        for (Texture t : assets.get(ATLAS, TextureAtlas.class).getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        prefs = Gdx.app.getPreferences(PREFS);
        load();

        audio = new AudioHandler();

        sb = new SpriteBatch();
//        sm = new ScreenManager(new com.distraction.glj9.screens.LevelSelectScreen(this));
        sm = new ScreenManager(new com.distraction.glj9.screens.TitleScreen(this));
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
        return font;
    }

    public boolean isComplete(int index) {
        return completed[index];
    }

    public void setComplete(int index) {
        completed[index] = true;
        long saved = 0L;
        for (int i = 0; i < completed.length; i++) {
            if (completed[i]) saved |= (1L << i);
        }
        prefs.putLong(KEY_COMPLETED, saved);
        prefs.flush();
    }

    private void load() {
        long saved = prefs.getLong(KEY_COMPLETED);
        for (int i = 0; i < completed.length; i++) {
            completed[i] = (saved & (1L << i)) != 0;
        }
    }

    public void dispose() {
        sb.dispose();
        audio.dispose();
        assets.dispose();
    }

}
