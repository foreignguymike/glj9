package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Ghost extends Entity {

    private static final float[] IDLE_INTERVAL = new float[] { 0.4f, 0.2f, 0.1f };

    private final Animation<TextureRegion> animation;

    private float time;
    private float bouncy;

    private int speed;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        animation = new Animation<>(context.getImage("ghostidle").split(w, h)[0], 0.1f);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        animation.setInterval(IDLE_INTERVAL[speed - 1]);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        time += dt;
        if (time > MathUtils.PI2) time -= MathUtils.PI2;
        bouncy = MathUtils.sin(time * 2) * 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (transparent) sb.setColor(Constants.TRANSPARENT);
        else sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get(), x, y + bouncy + 4);
    }
}
