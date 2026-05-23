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
    private final TextureRegion[][] sheets;
    private int currentSheet;

    private float time;
    private float bouncy;

    private int speed;
    private boolean sad;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        sheets = new TextureRegion[][] {
            context.getImage("ghostup").split(w, h)[0],
            context.getImage("ghostleft").split(w, h)[0],
            context.getImage("ghostdown").split(w, h)[0],
            context.getImage("ghostright").split(w, h)[0],
            context.getImage("ghostsadup").split(w, h)[0],
            context.getImage("ghostsadleft").split(w, h)[0],
            context.getImage("ghostsaddown").split(w, h)[0],
            context.getImage("ghostsadright").split(w, h)[0],
        };
        currentSheet = direction.ordinal();
        animation = new Animation<>(sheets[currentSheet], 0.1f);
        speed = context.speed;
    }

    @Override
    public void moveDirection(Direction direction) {
        super.moveDirection(direction);
        currentSheet = direction.ordinal() + (sad ? 4 : 0);
        animation.set(sheets[currentSheet], INTERVAL[speed - 1]);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        animation.setInterval(INTERVAL[speed - 1]);
    }

    public void setSad(boolean sad) {
        if (this.sad != sad) {
            this.sad = sad;
            if (sad) {
                currentSheet += 4;
                animation.set(sheets[currentSheet], INTERVAL[speed - 1]);
            } else {
                currentSheet -= 4;
                animation.set(sheets[currentSheet], INTERVAL[speed - 1]);
            }
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
