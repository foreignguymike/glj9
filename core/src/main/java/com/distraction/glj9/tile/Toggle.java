package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Toggle extends Entity {

    private final TextureRegion[] toggles;
    public boolean on;

    public Toggle(Context context, boolean on) {
        super(context);
        toggles = context.getImage("toggle").split(16, 8)[0];
        this.on = on;
        w = 20;
        h = 16;
    }

    public void toggle() {
        on = !on;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, toggles[on ? 1 : 0], x, y);
    }
}
