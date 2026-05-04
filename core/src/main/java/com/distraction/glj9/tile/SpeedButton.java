package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.SimpleCallback;
import com.distraction.glj9.utils.Utils;

public class SpeedButton extends Button {

    private final TextureRegion caret;
    private int speed;

    public SpeedButton(Context context, TextureRegion[] images, int speed, SimpleCallback callback) {
        super(context, images, callback);
        this.speed = speed;
        caret = context.getImage("speedcaret");
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void onMousePressed(boolean pressed) {
        if (pressed) {
            if (hovered) {
                if (!this.pressed) {
                    speed++;
                    if (speed > 3) speed = 1;
                    callback.callback();
                }
                this.pressed = true;
            }
        } else {
            this.pressed = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        float w = (caret.getRegionWidth() + 1);
        float tw = w * speed;
        float xs = x - tw / 2 + w / 2;
        for (int i = 0; i < speed; i++) {
            Utils.drawCentered(sb, caret, xs + i * w, y + 1 + (pressed ? -2 : 0));
        }
    }
}
