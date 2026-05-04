package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.SimpleCallback;
import com.distraction.glj9.utils.Utils;

public class Button extends Entity {

    private final TextureRegion[] images;
    private final SimpleCallback callback;

    private TextureRegion image;
    private boolean hovered;
    private boolean pressed;

    public Button(Context context, TextureRegion[] images, SimpleCallback callback) {
        super(context);
        this.images = images;
        this.callback = callback;
        w = images[0].getRegionWidth();
        h = images[0].getRegionHeight();

        image = images[0];
    }

    public void onMouseMoved(float mx, float my) {
        hovered = contains(mx, my);
        if (hovered) image = images[1];
        else image = images[0];
    }

    public void onMousePressed(boolean pressed) {
        if (pressed) {
            if (hovered) {
                if (!this.pressed) callback.callback();
                this.pressed = true;
                image = images[2];
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y);
    }
}
