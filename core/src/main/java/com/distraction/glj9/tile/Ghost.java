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

    private float time;
    private float bouncy;

    protected Ghost(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        animation = new Animation<>(context.getImage("ghostidle").split(w, h)[0], 0.2f);
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
        super.render(sb);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, animation.get(), x, y + bouncy + 4);
    }
}
