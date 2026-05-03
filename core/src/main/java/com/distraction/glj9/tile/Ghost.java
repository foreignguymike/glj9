package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.Utils;

public class Ghost extends Entity {

    private final Animation<TextureRegion> animation;
    private boolean mirror;

    private float bouncy;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 21;
        h = 20;
        animation = new Animation<>(context.getImage("ghost").split(w, h)[0], 0.2f);
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
        bouncy = Math.abs(MathUtils.sin(animation.getProgress() * MathUtils.PI) * 2);
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get(), x, y + bouncy + 8, mirror);
    }
}
