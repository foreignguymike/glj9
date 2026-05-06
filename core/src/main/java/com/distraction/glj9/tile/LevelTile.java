package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.IntCallback;
import com.distraction.glj9.utils.Utils;

public class LevelTile extends Entity {

    private final TextureRegion image;
    private final TextureRegion highlightImage;
    private final TextureRegion pellet;
    private final IntCallback callback;
    private final IntCallback hoverCallback;

    private int level;
    private boolean hovered;

    private final BitmapFont font;
    private final GlyphLayout text;

    private boolean visible = true;

    public LevelTile(Context context, int level, TextureRegion image, TextureRegion highlightImage, IntCallback callback, IntCallback hoverCallback) {
        super(context);
        this.image = image;
        this.highlightImage = highlightImage;
        this.callback = callback;
        this.hoverCallback = hoverCallback;
        w = image.getRegionWidth();
        h = image.getRegionHeight();

        font = context.getFont();
        text = new GlyphLayout(font, level + "", Constants.WHITE, 0, Align.center, false);

        pellet = context.getImage("pellet");

        setLevel(level);
    }

    public void setLevel(int level) {
        if (this.level == level) return;
        this.level = level;
        text.setText(font, level + "", Constants.WHITE, 0, Align.center, false);
    }

    public void onMouseMoved(float mx, float my) {
        boolean previous = hovered;
        hovered = contains(mx, my);
        if (hovered != previous) {
            hoverCallback.callback(hovered ? level : 0);
        }
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
        if (context.completedLevels[level - 1]) Utils.drawCentered(sb, pellet, x + 7, y - 7);
    }

}
