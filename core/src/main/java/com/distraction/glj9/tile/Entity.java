package com.distraction.glj9.tile;

import static com.distraction.glj9.tile.TileMap.TILE_SIZE;
import static com.distraction.glj9.tile.TileMap.TILE_SIZE_2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Context;

public abstract class Entity {

    protected Context context;

    protected int row, col, destrow, destcol;
    public float x;
    public float y;
    protected float startx;
    protected float starty;
    protected float destx;
    protected float desty;
    protected int w, h;
    protected Direction direction;

    protected boolean started = false;

    protected boolean transparent = false;

    protected Entity(Context context) {
        this.context = context;
    }

    protected Entity(Context context, int row, int col, Direction direction) {
        this.context = context;
        this.row = row;
        this.col = col;
        this.direction = direction;

        setTile(row, col);
    }

    public void rotate() {
        if (direction == Direction.UP) direction = Direction.RIGHT;
        else if (direction == Direction.RIGHT) direction = Direction.DOWN;
        else if (direction == Direction.DOWN) direction = Direction.LEFT;
        else direction = Direction.UP;
    }

    public int getWidth() {
        return w;
    }

    public boolean contains(float px, float py) {
        float w2 = w / 2f;
        float h2 = h / 2f;
        return px >= x - w2 &&
            px <= x + w2 &&
            py >= y - h2 &&
            py <= y + h2;
    }

    public void setTile(int row, int col) {
        this.row = this.destrow = row;
        this.col = this.destcol = col;
        this.x = this.startx = col * TILE_SIZE + TILE_SIZE_2;
        this.y = this.starty = row * TILE_SIZE + TILE_SIZE_2;
    }

    public void moveDirection(Direction direction) {
        this.direction = direction;
        if (direction == Direction.UP) destrow = row + 1;
        else if (direction == Direction.LEFT) destcol = col - 1;
        else if (direction == Direction.DOWN) destrow = row - 1;
        else if (direction == Direction.RIGHT) destcol = col + 1;
        startx = x;
        starty = y;
        this.destx = destcol * TILE_SIZE + TILE_SIZE_2;
        this.desty = destrow * TILE_SIZE + TILE_SIZE_2;
    }

    public void move(float percent) {
        x = MathUtils.lerp(startx, destx, percent);
        y = MathUtils.lerp(starty, desty, percent);
    }

    public void start(Direction direction) {
        started = true;
        moveDirection(direction);
    }

    public void finish() {
        setTile(destrow, destcol);
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

}
