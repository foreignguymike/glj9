package com.distraction.glj9.tile;

import static com.distraction.glj9.tile.TileMap.TILE_SIZE;
import static com.distraction.glj9.tile.TileMap.TILE_SIZE_2;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Context;

public class Entity {

    protected Context context;

    protected int row, col, destrow, destcol;
    protected float x, y, startx, starty, destx, desty;
    protected int w, h;
    protected Direction direction;

    protected Entity(Context context, int row, int col, Direction direction) {
        this.context = context;
        this.row = row;
        this.col = col;
        this.direction = direction;

        setTile(row, col);
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

    public void finish() {
        setTile(destrow, destcol);
    }

    public void update(float dt) {

    }

    public void render(SpriteBatch sb) {

    }

}
