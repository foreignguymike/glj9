package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.Utils;

public class Arrow extends Entity {

    private final TextureRegion image;

    protected Arrow(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 14;
        h = 14;
        image = context.getImage("arrows").split(12, 13)[0][direction.ordinal()];
    }

    @Override
    public void moveDirection(Direction direction) {
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y);
    }
}
