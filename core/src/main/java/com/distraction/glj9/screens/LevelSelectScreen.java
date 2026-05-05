package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.LevelData;
import com.distraction.glj9.tile.LevelTile;
import com.distraction.glj9.utils.Background;

public class LevelSelectScreen extends Screen {

    private final TextureRegion pixel;
    private final TextureRegion titleBg;
    private final TextureRegion border;
    private final Background bg;

    private final BitmapFont font;
    private final GlyphLayout titleText;

    private final LevelTile[][] levelTiles;
    private int maxLevels = LevelData.levels.length;
    private int page;

    public LevelSelectScreen(Context context) {
        super(context);
        pixel = context.getPixel();
        titleBg = context.getImage("levelselecttitlebg");
        border = context.getImage("levelselectborder");
        bg = new Background(context, context.getImage("bg2"), 2, 2, 16, 16);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        font = context.getFont();
        titleText = new GlyphLayout(font, "Level Select", Constants.WHITE, 0, Align.center, false);


        int count = 0;
        TextureRegion levelTileImage = context.getImage("leveltile");
        TextureRegion levelTileHighlightImage = context.getImage("leveltileh");
        levelTiles = new LevelTile[3][4];
        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                LevelTile levelTile = new LevelTile(
                    context,
                    getLevelNumber(row, col, 0),
                    levelTileImage,
                    levelTileHighlightImage,
                    this::onLevelSelected
                );
                levelTile.x = 12 + 18 * col;
                levelTile.y = 59 - 18 * row;
                levelTiles[row][col] = levelTile;
                count++;
                if (count > maxLevels) levelTile.setVisibility(false);
            }
        }
    }

    private void onLevelSelected(int level) {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new PlayScreen(context, level));
        });
        out.start();
    }

    private int getLevelNumber(int row, int col, int page) {
        return 1 + col + row * 4 + 12 * page;
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                levelTiles[row][col].onMouseMoved(m.x, m.y);
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (int row = 0; row < levelTiles.length; row++) {
                for (int col = 0; col < levelTiles[0].length; col++) {
                    levelTiles[row][col].onMousePressed();
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
        bg.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.LEVEL_SELECT_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        bg.render(sb);
        sb.setColor(Constants.DIM_BG);
        sb.draw(pixel, 3, 2, border.getRegionWidth() / 2f - 5, border.getRegionHeight() - 2);
        sb.draw(pixel, Constants.WIDTH / 2f + 3, 2, border.getRegionWidth() / 2f - 5, border.getRegionHeight() - 2);
        sb.setColor(Color.WHITE);
        sb.draw(border, 1, 1);

        sb.setColor(Constants.LEVEL_SELECT_TOP);
        sb.draw(pixel, 0, Constants.HEIGHT - 9, Constants.WIDTH, 9);
        sb.setColor(Color.WHITE);
        sb.draw(titleBg, 0, Constants.HEIGHT - 14);
        font.draw(sb, titleText, Constants.WIDTH / 2f, Constants.HEIGHT - 5f);

        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                levelTiles[row][col].render(sb);
            }
        }

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
