package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    public static final int TILE_SIZE = 32;
    public static final int TILE_SIZE_2 = TILE_SIZE / 2;

    private static final int WALL_UP = 0b1000;
    private static final int WALL_LEFT = 0b0100;
    private static final int WALL_DOWN = 0b0010;
    private static final int WALL_RIGHT = 0b0001;
    private static final int[] TILE_ID_TO_MASK = {
        15,14, 8, 8, 8, 9,15,
        13,12, 0, 0, 1, 4,11,
        4, 0, 0, 2, 0, 0, 9,
        4, 0, 1,12, 0, 0, 1,
        4, 2, 0, 0, 0, 0, 1,
        6, 8, 0, 0, 0, 3, 5,
        15, 7, 6, 2, 2,10, 3
    };

    private static final float STEP_DURATION = 0.5f;

    private final Context context;
    private final TextureRegion[] tileset;

    private int[][] tiles;
    private Entity player;
    private List<Entity> entities;
    private float stepTimer = 0;

    public TileMap(Context context) {
        this.context = context;
        tileset = flat(context.getImage("tileset").split(TILE_SIZE, TILE_SIZE));
    }

    public void loadLevel(int level) {
        LevelData data = LevelData.levels[level];
        tiles = flip(data.tiles);
        entities = new ArrayList<>();
        for (EntityData e : data.entityDataList) {
            if (e.type == EntityData.EntityType.PLAYER) {
                player = new Player(context, e.row, e.col, e.direction);
            } else {
                entities.add(new Collectible(context, e.row, e.col));
            }
        }
        for (Entity e : entities) {
            if (e instanceof Collectible) continue;
            e.moveDirection(getNextDirection(e));
        }
        player.moveDirection(getNextDirection(player));
    }

    public Direction getNextDirection(Entity e) {
        Direction direction = e.direction;
        int tile = TILE_ID_TO_MASK[tiles[e.row][e.col] - 1];
        if (direction == Direction.UP) {
            if ((tile & WALL_UP) == 0) return Direction.UP;
            else if ((tile & WALL_RIGHT) == 0) return Direction.RIGHT;
            else if ((tile & WALL_LEFT) == 0) return Direction.LEFT;
            else return Direction.DOWN;
        } else if (direction == Direction.LEFT) {
            if ((tile & WALL_LEFT) == 0) return Direction.LEFT;
            else if ((tile & WALL_UP) == 0) return Direction.UP;
            else if ((tile & WALL_DOWN) == 0) return Direction.DOWN;
            else return Direction.RIGHT;
        } else if (direction == Direction.DOWN) {
            if ((tile & WALL_DOWN) == 0) return Direction.DOWN;
            else if ((tile & WALL_LEFT) == 0) return Direction.LEFT;
            else if ((tile & WALL_RIGHT) == 0) return Direction.RIGHT;
            else return Direction.UP;
        } else if (direction == Direction.RIGHT) {
            if ((tile & WALL_RIGHT) == 0) return Direction.RIGHT;
            else if ((tile & WALL_DOWN) == 0) return Direction.DOWN;
            else if ((tile & WALL_UP) == 0) return Direction.UP;
            else return Direction.LEFT;
        }
        throw new IllegalStateException("stuck");
    }

    public void update(float dt) {
        stepTimer += dt;

        if (stepTimer > STEP_DURATION) {
            stepTimer = 0;
            for (Entity e : entities) {
                if (e instanceof Collectible) continue;
                e.finish();
                e.moveDirection(getNextDirection(e));
            }
            player.finish();
            player.moveDirection(getNextDirection(player));
        }

        float percent = stepTimer / STEP_DURATION;
        for (Entity e : entities) {
            e.update(dt);
            if (!(e instanceof Collectible)) e.move(percent);
        }
        player.update(dt);
        player.move(percent);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                sb.draw(tileset[tiles[row][col] - 1], col * TILE_SIZE, row * TILE_SIZE);
            }
        }
        for (Entity e : entities) e.render(sb);
        player.render(sb);
    }

    private static TextureRegion[] flat(TextureRegion[][] tileset) {
        TextureRegion[] ret = new TextureRegion[tileset.length * tileset[0].length];
        int cols = tileset.length;
        for (int row = 0; row < tileset.length; row++) {
            for (int col = 0; col < tileset[0].length; col++) {
                ret[row * cols + col] = tileset[row][col];
            }
        }
        return ret;
    }

    private static int[][] flip(int[][] tiles) {
        int[][] ret = new int[tiles.length][tiles[0].length];
        for (int row = 0; row < tiles.length; row++) {
            ret[tiles.length - row - 1] = tiles[row];
        }
        return ret;
    }

}
