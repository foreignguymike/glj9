package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.SimpleCallback;
import com.distraction.glj9.utils.Utils;

public class Button extends Entity {

    private final TextureRegion[] images;
    protected final SimpleCallback callback;

    protected boolean hovered;
    public boolean pressed;

    public Button(Context context, TextureRegion[] images, SimpleCallback callback) {
        super(context);
        this.images = images;
        this.callback = callback;
        w = images[0].getRegionWidth();
        h = images[0].getRegionHeight();
    }

    public void onMouseMoved(float mx, float my) {
        hovered = contains(mx, my);
    }

    public void onMousePressed(boolean pressed) {
        if (pressed) {
            if (hovered) {
                if (!this.pressed) callback.callback();
                this.pressed = true;
            }
        } else {
            this.pressed = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (pressed) Utils.drawCentered(sb, images[2], x, y);
        else if (hovered) Utils.drawCentered(sb, images[1], x, y);
        else Utils.drawCentered(sb, images[0], x, y);
    }
}
