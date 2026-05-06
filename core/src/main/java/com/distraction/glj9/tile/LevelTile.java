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

    private static final float INTERVAL = 0.05f;
    private static final float SPEED = 20;
    private static final int MAX_LEVELS = LevelData.levels.length;

    private final TextureRegion image;
    private final TextureRegion highlightImage;
    private final TextureRegion transitionImage;
    private final TextureRegion pellet;
    private final IntCallback callback;
    private final IntCallback hoverCallback;

    private int level;
    private int nextLevel;
    private boolean hovered;

    private final BitmapFont font;
    private final GlyphLayout text;

    private boolean visible = true;

    private float time;

    public LevelTile(Context context, int level, IntCallback callback, IntCallback hoverCallback) {
        super(context);
        this.callback = callback;
        this.hoverCallback = hoverCallback;
        image = context.getImage("leveltile");
        highlightImage = context.getImage("leveltileh");
        transitionImage = context.getImage("leveltilet");
        w = image.getRegionWidth();
        h = image.getRegionHeight();

        font = context.getFont();
        text = new GlyphLayout(font, level + "", Constants.WHITE, 0, Align.center, false);

        pellet = context.getImage("pellet");

        this.level = this.nextLevel = level;
        text.setText(font, level + "", Constants.WHITE, 0, Align.center, false);
    }

    public void setLevel(int level, float time) {
        this.time = time;
        this.nextLevel = level;
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
        if (hovered) callback.callback(nextLevel);
    }

    @Override
    public void update(float dt) {
        time -= dt;
        if (time < INTERVAL && level != nextLevel) {
            level = nextLevel;
            text.setText(font, level + "", Constants.WHITE, 0, Align.center, false);
        }
        float targety = time > 0 && time < INTERVAL ? -1 : 0;
        if (desty < targety) {
            desty += SPEED * dt;
            if (desty > targety) desty = targety;
        }
        if (desty > targety) {
            desty -= SPEED * dt;
            if (desty < targety) desty = targety;
        }
        visible = nextLevel < MAX_LEVELS;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!visible) return;
        sb.setColor(Color.WHITE);
        if (hovered) Utils.drawCentered(sb, highlightImage, x + 0.5f, y + desty);
        else if (desty != 0) Utils.drawCentered(sb, transitionImage, x + 0.5f, y + desty);
        else Utils.drawCentered(sb, image, x + 0.5f, y + desty);
        font.draw(sb, text, x, y + 4 + desty);
        if (level - 1 >= 0 && level - 1 < MAX_LEVELS && context.completedLevels[level - 1]) {
            Utils.drawCentered(sb, pellet, x + 7, y - 7 + desty);
        }
    }

}
