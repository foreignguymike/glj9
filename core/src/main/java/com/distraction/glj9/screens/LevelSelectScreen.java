package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;
import com.distraction.glj9.tile.LevelData;
import com.distraction.glj9.tile.LevelTile;
import com.distraction.glj9.tile.TileMapPreview;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.MusicFader;

import java.util.List;

public class LevelSelectScreen extends Screen {

    private static final int ox = 40;

    private final TextureRegion pixel;
    private final TextureRegion titleBg;
    private final TextureRegion border;
    private final Background bg;
    private final Button backButton;

    private final BitmapFont font;
    private final GlyphLayout titleText;
    private final GlyphLayout difficultyText;

    private final LevelTile[][] levelTiles;
    private final int maxLevels = LevelData.levels.length;
    private final int maxPages = (maxLevels - 1) / 12;
    private int page;
    private final Button pageLeft;
    private final Button pageRight;

    private final TileMapPreview preview;

    private final MusicFader musicFader;

    public LevelSelectScreen(Context context) {
        super(context);
        pixel = context.getPixel();
        titleBg = context.getImage("levelselecttitlebg");
        border = context.getImage("uiborder");
        bg = new Background(context, context.getImage("bg2"), 2, 2, 16, 16);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        this.page = context.page;

        font = context.getFont();
        titleText = new GlyphLayout(font, "Level Select", Constants.WHITE, 0, Align.center, false);
        difficultyText = new GlyphLayout(font, getDifficulty(page), Constants.WHITE, 0, Align.center, false);

        levelTiles = new LevelTile[3][4];
        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                int level = getLevelNumber(row, col, page);
                LevelTile levelTile = new LevelTile(
                    context,
                    level,
                    this::onLevelSelected,
                    this::onLevelHover
                );
                levelTile.x = ox - 27 + 18 * col;
                levelTile.y = 59 - 18 * row;
                levelTiles[row][col] = levelTile;
            }
        }

        TextureRegion[] left = new TextureRegion[]{
            context.getImage("pageleft"),
            context.getImage("pagelefth"),
            context.getImage("pageleftp"),
        };
        pageLeft = new Button(context, left, this::onPageLeft);
        TextureRegion[] right = new TextureRegion[]{
            context.getImage("pageright"),
            context.getImage("pagerighth"),
            context.getImage("pagerightp"),
        };
        pageRight = new Button(context, right, this::onPageRight);
        pageLeft.x = ox - 27;
        pageLeft.y = 9;
        pageRight.x = ox + 26;
        pageRight.y = 9;

        preview = new TileMapPreview(context);

        backButton = new Button(context, context.getImage("backbuttons").split(10, 10)[0], this::onBack);
        backButton.x = 8;
        backButton.y = Constants.HEIGHT - 8;

        if (!context.audio.isPlaying("levelselect")) {
            List<Music> playing = context.audio.getCurrentlyPlayingList();
            if (!playing.isEmpty()) {
                musicFader = new MusicFader(playing, 1f, () -> {
                    context.audio.playMusic("levelselect", 0.5f, true);
                });
            } else {
                musicFader = null;
                context.audio.playMusic("levelselect", 0.5f, true);
            }
        } else {
            musicFader = null;
            context.audio.playMusic("levelselect", 0.5f, true);
        }
    }

    private void onBack() {
        context.audio.playSound("back", 0.4f);
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            TitleScreen s = new TitleScreen(context);
            s.in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> s.ignoreInput = false);
            s.in.start();
            context.sm.replace(s);
        });
        out.start();
    }

    private String getDifficulty(int page) {
        if (page == 0) return "Easy";
        else if (page == 1) return "Hard";
        else return "Tricky";
    }

    private void onLevelHover(int level) {
        if (level >= 1 && level <= maxLevels) {
            context.audio.playSound("pop", 0.2f, MathUtils.random(0.5f, 0.6f));
            preview.load(level);
        }
    }

    private void onPageLeft() {
        if (page - 1 < 0) return;
        context.audio.playSound("select3", 0.2f);
        page--;
        context.page = page;
        reloadPage();
    }

    private void onPageRight() {
        if (page + 1 > maxPages) return;
        context.audio.playSound("select3", 0.2f);
        page++;
        context.page = page;
        reloadPage();
    }

    private void reloadPage() {
        difficultyText.setText(font, getDifficulty(page), Constants.WHITE, 0, Align.center, false);
        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                LevelTile tile = levelTiles[row][col];
                int level = getLevelNumber(row, col, page);
                tile.setLevel(level, (row + col) * 0.04f + 0.05f);
            }
        }
    }

    private void onLevelSelected(int level) {
        if (musicFader != null) musicFader.setActive(false);
        context.audio.playSound("select2", 0.4f);
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
        pageLeft.onMouseMoved(m.x, m.y);
        pageRight.onMouseMoved(m.x, m.y);
        backButton.onMouseMoved(m.x, m.y);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            for (int row = 0; row < levelTiles.length; row++) {
                for (int col = 0; col < levelTiles[0].length; col++) {
                    levelTiles[row][col].onMousePressed();
                }
            }
            backButton.onMousePressed(true);
        }
        pageLeft.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
        pageRight.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
        if (musicFader != null) musicFader.update(dt);
        bg.update(dt);

        for (int row = 0; row < levelTiles.length; row++) {
            for (int col = 0; col < levelTiles[0].length; col++) {
                levelTiles[row][col].update(dt);
            }
        }
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.LEVEL_SELECT_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        bg.render(sb);
        sb.setColor(Constants.DIM_BG);
        sb.draw(pixel, 3, 2, border.getRegionWidth() - 3, border.getRegionHeight() - 2);
        sb.draw(pixel, Constants.WIDTH / 2f + 3, 2, border.getRegionWidth() - 3, border.getRegionHeight() - 2);
        sb.setColor(Color.WHITE);
        sb.draw(border, 1, 1);
        sb.draw(border, Constants.WIDTH / 2f + 1, 1);
        sb.setColor(Constants.DARK_RED);
        sb.draw(pixel, Constants.WIDTH / 2f - 1, 4, 2, border.getRegionHeight() - 6);

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
        pageLeft.render(sb);
        pageRight.render(sb);
        font.draw(sb, difficultyText, ox, 13);

        preview.render(sb);

        backButton.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Color.WHITE);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
