package com.distraction.glj9.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Entity;
import com.distraction.glj9.tile.NinePatch;

public class Dialog extends Entity {

    private static final Interpolation SWING_OUT = new Interpolation.SwingOut(3f);
    private static final Interpolation CLOSE = Interpolation.fastSlow;
    private static final float POP_TIME = 0.2f;
    private static final float CHAR_TIME = 1 / 30f;
    private static final float BLIP_TIME = 1 / 12f;

    private final String[] texts;
    private int textIndex = -1;
    private int charIndex = 0;
    private float time;
    private String text;

    private final BitmapFont font;
    private final GlyphLayout layout;

    public boolean lock;

    private float popTime;
    private float blipTime;

    private final NinePatch ninePatch;

    public Dialog(Context context, String[] texts, float x, float y, int w, int h) {
        super(context);
        this.texts = texts;
        ninePatch = new NinePatch(
            context,
            context.getImage("dialogcorner"),
            context.getImage("dialogside"),
            context.getPixel(),
            w + 10, h
        );

        this.w = w;
        this.h = h;

        font = context.getFont();
        layout = new GlyphLayout();

        this.x = x;
        this.y = y;
        ninePatch.x = x;
        ninePatch.y = y;
        ninePatch.fillColor = Constants.DIALOG_BG;
    }

    public void next() {
        if (lock && textIndex == texts.length - 1) return;
        if (textIndex == texts.length) return;
        if (!isCurrentTextDone()) return;

        textIndex++;
        if (textIndex < texts.length) {
            text = "";
            time = CHAR_TIME;
            blipTime = 0;
            charIndex = 0;
        }
    }

    private boolean isCurrentTextDone() {
        if (textIndex < 0) return true;
        if (textIndex < texts.length) return charIndex == texts[textIndex].length();
        return true;
    }

    public boolean isTextDone() {
        int lastTextIndex = texts.length - 1;
        int lastCharIndex = texts[lastTextIndex].length() - 1;
        return textIndex == lastTextIndex && charIndex == lastCharIndex + 1;
    }

    public boolean isDone() {
        return textIndex >= texts.length && popTime <= 0;
    }

    private void updateText() {
        layout.setText(
            font,
            text,
            Constants.DIALOG_TEXT,
            w,
            Align.center,
            true
        );
    }

    private void typeNextChar() {
//        text += texts[textIndex].charAt(charIndex);
//        updateText();
//        charIndex++;

        String full = texts[textIndex];
        if (charIndex >= full.length()) return;
        char c = full.charAt(charIndex);
        if (c == '[') {
            int end = full.indexOf(']', charIndex);
            if (end != -1) {
                text += full.substring(charIndex, end + 1);
                charIndex = end + 1;
                typeNextChar();
                return;
            }
        }
        text += c;
        charIndex++;
        updateText();
    }

    @Override
    public void update(float dt) {
        if (textIndex >= 0 && textIndex < texts.length) {
            if (popTime < POP_TIME) {
                popTime += dt;
                if (popTime > POP_TIME) {
                    popTime = POP_TIME;
                }
            } else if (time > 0) {
                time -= dt;
                if (time <= 0) {
                    typeNextChar();
                    if (charIndex < texts[textIndex].length()) {
                        time = CHAR_TIME;
                    }
                }
                blipTime -= dt;
                if (blipTime < 0) {
                    blipTime = BLIP_TIME;
//                    context.audio.playSound("dialog", 0.25f, MathUtils.random(0.92f, 1.08f));
                }
            }
        } else if (textIndex == texts.length) {
            if (popTime > 0) {
                popTime -= dt;
                if (popTime < 0) {
                    popTime = 0;
                }
            }
        }
        if (textIndex >= 0) ninePatch.scale = SWING_OUT.apply(popTime / POP_TIME);
        else ninePatch.scale = CLOSE.apply(popTime / POP_TIME);
        ninePatch.x = x;
        ninePatch.y = y;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (popTime > 0) ninePatch.render(sb);
        if (textIndex >= 0 && textIndex < texts.length) font.draw(sb, layout, x - w / 2f, y + layout.height / 2f);
    }
}
