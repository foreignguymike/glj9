package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TileMap {

    public static final int TILE_SIZE = 16;
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

    private static final float[] STEP_DURATIONS = new float[] {0.4f, 0.2f, 0.1f};
    private float stepDuration = 0.4f;

    private final Context context;
    private final TextureRegion[][] tilesets;
    private LevelData data;

    private int[][] tiles;
    private int numRows;
    private int numCols;
    private Player player;
    private List<Entity> ghosts;
    private List<Entity> sortedEntities;
    private List<Entity> arrows;
    private List<Entity> collectibles;
    private float stepTimer = 0;
    private boolean started = false;

    private final TextureRegion cursor;
    private int cursorRow = -1;
    private int cursorCol = -1;
    private int maxArrows;
    private int remainingArrows;

    private final Comparator<Entity> comp = (e1, e2) -> (int) e2.y - (int) e1.y;

    private int score;

    public TileMap(Context context, int level) {
        this.context = context;
        tilesets = new TextureRegion[][] {
            flat(context.getImage("tileset").split(TILE_SIZE, TILE_SIZE)),
            flat(context.getImage("tileset2").split(TILE_SIZE, TILE_SIZE))
        };
        cursor = context.getImage("cursor");
        loadLevel(level);
        setSpeed(1);
    }

    private void loadLevel(int level) {
        data = LevelData.levels[level];
        tiles = flip(data.tiles);
        numRows = tiles.length;
        numCols = tiles[0].length;
        ghosts = new ArrayList<>();
        sortedEntities = new ArrayList<>();
        arrows = new ArrayList<>();
        collectibles = new ArrayList<>();
        for (EntityData e : data.entityDataList) {
            if (e.type == EntityData.EntityType.PLAYER) {
                player = new Player(context, numRows - e.row - 1, e.col, e.direction);
            } else if (e.type == EntityData.EntityType.ARROW) {
                arrows.add(new Arrow(context, numRows - e.row - 1, e.col, e.direction));
            } else {
                ghosts.add(new Ghost(context, numRows - e.row - 1, e.col, e.direction));
            }
        }
        int[][] coll = flip(data.collectibles);
        for (int row = 0; row < coll.length; row++) {
            for (int col = 0; col < coll[0].length; col++) {
                int id = coll[row][col];
                EntityData.EntityType type;
                if (id == 1) type = EntityData.EntityType.COIN;
                else if (id == 2) type = EntityData.EntityType.CANDLE;
                else if (id == 3) type = EntityData.EntityType.DIAMOND;
                else continue;
                collectibles.add(new Collectible(context, type, row, col));
            }
        }
        started = false;
        cursorRow = numRows / 2;
        cursorCol = numCols / 2;
        maxArrows = data.numArrows;
        remainingArrows = maxArrows - arrows.size();
        sortedEntities.add(player);
        sortedEntities.addAll(ghosts);
        sortedEntities.sort(comp);
    }

    public void redo() {
        ghosts = new ArrayList<>();
        collectibles = new ArrayList<>();
        for (EntityData e : data.entityDataList) {
            if (e.type == EntityData.EntityType.PLAYER) {
                player.setTile(numRows - e.row - 1, e.col);
                player.setDirection(e.direction);
                player.redo();
            } else if (e.type == EntityData.EntityType.GHOST) {
                ghosts.add(new Ghost(context, numRows - e.row - 1, e.col, e.direction));
            }
        }
        int[][] coll = flip(data.collectibles);
        for (int row = 0; row < coll.length; row++) {
            for (int col = 0; col < coll[0].length; col++) {
                int id = coll[row][col];
                EntityData.EntityType type;
                if (id == 1) type = EntityData.EntityType.COIN;
                else if (id == 2) type = EntityData.EntityType.CANDLE;
                else if (id == 3) type = EntityData.EntityType.DIAMOND;
                else continue;
                collectibles.add(new Collectible(context, type, row, col));
            }
        }
        started = false;
        stepTimer = 0;
        score = 0;
        cursorRow = numRows / 2;
        cursorCol = numCols / 2;
        sortedEntities.clear();
        sortedEntities.add(player);
        sortedEntities.addAll(ghosts);
        sortedEntities.sort(comp);
    }

    public void setSpeed(int speed) {
        float previousPercent = stepTimer / stepDuration;
        stepDuration = STEP_DURATIONS[speed - 1];
        stepTimer = stepDuration * previousPercent;
        player.setSpeed(speed);
    }

    public int getRemainingArrows() {
        return remainingArrows;
    }

    public int getScore() {
        return score;
    }

    public int getWidth() {
        return numCols * TILE_SIZE;
    }

    public int getHeight() {
        return numRows * TILE_SIZE;
    }

    public void onMouseMove(float mx, float my) {
        if (mx > 0 && mx < getWidth() && my > 0 && my < getHeight()) {
            cursorRow = (int) (my / TILE_SIZE);
            cursorCol = (int) (mx / TILE_SIZE);
        } else {
            cursorRow = cursorCol = -1;
        }
    }

    private Entity getExistingArrow() {
        for (Entity a : arrows) {
            if (a.row == cursorRow && a.col == cursorCol) {
                return a;
            }
        }
        return null;
    }

    public void place() {
        if (started) return;
        if (cursorRow == -1 || cursorCol == -1) return;
        Entity existingArrow = getExistingArrow();
        if (existingArrow == null) {
            if (remainingArrows > 0) {
                remainingArrows--;
                arrows.add(new Arrow(context, cursorRow, cursorCol, Direction.RIGHT));
            }
        } else {
            existingArrow.rotate();
        }
    }

    public void remove() {
        if (started) return;
        if (cursorRow == -1 || cursorCol == -1) return;
        if (remainingArrows >= maxArrows) return;
        Entity existingArrow = getExistingArrow();
        if (existingArrow != null) {
            remainingArrows++;
            arrows.remove(existingArrow);
        }
    }

    public void start() {
        if (!started) {
            started = true;
            for (Entity e : ghosts) {
                e.start();
                e.moveDirection(getNextDirection(e));
            }
            player.start();
            player.moveDirection(getNextDirection(player));
        }
    }

    public boolean isStarted() {
        return started;
    }

    public Direction getNextDirection(Entity e) {
        int tile = TILE_ID_TO_MASK[tiles[e.row][e.col] - 1];
        for (Entity a : arrows) {
            if (e.row == a.row && e.col == a.col) {
                e.direction = a.direction;
                if (a.direction == Direction.UP && (tile & WALL_UP) == 0) return Direction.UP;
                else if (a.direction == Direction.LEFT && (tile & WALL_LEFT) == 0) return Direction.LEFT;
                else if (a.direction == Direction.DOWN && (tile & WALL_DOWN) == 0) return Direction.DOWN;
                else if (a.direction == Direction.RIGHT && (tile & WALL_RIGHT) == 0) return Direction.RIGHT;
            }
        }
        Direction direction = e.direction;
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
        if (started) {
            stepTimer += dt;
            if (stepTimer > stepDuration) {
                stepTimer = 0;
                player.finish();
                player.moveDirection(getNextDirection(player));
                for (int i = 0; i < collectibles.size(); i++) {
                    Entity c = collectibles.get(i);
                    if (player.row == c.row && player.col == c.col) {
                        collectibles.remove(i);
                        score += 100;
                        i--;
                    }
                }
                for (Entity g : ghosts) {
                    g.finish();
                    g.moveDirection(getNextDirection(g));
                }
            }
        }

        float percent = stepTimer / stepDuration;
        for (Entity g : ghosts) {
            g.update(dt);
            if (started) g.move(percent);
        }
        player.update(dt);
        if (started) player.move(percent);
        for (Entity c : collectibles) c.update(dt);
        for (Entity a : arrows) a.update(dt);

        sortedEntities.sort(comp);
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                sb.draw(tilesets[(row + col) & 1][tiles[row][col] - 1], col * TILE_SIZE, row * TILE_SIZE);
            }
        }
        for (Entity a : arrows) a.render(sb);
        for (Entity c : collectibles) c.render(sb);
        for (Entity e : sortedEntities) e.render(sb);
        if (!started && cursorRow != -1 && cursorCol != -1) {
            sb.draw(cursor, cursorCol * TILE_SIZE + 1, cursorRow * TILE_SIZE + 1);
        }
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
