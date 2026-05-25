package com.distraction.glj9.tile;

import com.distraction.glj9.Constants;

public class LevelData {

    public int[][] tiles;
    public int[][] collectibles;
    public EntityData[] entityDataList;
    public int numArrows;

    public LevelData(int cols, int[] tiles, int[] collectibles, EntityData[] entityDataList, int numArrows) {
        int rows = tiles.length / cols;
        this.tiles = new int[rows][cols];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i / cols][i % cols] = tiles[i];
        }
        this.collectibles = new int[rows][cols];
        for (int i = 0; i < collectibles.length; i++) {
            this.collectibles[i / cols][i % cols] = collectibles[i];
        }
        this.entityDataList = entityDataList;
        this.numArrows = numArrows;
    }

    public static final LevelData[] easy = new LevelData[] {
        new LevelData(
            5,
            new int[] {
                25,4,4,4,21,
                22,33,33,33,12,
                45,30,30,30,41
            },
            new int[] {
                0, 0, 1, 0, 0,
                0, 1, 0, 1, 0,
                0, 0, 1, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 2)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                0,0,8,0,0,
                0,0,42,0,8,
                0,0,42,0,42,
                8,0,13,48,49,
                36,48,49,1,1
            },
            new int[] {
                0, 0, 2, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 4),
                new EntityData(EntityData.EntityType.GHOST, 3, 0, Direction.DOWN)
            },
            1
        ),
        new LevelData(
            3,
            new int[] {
                25,4,21,
                22,33,12,
                45,30,41
            },
            new int[] {
                0, 0, 0,
                0, 0, 0,
                0, 1, 2
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 0),
                new EntityData(EntityData.EntityType.GHOST, 1, 1, Direction.DOWN)
            },
            1
        ),
        new LevelData(
            7,
            new int[] {
                25,4,4,4,4,4,21,
                22,33,33,33,33,33,12,
                45,30,30,30,30,30,41
            },
            new int[] {
                1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 0, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 3)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                25,4,4,4,21,
                45,11,33,27,41,
                2,32,33,34,14,
                25,26,33,23,21,
                45,30,30,30,41
            },
            new int[] {
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                1, 1, 1, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 2, 0, Direction.RIGHT),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                0,2,3,21,
                0,1,22,12,
                25,21,22,12,
                45,46,18,41
            },
            new int[] {
                0, 0, 0, 0,
                0, 0, 1, 1,
                0, 0, 1, 1,
                0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 1),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                29,30,30,28,
                15,4,4,24,
                45,30,30,41
            },
            new int[] {
                0, 0, 2, 0,
                1, 1, 1, 1,
                0, 0, 0, 0,
                0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 3),
                new EntityData(EntityData.EntityType.GHOST, 3, 0, Direction.UP),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                9,48,6,0,
                42,0,42,0,
                36,48,47,14
            },
            new int[] {
                1, 1, 1, 0,
                1, 0, 1, 0,
                0, 0, 2, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 0),
                new EntityData(EntityData.EntityType.GHOST, 2, 3, Direction.LEFT)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                25,21,0,0,0,
                45,46,6,0,0,
                0,0,36,3,21,
                0,0,0,45,41
            },
            new int[] {
                1, 1, 0, 0, 0,
                1, 1, 0, 0, 0,
                0, 0, 0, 1, 1,
                0, 0, 0, 1, 1,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 2)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                2,48,37,48,14,
                0,0,42,0,0,
                2,48,47,48,14
            },
            new int[] {
                0, 0, 0, 0, 2,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 2, 4, Direction.LEFT)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                9,48,48,6,
                15,21,25,24,
                45,46,18,41
            },
            new int[] {
                1, 1, 1, 1,
                1, 0, 0, 1,
                2, 0, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 2),
                new EntityData(EntityData.EntityType.GHOST, 1, 1, Direction.DOWN),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,21,0,0,
                22,23,21,0,
                29,30,31,14,
                36,48,49,0
            },
            new int[] {
                1, 0, 0, 0,
                1, 1, 1, 0,
                1, 1, 1, 1,
                1, 1, 1, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 1)
            },
            1
        )
    };

    public static final LevelData[] hard = new LevelData[] {
        new LevelData(
            5,
            new int[] {
                2,37,48,37,14,
                25,20,4,20,21,
                45,30,30,30,41
            },
            new int[] {
                0, 1, 1, 1, 0,
                1, 1, 0, 1, 1,
                1, 1, 2, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 2),
                new EntityData(EntityData.EntityType.GHOST, 0, 2, Direction.LEFT)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                0,0,8,0,0,
                25,4,20,4,21,
                45,30,40,30,41,
                0,0,44,0,0
            },
            new int[] {
                0, 0, 2, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 0),
                new EntityData(EntityData.EntityType.GHOST, 2, 2, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 3, 2, Direction.DOWN),
            },
            2
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                45,30,40,41,
                0,0,44,0
            },
            new int[] {
                0, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 1, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0)
            },
            2
        ),
        new LevelData(
            4,
            new int[] {
                25,5,3,21,
                22,12,45,28,
                45,19,4,24,
                2,18,30,41
            },
            new int[] {
                0, 0, 0, 1,
                0, 0, 0, 1,
                2, 0, 0, 1,
                1, 0, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 1),
                new EntityData(EntityData.EntityType.GHOST, 0, 2, Direction.DOWN)
            },
            1
        ),
        new LevelData(
            3,
            new int[] {
                25,21,0,
                45,19,21,
                0,45,41
            },
            new int[] {
                0, 0, 0,
                0, 1, 0,
                0, 2, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 2, 2, Direction.LEFT),
            },
            2
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                22,33,33,12,
                29,30,11,12,
                36,48,18,41
            },
            new int[] {
                0, 1, 1, 0,
                1, 0, 0, 1,
                1, 2, 0, 1,
                0, 1, 1, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 3),
                new EntityData(EntityData.EntityType.GHOST, 3, 0, Direction.RIGHT)
            },
            1
        ),
        new LevelData(
            5,
            new int[] {
                2,6,0,9,14,
                0,42,0,42,0,
                0,13,48,35,0,
                0,42,0,42,0,
                0,36,48,49,0
            },
            new int[] {
                0, 1, 0, 1, 0,
                0, 1, 0, 1, 0,
                0, 1, 2, 1, 0,
                0, 1, 0, 1, 0,
                0, 1, 1, 1, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 0, 4, Direction.LEFT)
            },
            2
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                22,33,33,12,
                45,30,30,41
            },
            new int[] {
                1, 1, 1, 1,
                1, 0, 1, 1,
                0, 1, 2, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 1),
                new EntityData(EntityData.EntityType.GHOST, 2, 0, Direction.RIGHT)
            },
            1
        ),
        new LevelData(
            3,
            new int[] {
                25,4,21,
                22,33,12,
                45,30,41
            },
            new int[] {
                0, 0, 0,
                1, 1, 0,
                0, 2, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 2),
                new EntityData(EntityData.EntityType.GHOST, 0, 2, Direction.DOWN),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                0,9,48,6,
                25,16,48,35,
                45,41,25,24,
                0,0,45,41
            },
            new int[] {
                0, 0, 2, 0,
                0, 0, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 0),
                new EntityData(EntityData.EntityType.GHOST, 3, 3, Direction.LEFT)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,4,21,0,
                22,33,12,0,
                45,30,19,21,
                0,0,45,41
            },
            new int[] {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 2,
                0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 1),
                new EntityData(EntityData.EntityType.GHOST, 3, 3, Direction.LEFT)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,21,25,21,
                22,34,18,28,
                45,46,48,49
            },
            new int[] {
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 2
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 1, 1, Direction.RIGHT)
            },
            1
        ),
    };

    public static final LevelData[] tricky = new LevelData[] {
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                45,30,40,28,
                0,0,42,42,
                0,0,36,49
            },
            new int[] {
                2, 1, 1, 0,
                0, 1, 1, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 2),
                new EntityData(EntityData.EntityType.GHOST, 1, 0, Direction.UP)
            },
            1
        ),
        new LevelData(
            3,
            new int[] {
                25,4,21,
                22,33,12,
                22,33,12,
                45,30,41
            },
            new int[] {
                0, 0, 1,
                0, 0, 2,
                0, 1, 0,
                0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 3, 1, Direction.LEFT),
            },
            1
        ),
        new LevelData(
            3,
            new int[] {
                25,4,21,
                22,33,12,
                22,33,12,
                45,30,41
            },
            new int[] {
                0, 0, 0,
                0, 0, 2,
                0, 1, 0,
                1, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 1),
                new EntityData(EntityData.EntityType.GHOST, 1, 1, Direction.LEFT),
                new EntityData(EntityData.EntityType.GHOST, 0, 0, Direction.DOWN)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,21,9,6,
                45,28,42,42,
                25,20,24,42,
                45,30,46,49
            },
            new int[] {
                0, 1, 0, 0,
                0, 2, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 3),
                new EntityData(EntityData.EntityType.GHOST, 3, 3, Direction.UP)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                22,33,27,28,
                22,27,28,42,
                45,41,36,49
            },
            new int[] {
                1, 1, 1, 1,
                1, 0, 1, 1,
                1, 0, 1, 1,
                2, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 1),
                new EntityData(EntityData.EntityType.GHOST, 2, 1, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 3, 1, Direction.DOWN),
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,21,25,21,
                29,46,32,12,
                13,3,26,12,
                44,45,30,41
            },
            new int[] {
                0, 0, 2, 1,
                0, 0, 1, 1,
                0, 1, 1, 1,
                1, 0, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 1),
                new EntityData(EntityData.EntityType.GHOST, 0, 0, Direction.RIGHT)
            },
            2
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                29,30,30,28,
                42,0,0,42,
                36,48,48,49
            },
            new int[] {
                0, 0, 0, 0,
                0, 1, 0, 1,
                0, 0, 0, 0,
                2, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 1),
                new EntityData(EntityData.EntityType.GHOST, 3, 2, Direction.RIGHT)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                9,48,3,21,
                15,5,32,12,
                29,41,22,12,
                36,48,18,41
            },
            new int[] {
                1, 1, 0, 1,
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 2)
            },
            2
        ),
        new LevelData(
            5,
            new int[] {
                25,4,4,4,21,
                45,11,33,33,12,
                25,26,33,27,41,
                22,33,33,23,21,
                45,30,30,30,41
            },
            new int[] {
                1, 1, 0, 1, 1,
                1, 0, 1, 0, 1,
                1, 1, 1, 0, 1,
                1, 0, 1, 0, 0,
                1, 1, 1, 2, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 4, 4),
                new EntityData(EntityData.EntityType.GHOST, 2, 2, Direction.RIGHT)
            },
            3
        ),
        new LevelData(
            5,
            new int[] {
                25,21,2,3,21,
                22,34,48,32,12,
                22,23,4,26,12,
                22,27,30,30,28,
                45,46,48,14,44
            },
            new int[] {
                0, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                2, 1, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 1,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 2),
                new EntityData(EntityData.EntityType.GHOST, 4, 2, Direction.RIGHT)
            },
            2
        ),
        new LevelData(
            5,
            new int[] {
                25,21,25,5,14,
                29,46,10,46,6,
                15,21,42,25,24,
                45,19,20,39,28,
                2,18,30,41,44
            },
            new int[] {
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 2,
                1, 0, 0, 0, 0,
                0, 0, 0, 1, 1,
                0, 0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 0),
                new EntityData(EntityData.EntityType.GHOST, 1, 3, Direction.RIGHT)
            },
            2
        ),
        new LevelData(
            5,
            new int[] {
                9,3,5,48,6,
                42,45,28,25,24,
                15,4,24,22,12,
                22,33,23,26,12,
                45,30,30,30,41
            },
            new int[] {
                0, 0, 0, 0, 0,
                0, 0, 1, 1, 0,
                0, 0, 0, 0, 0,
                0, 0, 1, 2, 0,
                0, 0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 2),
                new EntityData(EntityData.EntityType.GHOST, 2, 1, Direction.LEFT)
            },
            2
        )
    };

    public static LevelData[] secret = new LevelData[] {
        new LevelData(
            11,
            new int[] {
                0,0,9,48,48,48,48,48,6,0,0,
                0,0,13,48,48,48,48,48,35,0,0,
                0,0,42,0,0,0,0,0,42,0,0,
                9,48,17,48,48,37,48,48,17,48,6,
                36,6,13,37,48,47,48,37,35,9,49,
                9,47,49,36,6,0,9,49,36,47,6,
                36,48,48,48,47,48,47,48,48,48,49
            },
            new int[] {
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1,
                1, 1, 1, 2, 1, 0, 1, 2, 1, 1, 1,
                1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 5),
                new EntityData(EntityData.EntityType.GHOST, 4, 4, Direction.LEFT),
                new EntityData(EntityData.EntityType.GHOST, 4, 6, Direction.RIGHT),
            },
            99
        ),
        new LevelData(
            13,
            new int[] {
                9,48,6,0,8,0,0,0,8,0,8,0,8,
                42,0,13,48,17,48,48,48,17,48,17,48,35,
                36,37,49,0,44,0,0,0,44,0,36,37,49,
                0,42,0,0,0,0,0,0,0,0,0,42,0,
                2,17,14,0,0,0,0,0,0,0,2,17,14,
                0,42,0,0,0,0,0,0,0,0,0,42,0,
                25,16,6,0,8,0,0,0,8,0,0,15,21,
                29,41,13,48,17,48,48,48,17,48,6,22,12,
                36,48,49,0,44,0,0,0,44,0,36,18,41
            },
            new int[] {
                1,1,1,0,0,0,0,0,0,0,1,0,1,
                1,0,1,1,1,1,1,1,1,1,1,1,1,
                1,1,1,0,0,0,0,0,0,0,1,1,1,
                0,1,0,0,0,0,0,0,0,0,0,1,0,
                0,1,0,0,0,0,0,0,0,0,0,1,0,
                0,1,0,0,0,0,0,0,0,0,0,1,0,
                1,1,1,0,0,0,0,0,0,0,0,1,1,
                1,1,1,1,1,1,0,1,1,1,1,1,1,
                1,1,1,0,0,0,0,0,0,0,1,1,1,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 7, 6),
                new EntityData(EntityData.EntityType.GHOST, 2, 4, Direction.UP),
                new EntityData(EntityData.EntityType.GHOST, 0, 8, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 6, 4, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 8, 8, Direction.UP),
                new EntityData(EntityData.EntityType.GHOST, 4, 0, Direction.RIGHT),
                new EntityData(EntityData.EntityType.GHOST, 4, 12, Direction.LEFT),
            },
            99
        )
    };

    public static int getLevelCount() {
        return easy.length + hard.length + tricky.length + (Constants.SECRET_UNLOCKED ? secret.length : 0);
    }
    public static LevelData[] levels;
    static {
        levels = new LevelData[easy.length + hard.length + tricky.length + secret.length];
        int count = 0;
        for (LevelData levelData : easy) levels[count++] = levelData;
        for (LevelData levelData : hard) levels[count++] = levelData;
        for (LevelData levelData : tricky) levels[count++] = levelData;
        for (LevelData levelData : secret) levels[count++] = levelData;
    }

    public static LevelData[] tutorials = new LevelData[] {
        new LevelData(
            5,
            new int[] {
                9,48,6,0,0,
                42,0,42,0,8,
                36,48,47,3,24,
                0,0,0,45,41
            },
            new int[] {
                1, 1, 1, 0, 0,
                1, 0, 0, 0, 1,
                1, 1, 1, 0, 0,
                0, 0, 0, 0, 0,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 2)
            },
            1
        ),
        new LevelData(
            7,
            new int[] {
                2,48,37,48,37,48,14,
                0,0,36,48,49,0,0
            },
            new int[] {
                0, 0, 0, 0, 0, 0, 0,
                0, 0, 1, 2, 1, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 0, 0),
                new EntityData(EntityData.EntityType.GHOST, 0, 6, Direction.LEFT),
            },
            1
        ),
    };

}
