package com.distraction.glj9;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.distraction.glj9.audio.AudioHandler;
import com.distraction.glj9.screens.Dialog;
import com.distraction.glj9.screens.ScreenManager;
import com.distraction.glj9.screens.SplashScreen;
import com.distraction.glj9.tile.LevelData;

public class Context {

    private static final String ATLAS = "glj9.atlas";
    private static final String FONT = "fonts/m5x7_16.fnt";
    private static final String PREFS = "pokopuzzle";
    private static final String KEY_COMPLETED = "completed";

    private static final int MAX_LEVELS = LevelData.levels.length;

    private static final long SECRET = 0x5A17BEEFL;
    private static final long LEVEL_MASK = (1L << MAX_LEVELS) - 1L;

    public final AssetManager assets;
    public final AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    public boolean pixelPerfect = false;

    public int speed = 1;
    private final boolean[] completed = new boolean[MAX_LEVELS];

    public int page;

    private final BitmapFont font;

    private final Preferences prefs;

    public Dialog dialog;

    private static final String vert =
        "attribute vec4 a_position;\n" +
        "attribute vec4 a_color;\n" +
        "attribute vec2 a_texCoord0;\n" +
        "uniform mat4 u_projTrans;\n" +
        "varying vec4 v_color;\n" +
        "varying vec2 v_texCoords;\n" +
        "\n" +
        "void main() {\n" +
        "    v_color = a_color;\n" +
        "    v_texCoords = a_texCoord0;\n" +
        "    gl_Position = u_projTrans * a_position;\n" +
        "}";
    private static final String frag =
        "#ifdef GL_ES\n" +
        "precision mediump float;\n" +
        "#endif\n" +
        "\n" +
        "varying vec4 v_color;\n" +
        "varying vec2 v_texCoords;\n" +
        "uniform sampler2D u_texture;\n" +
        "uniform vec4 u_silhouetteColor; // The solid color you want\n" +
        "\n" +
        "void main() {\n" +
        "    vec4 texColor = texture2D(u_texture, v_texCoords);\n" +
        "    gl_FragColor = vec4(u_silhouetteColor.rgb, texColor.a * u_silhouetteColor.a);\n" +
        "}";
    private final ShaderProgram fillShader;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(FONT, BitmapFont.class);
        assets.finishLoading();

        fillShader = new ShaderProgram(vert, frag);
        if (!fillShader.isCompiled()) {
            throw new RuntimeException(fillShader.getLog());
        }

        font = assets.get(FONT, BitmapFont.class);
        font.getData().markupEnabled = true;

        for (Texture t : assets.get(ATLAS, TextureAtlas.class).getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        prefs = Gdx.app.getPreferences(PREFS);
        load();

        audio = new AudioHandler();

        sb = new SpriteBatch();
        sm = new ScreenManager(new SplashScreen(this));
    }

    public void useFillShader(SpriteBatch sb, Color color) {
        sb.setShader(fillShader);
        fillShader.setUniformf("u_silhouetteColor", color);
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
        save();
    }

    private int checksum(long saved) {
        long x = saved ^ SECRET;
        x ^= (x >>> 17);
        x *= 0xed5ad4bbL;
        x ^= (x >>> 11);
        x *= 0xac4c1b51L;
        x ^= (x >>> 15);
        return (int)(x & 0x0FFFFFFF);
    }

    private void save() {
        long saved = 0L;
        for (int i = 0; i < completed.length; i++) {
            if (completed[i]) saved |= (1L << i);
        }
        int check = checksum(saved);
        long packed = saved | ((long)check << MAX_LEVELS);
        prefs.putLong(KEY_COMPLETED, packed);
        prefs.flush();
    }

    private void load() {
        long packed = prefs.getLong(KEY_COMPLETED, 0L);
        long saved = packed & LEVEL_MASK;
        int storedCheck = (int)(packed >>> MAX_LEVELS);
        if (storedCheck != checksum(saved)) {
            saved = 0L;
            save();
        }
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
