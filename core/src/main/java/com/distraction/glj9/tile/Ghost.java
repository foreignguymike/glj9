package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Ghost extends Entity {

    private final Animation<TextureRegion> animation;
    private boolean mirror;

    private float time;
    private float bouncy;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        animation = new Animation<>(context.getImage("ghostidle").split(w, h)[0], 0.2f);
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
        time += dt;
        time %= MathUtils.PI2;
        bouncy = MathUtils.sin(time * MathUtils.PI) * 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get(), x, y + bouncy + 4, mirror);
    }
}
