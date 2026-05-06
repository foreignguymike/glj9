package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Collectible extends Entity {

    public final EntityData.EntityType type;
    private final TextureRegion image;

    private float time;
    private float offsety;

    public Collectible(Context context, EntityData.EntityType type, int row, int col) {
        super(context, row, col, Direction.UP);
        this.type = type;

        if (type == EntityData.EntityType.PELLET) image = context.getImage("pellet");
        else if (type == EntityData.EntityType.SUPER_PELLET) image = context.getImage("superpellet");
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
        if (transparent) sb.setColor(Constants.TRANSPARENT);
        else sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, image, x, y + 2 + offsety, w, h);
    }
}
