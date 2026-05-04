package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Player extends Entity {

    private final Animation<TextureRegion> animation;
    private final TextureRegion[] idleSprites;
    private final TextureRegion[] walkSprites;
    private boolean mirror;

    private boolean started = false;
    private float bouncy;

    protected Player(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 21;
        h = 20;
        idleSprites = context.getImage("playeridle").split(w, h)[0];
        walkSprites = context.getImage("playerwalk").split(w, h)[0];
        animation = new Animation<>(idleSprites, 0.5f);
    }

    @Override
    public void start() {
        super.start();
        animation.set(walkSprites, 0.2f);
    }

    @Override
    public void moveDirection(Direction direction) {
        super.moveDirection(direction);
        if (direction == Direction.RIGHT) mirror = false;
        else if (direction == Direction.LEFT) mirror = true;
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        if (!started) bouncy = 0f;
        else bouncy = Math.abs(MathUtils.sin(animation.getProgress() * MathUtils.PI) * 1);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get(), x, y + bouncy + 8, mirror);
    }
}
