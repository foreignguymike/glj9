package com.distraction.glj9.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;
import com.distraction.glj9.tile.Entity;
import com.distraction.glj9.tile.TileMap;

public class HUD extends Entity {

    private final TextureRegion pixel;
    private final TileMap tileMap;

    private final Button startButton;
    private final Button redoButton;

    public HUD(
        Context context,
        TileMap tileMap,
        SimpleCallback onStart,
        SimpleCallback onRedo
    ) {
        super(context);
        pixel = context.getPixel();
        this.tileMap = tileMap;

        startButton = new Button(
            context,
            new TextureRegion[]{
                context.getImage("startbutton"),
                context.getImage("startbuttonh"),
                context.getImage("startbuttonp")
            },
            onStart
        );
        redoButton = new Button(
            context,
            new TextureRegion[]{
                context.getImage("redobutton"),
                context.getImage("redobuttonh"),
                context.getImage("redobuttonp")
            },
            onRedo
        );

        w = 40;
        h = Constants.HEIGHT;

        startButton.x = Constants.WIDTH - 19;
        startButton.y = 25;
        redoButton.x = Constants.WIDTH - 19;
        redoButton.y = 10;
    }

    public void onMouseMove(float mx, float my) {
        startButton.onMouseMoved(mx, my);
        redoButton.onMouseMoved(mx, my);
    }

    public void onMousePressed(boolean pressed) {
        startButton.onMousePressed(pressed);
        redoButton.onMousePressed(pressed);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Constants.DIM_BG);
        sb.draw(pixel, Constants.WIDTH - w, 0, w, Constants.HEIGHT);
        for (int i = 0; i < Constants.HUD_BORDER_COLORS.length; i++) {
            sb.setColor(Constants.HUD_BORDER_COLORS[i]);
            sb.draw(pixel, Constants.WIDTH - w + i, 0, 1, Constants.HEIGHT);
        }
        startButton.render(sb);
        redoButton.render(sb);
    }

}
