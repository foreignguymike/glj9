package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.SimpleCallback;
import com.distraction.glj9.utils.Utils;

public class LevelTile extends Entity {

    public interface LevelCallback {
        void callback(int level);
    }

    private final TextureRegion image;
    private final TextureRegion highlightImage;
    private final LevelCallback callback;

    private int level;
    private boolean hovered;

    private final BitmapFont font;
    private final GlyphLayout text;

    private boolean visible = true;

    public LevelTile(Context context, int level, TextureRegion image, TextureRegion highlightImage, LevelCallback callback) {
        super(context);
        this.image = image;
        this.highlightImage = highlightImage;
        this.callback = callback;
        w = image.getRegionWidth();
        h = image.getRegionHeight();

        font = context.getFont();
        text = new GlyphLayout(font, level + "", Constants.WHITE, 0, Align.center, false);

        setLevel(level);
    }

    public void setLevel(int level) {
        if (this.level == level) return;
        this.level = level;
        text.setText(font, level + "", Constants.WHITE, 0, Align.center, false);
    }

    public void onMouseMoved(float mx, float my) {
        hovered = contains(mx, my);
    }

    public void onMousePressed() {
        if (!visible) return;
        if (hovered) callback.callback(level);
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!visible) return;
        sb.setColor(Color.WHITE);
        if (hovered) Utils.drawCentered(sb, highlightImage, x + 0.5f, y);
        else Utils.drawCentered(sb, image, x + 0.5f, y);
        font.draw(sb, text, x, y + 4);
    }

}
