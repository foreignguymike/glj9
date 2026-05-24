package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class TileMapPreview {

    private static final int ox = 120;

    private static final int PELLET = 1;
    private static final int SUPER_PELLET = 2;
    private static final int PLAYER = 3;
    private static final int GHOST = 4;

    public static final int TILE_SIZE = 5;

    private final Context context;
    private final TextureRegion[][] tilesets;
    private final TextureRegion pixel;
    private final TextureRegion pellet;
    private final BitmapFont font;
    private final GlyphLayout levelText;
    private final GlyphLayout arrowsText;

    private int level;
    private int[][] tiles;
    private int[][] objs;
    private int numRows;
    private int numCols;
    private int startx;
    private int starty;

    public TileMapPreview(Context context) {
        this.context = context;
        tilesets = new TextureRegion[][]{
            Utils.flat(context.getImage("tilesetpreview").split(TILE_SIZE, TILE_SIZE)),
            Utils.flat(context.getImage("tileset2preview").split(TILE_SIZE, TILE_SIZE))
        };
        pixel = context.getPixel();
        pellet = context.getImage("pellet");
        font = context.getFont();
        levelText = new GlyphLayout(font, "Level 1", Constants.WHITE, 0, Align.center, false);
        arrowsText = new GlyphLayout(font, "Arrows: 0", Constants.WHITE, 0, Align.center, false);
    }

    public void load(int level) {
        if (this.level == level) return;
        this.level = level;
        if (level <= 0) return;
        LevelData data = LevelData.levels[level - 1];
        tiles = Utils.flip(data.tiles);
        numRows = tiles.length;
        numCols = tiles[0].length;
        int w = numCols * TILE_SIZE;
        int h = numRows * TILE_SIZE;
        startx = ox - w / 2;
        starty = 36 - h / 2;
        int[][] coll = Utils.flip(data.collectibles);
        objs = new int[numRows][numCols];
        for (int row = 0; row < objs.length; row++) {
            for (int col = 0; col < objs[0].length; col++) {
                objs[row][col] = coll[row][col];
            }
        }
        for (EntityData e : data.entityDataList) {
            if (e.type == EntityData.EntityType.PLAYER) {
                objs[numRows - e.row - 1][e.col] = PLAYER;
            } else if (e.type == EntityData.EntityType.GHOST) {
                objs[numRows - e.row - 1][e.col] = GHOST;
            }
        }
        String text = Utils.isSecretLevel(level) ? "Secret" : "Level " + level;
        levelText.setText(font, text, Constants.LEVEL_SELECT_LEVEL_TEXT_COLOR, 0, Align.center, false);
        arrowsText.setText(font, "Arrows: " + data.numArrows, Constants.LEVEL_SELECT_LEVEL_TEXT_COLOR, 0, Align.center, false);
    }

    public void render(SpriteBatch sb) {
        if (level <= 0) return;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int type = tiles[row][col] - 1;
                if (type < 0) continue;
                sb.setColor(Color.WHITE);
                sb.draw(tilesets[(row + col) & 1][type], startx + col * TILE_SIZE, starty + row * TILE_SIZE);
                type = objs[row][col];
                if (type == PELLET) {
                    sb.setColor(Constants.PREVIEW_PELLET);
                    sb.draw(pixel, startx + col * TILE_SIZE + 2, starty + row * TILE_SIZE + 2);
                } else if (type == SUPER_PELLET) {
                    sb.setColor(Constants.PREVIEW_PELLET);
                    sb.draw(pixel, startx + col * TILE_SIZE + 1, starty + row * TILE_SIZE + 1, 3, 3);
                } else if (type == PLAYER) {
                    sb.setColor(Constants.PREVIEW_PLAYER);
                    sb.draw(pixel, startx + col * TILE_SIZE + 1, starty + row * TILE_SIZE + 1, 3, 3);
                } else if (type == GHOST) {
                    sb.setColor(Constants.PREVIEW_GHOST);
                    sb.draw(pixel, startx + col * TILE_SIZE + 1, starty + row * TILE_SIZE + 1, 3, 3);
                }
            }
        }
        font.draw(sb, levelText, ox, 68);
        sb.setColor(Color.WHITE);
        if (context.isComplete(level - 1)) sb.draw(pellet, ox - 34, 7);
        font.draw(sb, arrowsText, ox, 13);
    }

}
