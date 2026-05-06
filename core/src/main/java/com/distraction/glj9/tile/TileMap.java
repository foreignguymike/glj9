package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

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
    public final int level;

    private int[][] tiles;
    private int numRows;
    private int numCols;
    public Player player;
    private Ghost ghostCollide = null;
    private List<Ghost> ghosts;
    private List<Entity> sortedEntities;
    private List<Entity> arrows;
    private List<Collectible> collectibles;
    private float stepTimer = 0;
    private boolean started = false;

    private final TextureRegion cursor;
    private int cursorRow = -1;
    private int cursorCol = -1;
    private List<Entity> transparentEntities;

    private int maxArrows;
    private int remainingArrows;

    private final Comparator<Entity> comp = (e1, e2) -> {
        int diff = (int) e2.y - (int) e1.y;
        if (diff == 0 && e2 instanceof Player) return -1;
        return diff;
    };

    public TileMap(Context context, int level) {
        this.context = context;
        this.level = level;
        tilesets = new TextureRegion[][] {
            Utils.flat(context.getImage("tileset").split(TILE_SIZE, TILE_SIZE)),
            Utils.flat(context.getImage("tileset2").split(TILE_SIZE, TILE_SIZE))
        };
        cursor = context.getImage("cursor");
        loadLevel(level);
        setSpeed(context.speed);
    }

    private void loadLevel(int level) {
        data = LevelData.levels[level - 1];
        tiles = Utils.flip(data.tiles);
        numRows = tiles.length;
        numCols = tiles[0].length;
        ghosts = new ArrayList<>();
        sortedEntities = new ArrayList<>();
        arrows = new ArrayList<>();
        collectibles = new ArrayList<>();
        transparentEntities = new ArrayList<>();
        for (EntityData e : data.entityDataList) {
            if (e.type == EntityData.EntityType.PLAYER) {
                player = new Player(context, numRows - e.row - 1, e.col, e.direction);
            } else if (e.type == EntityData.EntityType.GHOST) {
                ghosts.add(new Ghost(context, numRows - e.row - 1, e.col, e.direction));
            }
        }
        int[][] coll = Utils.flip(data.collectibles);
        for (int row = 0; row < coll.length; row++) {
            for (int col = 0; col < coll[0].length; col++) {
                int id = coll[row][col];
                EntityData.EntityType type;
                if (id == 1) type = EntityData.EntityType.PELLET;
                else if (id == 2) type = EntityData.EntityType.SUPER_PELLET;
                else continue;
                collectibles.add(new Collectible(context, type, row, col));
            }
        }
        Collections.reverse(collectibles);
        started = false;
        setCursorTile(-1, -1);
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
        int[][] coll = Utils.flip(data.collectibles);
        for (int row = 0; row < coll.length; row++) {
            for (int col = 0; col < coll[0].length; col++) {
                int id = coll[row][col];
                EntityData.EntityType type;
                if (id == 1) type = EntityData.EntityType.PELLET;
                else if (id == 2) type = EntityData.EntityType.SUPER_PELLET;
                else continue;
                collectibles.add(new Collectible(context, type, row, col));
            }
        }
        Collections.reverse(collectibles);
        started = false;
        stepTimer = 0;
        setCursorTile(-1, -1);
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
        for (Ghost g : ghosts) g.setSpeed(speed);
    }

    public int getRemainingArrows() {
        return remainingArrows;
    }

    public int getWidth() {
        return numCols * TILE_SIZE;
    }

    public int getHeight() {
        return numRows * TILE_SIZE;
    }

    public void onMouseMove(float mx, float my) {
        if (started) return;
        if (mx > 0 && mx < getWidth() && my > 0 && my < getHeight()) {
            setCursorTile((int) (my / TILE_SIZE), (int) (mx / TILE_SIZE));
        } else {
            setCursorTile(-1, -1);
        }
    }

    private void setCursorTile(int row, int col) {
        for (Entity e : transparentEntities) e.transparent = false;
        transparentEntities.clear();
        this.cursorRow = row;
        this.cursorCol = col;
        if (row == -1 || col == -1) return;
        if (tiles[row][col] == 0) {
            cursorRow = cursorCol = -1;
            return;
        }
        for (Entity g : ghosts) {
            if (g.row == row && g.col == col) {
                transparentEntities.add(g);
                break;
            }
        }
        for (Entity c : collectibles) {
            if (c.row == row && c.col == col) {
                transparentEntities.add(c);
                break;
            }
        }
        for (Entity e : transparentEntities) e.transparent = true;
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
        if (player.row == cursorRow && player.col == cursorCol) {
            player.rotate();
            return;
        }
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
            setCursorTile(-1, -1);
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

    private void findGhostCollide() {
        for (Ghost g : ghosts) {
            if (player.destrow == g.row && player.destcol == g.col && g.destrow == player.row && g.destcol == player.col) {
                ghostCollide = g;
                break;
            }
        }
    }

    private void doGhostCollide() {
        if (ghostCollide == null) return;
        if (player.isSuper()) {
            sortedEntities.remove(ghostCollide);
            ghosts.remove(ghostCollide);
            checkComplete();
        } else {
            player.setDead();
        }
        ghostCollide = null;
    }

    private void checkComplete() {
        if (collectibles.isEmpty() && ghosts.isEmpty()) {
            context.completedLevels[level - 1] = true;
            player.setWin();
        }
    }

    private Direction getNextDirection(Entity e) {
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
        if (started && !player.isDead() && !player.isWin()) {
            float beforePercent = stepTimer / stepDuration;
            stepTimer += dt;
            float percent = stepTimer / stepDuration;
            if (ghostCollide != null && beforePercent < 0.5f && percent >= 0.5f) {
                doGhostCollide();
            }
            if (stepTimer > stepDuration) {
                stepTimer = 0;
                player.finish();
                player.moveDirection(getNextDirection(player));
                player.decrementSuperStep();
                for (int i = 0; i < collectibles.size(); i++) {
                    Collectible c = collectibles.get(i);
                    if (player.row == c.row && player.col == c.col) {
                        if (c.type == EntityData.EntityType.SUPER_PELLET) {
                            player.setSuper();
                        }
                        collectibles.remove(i);
                        i--;
                        checkComplete();
                    }
                }
                Ghost gc = null;
                for (Ghost g : ghosts) {
                    g.finish();
                    g.moveDirection(getNextDirection(g));
                    g.setSad(player.isSuper());
                    if (player.row == g.row && player.col == g.col) {
                        gc = g;
                    }
                }
                // hacky and too lazy to fix
                if (gc != null) {
                    if (player.isSuper()) {
                        sortedEntities.remove(gc);
                        ghosts.remove(gc);
                        checkComplete();
                    } else {
                        player.setDead();
                    }
                }
                findGhostCollide();
            } else {
                for (Entity g : ghosts) {
                    if (started) g.move(percent);
                }
                player.move(percent);
            }
        }

        player.update(dt);
        if (!player.isDead()) {
            for (Entity g : ghosts) g.update(dt);
            for (Entity c : collectibles) c.update(dt);
            for (Entity a : arrows) a.update(dt);
            sortedEntities.sort(comp);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int type = tiles[row][col] - 1;
                if (type < 0) continue;
                sb.draw(tilesets[(row + col) & 1][type], col * TILE_SIZE, row * TILE_SIZE);
            }
        }
        for (Entity a : arrows) a.render(sb);
        for (Entity c : collectibles) c.render(sb);
        if (!started && cursorRow != -1 && cursorCol != -1) {
            sb.draw(cursor, cursorCol * TILE_SIZE + 1, cursorRow * TILE_SIZE + 1);
        }
        for (Entity e : sortedEntities) e.render(sb);
        if (player.isDead()) player.render(sb);
    }

}
