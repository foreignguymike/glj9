package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Pointer extends Entity {

    private static final Interpolation INTERPOLATION = new Interpolation.SwingOut(2f);
    private static final float STARTX = -40;
    private static final float DURATION = 0.5f;

    private final TextureRegion image;

    private boolean showing;

    private float time = 0;
    private float sin;

    public Pointer(Context context) {
        super(context);
        image = context.getImage("pointer");
    }

    public void hide() {
        showing = false;
        destx = STARTX;
    }

    public void show(float destx, float desty) {
        showing = true;
        this.x = STARTX;
        this.destx = destx;
        this.y = desty;
        time = 0;
    }

    @Override
    public void update(float dt) {
        if (showing) time += dt;

        time = MathUtils.clamp(time, 0, DURATION);
        x = STARTX + (destx - STARTX) * INTERPOLATION.apply(time / DURATION);

        sin += 8 * dt;
        if (sin > MathUtils.PI2) sin -= MathUtils.PI2;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (showing) Utils.drawCentered(sb, image, x + (MathUtils.sin(sin) + 1) * 0.5f, y);
    }
}
