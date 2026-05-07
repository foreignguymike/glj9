package com.distraction.glj9.tile;

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
                new EntityData(EntityData.EntityType.GHOST, 3, 0, Direction.RIGHT)
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
            3,
            new int[] {
                25,4,21,
                22,33,12,
                22,33,12,
                22,33,12,
                22,33,12,
                22,33,12,
                45,30,41
            },
            new int[] {
                1, 1, 1,
                1, 1, 1,
                1, 1, 1,
                1, 0, 1,
                1, 1, 1,
                1, 1, 1,
                1, 1, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 3, 1)
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
                new EntityData(EntityData.EntityType.GHOST, 2, 0, Direction.DOWN),
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
        ),
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
                new EntityData(EntityData.EntityType.GHOST, 0, 2, Direction.DOWN)
            },
            1
        ),
    };

    public static final LevelData[] hard = new LevelData[] {
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
                new EntityData(EntityData.EntityType.GHOST, 2, 2, Direction.DOWN),
            },
            2
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
                new EntityData(EntityData.EntityType.GHOST, 0, 4, Direction.DOWN)
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
    };

    public static final LevelData[] tricky = new LevelData[] {
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
                new EntityData(EntityData.EntityType.GHOST, 3, 1, Direction.DOWN),
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
    };


    public static LevelData[] levels;
    static {
        levels = new LevelData[easy.length + hard.length + tricky.length];
        int count = 0;
        for (LevelData levelData : easy) levels[count++] = levelData;
        for (LevelData levelData : hard) levels[count++] = levelData;
        for (LevelData levelData : tricky) levels[count++] = levelData;
    }

}
