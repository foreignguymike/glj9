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

    private static final float[] INTERVAL = new float[] { 0.16f, 0.08f, 0.04f };

    private final Animation<TextureRegion> animation;
    private final TextureRegion[] ghost;
    private final TextureRegion[] sadGhost;

    private float time;
    private float bouncy;

    private int speed;
    private boolean sad;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        ghost = context.getImage("ghost").split(w, h)[0];
        sadGhost = context.getImage("ghostsad").split(w, h)[0];
        animation = new Animation<>(ghost, 0.1f);
        speed = context.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        animation.setInterval(INTERVAL[speed - 1]);
    }

    public void setSad(boolean sad) {
        if (this.sad != sad) {
            this.sad = sad;
            if (sad) animation.set(sadGhost, INTERVAL[speed - 1]);
            else animation.set(ghost, INTERVAL[speed - 1]);
        }
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
