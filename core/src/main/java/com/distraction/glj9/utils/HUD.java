package com.distraction.glj9.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
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

    private int score;
    private int arrows;

    private final GlyphLayout scoreTitleText;
    private final GlyphLayout scoreText;
    private final GlyphLayout arrowsTitleText;
    private final GlyphLayout arrowsText;

    private final BitmapFont font;

    public HUD(
        Context context,
        TileMap tileMap,
        SimpleCallback onStart,
        SimpleCallback onRedo
    ) {
        super(context);
        pixel = context.getPixel();
        this.tileMap = tileMap;
        w = 40;
        h = Constants.HEIGHT;

        font = context.getFont();

        scoreTitleText = new GlyphLayout(font, "Score", Constants.WHITE, 1, Align.center, false);
        scoreText = new GlyphLayout(font, "0", Constants.PINK, 1, Align.center, false);
        arrowsTitleText = new GlyphLayout(font, "Arrows", Constants.WHITE, 1, Align.center, false);
        arrowsText = new GlyphLayout(font, tileMap.getRemainingArrows() + "", Constants.PINK, 1, Align.center, false);

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
        if (tileMap.getScore() != this.score) {
            this.score = tileMap.getScore();
            this.scoreText.setText(font, this.score + "", Constants.PINK, 0, Align.center, false);
        }

        if (tileMap.getRemainingArrows() != this.arrows) {
            this.arrows = tileMap.getRemainingArrows();
            this.arrowsText.setText(font, this.arrows + "", Constants.PINK, 0, Align.center, false);
        }

        startButton.pressed = tileMap.isStarted();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Constants.DIM_BG);
        sb.draw(pixel, Constants.WIDTH - w, 0, w, Constants.HEIGHT);
        for (int i = 0; i < Constants.HUD_BORDER_COLORS.length; i++) {
            sb.setColor(Constants.HUD_BORDER_COLORS[i]);
            sb.draw(pixel, Constants.WIDTH - w + i, 0, 1, Constants.HEIGHT);
        }
        sb.setColor(Color.WHITE);
        font.draw(sb, scoreTitleText, startButton.x, Constants.HEIGHT - 5);
        font.draw(sb, scoreText, startButton.x, Constants.HEIGHT - 15);
        font.draw(sb, arrowsTitleText, startButton.x, Constants.HEIGHT - 30);
        font.draw(sb, arrowsText, startButton.x, Constants.HEIGHT - 40);
        startButton.render(sb);
        redoButton.render(sb);
    }

}
