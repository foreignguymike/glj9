package com.distraction.glj9.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;
import com.distraction.glj9.tile.Entity;
import com.distraction.glj9.tile.LevelData;
import com.distraction.glj9.tile.SpeedButton;
import com.distraction.glj9.tile.TileMap;

public class HUD extends Entity {

    private static final Interpolation TITLE_POS = new FlyByInterpolation();
    private static final Interpolation NEXT_POS = Interpolation.swingOut;

    // hack because font integer positions and stuff
    private final TextureRegion levelText;
    private final TextureRegion secretText;
    private final TextureRegion[] levelNumText;
    private final int totalTextWidth;

    private final TextureRegion pixel;
    private final TextureRegion levelTextBg;
    private final TileMap tileMap;

    private final Button nextLevelButton;
    private final Button backButton;
    private final Button redoButton;
    private final Button startButton;
    public final SpeedButton speedButton;

    private int superSteps;
    private int arrows;

    private final GlyphLayout arrowsTitleText;
    private final GlyphLayout arrowsText;
    private final GlyphLayout superTitleText;
    private final GlyphLayout superText;

    private final BitmapFont font;
    public boolean startDown;

    private float levelTextTime;
    private float levelTextx;

    private float nextTime;

    public boolean startEnabled = true;
    public boolean speedEnabled = true;
    public boolean redoEnabled = true;

    public HUD(
        Context context,
        TileMap tileMap,
        SimpleCallback onStart,
        SimpleCallback onBack,
        SimpleCallback onRedo,
        SimpleCallback onNext
    ) {
        super(context);
        pixel = context.getPixel();
        this.tileMap = tileMap;
        w = 40;
        h = Constants.HEIGHT;

        font = context.getFont();

        levelTextBg = context.getImage("leveltextbg");
        arrowsTitleText = new GlyphLayout(font, "Arrows", Constants.WHITE, 0, Align.center, false);
        arrowsText = new GlyphLayout(font, tileMap.getRemainingArrows() + "", Constants.PINK, 0, Align.center, false);
        superTitleText = new GlyphLayout(font, "Super", Constants.WHITE, 0, Align.center, false);
        superText = new GlyphLayout(font, "0", Constants.PINK, 0, Align.center, false);

        TextureRegion[][] nextLevel = context.getImage("nextlevelbuttons").split(64, 12);
        nextLevelButton = new Button(
            context,
            new TextureRegion[]{nextLevel[0][0], nextLevel[1][0], nextLevel[2][0]},
            () -> {
                context.audio.playSound("select", 0.4f);
                onNext.callback();
            }
        );
        backButton = new Button(
            context,
            context.getImage("backbuttons").split(10, 10)[0],
            () -> {
                context.audio.playSound("back", 0.4f);
                onBack.callback();
            }
        );
        redoButton = new Button(
            context,
            context.getImage("redobuttons").split(10, 10)[0],
            () -> {
                context.audio.playSound("reset", 0.6f);
                nextTime = 0;
                onRedo.callback();
            }
        );
        startButton = new Button(
            context,
            context.getImage("startbuttons").split(32, 10)[0],
            () -> {
                context.audio.playSound("activate", 0.5f);
                onStart.callback();
            }
        );
        speedButton = new SpeedButton(
            context,
            context.getImage("speedbuttons").split(32, 10)[0],
            context.speed,
            () -> {
                context.audio.playSound("speed");
                setSpeed();
            }
        );

        nextLevelButton.x = (Constants.WIDTH - w) / 2f;
        nextLevelButton.y = -8;
        backButton.x = Constants.WIDTH - 27;
        backButton.y = Constants.HEIGHT - 10;
        redoButton.x = Constants.WIDTH - 10;
        redoButton.y = Constants.HEIGHT - 10;
        startButton.x = Constants.WIDTH - 19;
        startButton.y = 23;
        speedButton.x = Constants.WIDTH - 19;
        speedButton.y = 10;

        secretText = context.getImage("secret");
        levelText = context.getImage("leveltext");
        TextureRegion[] numTexts = context.getImage("numtext").split(6, 7)[0];
        String levelString = Integer.toString(tileMap.level);
        levelNumText = new TextureRegion[levelString.length()];
        for (int i = 0; i < levelString.length(); i++) {
            char c = levelString.charAt(i);
            if (c == '-') continue;
            levelNumText[i] = numTexts[c - '0'];
        }
        totalTextWidth = levelText.getRegionWidth() + 1 + 6 * levelNumText.length;
    }

    private void setSpeed() {
        context.speed = speedButton.getSpeed();
        tileMap.setSpeed(context.speed);
    }

    private boolean nextLevelVisible() {
        if (!tileMap.player.isWin()) return false;
        if (tileMap.level > 0 && tileMap.level < LevelData.levels.length + (Constants.SECRET_UNLOCKED ? 0 : -1)) return true;
        if (tileMap.level < 0 && tileMap.level * -1 < LevelData.tutorials.length) return true;
        return false;
    }

    public void onMouseMove(float mx, float my) {
        if (nextLevelVisible()) nextLevelButton.onMouseMoved(mx, my);
        backButton.onMouseMoved(mx, my);
        if (redoEnabled) redoButton.onMouseMoved(mx, my);
        if (startEnabled) startButton.onMouseMoved(mx, my);
        if (speedEnabled) speedButton.onMouseMoved(mx, my);
    }

    public boolean onMousePressed(boolean pressed) {
        boolean isPressed = false;
        if (nextLevelVisible()) isPressed |= nextLevelButton.onMousePressed(pressed);
        isPressed |= backButton.onMousePressed(pressed);
        if (redoEnabled) isPressed |= redoButton.onMousePressed(pressed);
        if (startEnabled) isPressed |= startButton.onMousePressed(pressed);
        if (speedEnabled) isPressed |= speedButton.onMousePressed(pressed);
        return isPressed;
    }

    public void enable() {
        redoEnabled = startEnabled = speedEnabled = true;
    }

    public void disable() {
        redoEnabled = startEnabled = speedEnabled = false;
        redoButton.hovered = speedButton.hovered = false;
    }

    @Override
    public void update(float dt) {
        if (tileMap.getRemainingArrows() != this.arrows) {
            this.arrows = tileMap.getRemainingArrows();
            this.arrowsText.setText(font, this.arrows + "", Constants.PINK, 0, Align.center, false);
        }
        if (tileMap.player.getSuperSteps() != this.superSteps) {
            this.superSteps = tileMap.player.getSuperSteps();
            this.superText.setText(font, this.superSteps + "", Constants.PINK, 0, Align.center, false);
        }
        startButton.pressed = tileMap.isStarted() || startDown;

        if (tileMap.level > 0) {
            levelTextTime += dt;
            levelTextx = (Constants.WIDTH - w) * 0.5f * TITLE_POS.apply(levelTextTime - 0.4f);
        }

        if (nextLevelVisible()) {
            nextTime += dt;
            if (nextTime > 0.5f) nextTime = 0.5f;
            nextLevelButton.y = -10 + 20 * NEXT_POS.apply(nextTime * 2);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (tileMap.level > 0) {
            Utils.drawCentered(sb, levelTextBg, levelTextx, Constants.HEIGHT / 2f - 3);
            if (Utils.isSecretLevel(tileMap.level)) {
                sb.draw(secretText, levelTextx - totalTextWidth / 2f + 4, Constants.HEIGHT / 2f - 6);
            } else {
                sb.draw(levelText, levelTextx - totalTextWidth / 2f, Constants.HEIGHT / 2f - 7);
                if (levelNumText.length > 1) {
                    sb.draw(levelNumText[0], levelTextx + 11 + 0.5f, Constants.HEIGHT / 2f - 6);
                    sb.draw(levelNumText[1], levelTextx + 17 + 0.5f, Constants.HEIGHT / 2f - 6);
                } else {
                    sb.draw(levelNumText[0], levelTextx + 15 + 0.5f, Constants.HEIGHT / 2f - 6);
                }
            }
        }
        sb.setColor(Constants.DIM_BG);
        sb.draw(pixel, Constants.WIDTH - w, 0, w, Constants.HEIGHT);
        for (int i = 0; i < Constants.HUD_BORDER_COLORS.length; i++) {
            sb.setColor(Constants.HUD_BORDER_COLORS[i]);
            sb.draw(pixel, Constants.WIDTH - w + i, 0, 1, Constants.HEIGHT);
        }
        sb.setColor(Color.WHITE);
        font.draw(sb, arrowsTitleText, startButton.x, Constants.HEIGHT - 19);
        font.draw(sb, arrowsText, startButton.x, Constants.HEIGHT - 29);
        font.draw(sb, superTitleText, startButton.x, Constants.HEIGHT - 39);
        font.draw(sb, superText, startButton.x, Constants.HEIGHT - 51);
        if (nextLevelVisible()) nextLevelButton.render(sb);
        backButton.render(sb);
        redoButton.render(sb);
        startButton.render(sb);
        speedButton.render(sb);
    }

}
