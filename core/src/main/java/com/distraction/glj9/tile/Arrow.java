package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Arrow extends Entity {

    private TextureRegion image;

    protected Arrow(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 12;
        h = 12;
        image = context.getImage("arrows").split(12, 12)[0][direction.ordinal()];
    }

    @Override
    public void rotate() {
        super.rotate();
        image = context.getImage("arrows").split(12, 12)[0][direction.ordinal()];
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
