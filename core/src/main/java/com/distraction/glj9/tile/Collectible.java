package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Collectible extends Entity {

    private final EntityData.EntityType type;
    private final TextureRegion image;

    private float time;
    private float offsety;

    public Collectible(Context context, EntityData.EntityType type, int row, int col) {
        super(context, row, col, Direction.UP);
        this.type = type;

        if (type == EntityData.EntityType.COIN) image = context.getImage("coin");
        else if (type == EntityData.EntityType.DIAMOND) image = context.getImage("diamond");
        else if (type == EntityData.EntityType.CANDLE) image = context.getImage("candle");
        else throw new IllegalArgumentException("Invalid type: " + type);

        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    @Override
    public void move(float percent) {

    }

    @Override
    public void update(float dt) {
        time += dt;
        if (time > MathUtils.PI2) time -= MathUtils.PI2;
        offsety = MathUtils.sin(time * 3);
    }

    @Override
    public void render(SpriteBatch sb) {
        Utils.drawCentered(sb, image, x, y + 6 + offsety, w, h);
    }
}
