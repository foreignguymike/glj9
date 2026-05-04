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
    private final TextureRegion[][][] sheets;
    private final TextureRegion[] idleSprites;

    private float bouncy;

    protected Player(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        sheets = new TextureRegion[][][] {
            context.getImage("playerup").split(w, h),
            context.getImage("playerleft").split(w, h),
            context.getImage("playerdown").split(w, h),
            context.getImage("playerright").split(w, h),
        };
        idleSprites = new TextureRegion[]{sheets[direction.ordinal()][0][0], sheets[direction.ordinal()][0][1]};
        animation = new Animation<>(idleSprites, 0.4f);
    }

    public void redo() {
        animation.set(idleSprites, 0.4f);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        animation.set(sheets[direction.ordinal()][1], 0.05f);
    }

    @Override
    public void moveDirection(Direction direction) {
        super.moveDirection(direction);
        animation.set(sheets[direction.ordinal()][1], 0.05f);
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
        Utils.drawCentered(sb, animation.get(), x, y + 4);
    }
}
