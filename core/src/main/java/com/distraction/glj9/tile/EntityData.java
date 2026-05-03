package com.distraction.glj9.tile;

public class EntityData {

    public enum EntityType {
        PLAYER,
        GHOST,
        PELLET,
        SUPER_PELLET;
    }

    public final EntityType type;
    public final int row;
    public final int col;
    public final Direction direction;

    public EntityData(EntityType type, int row, int col, Direction direction) {
        this.type = type;
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

}
