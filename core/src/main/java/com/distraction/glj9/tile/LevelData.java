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


    public static LevelData[] levels = new LevelData[] {
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
                new EntityData(EntityData.EntityType.PLAYER, 1, 2, Direction.DOWN)
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
                new EntityData(EntityData.EntityType.PLAYER, 1, 4, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 3, 0, Direction.RIGHT)
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
                new EntityData(EntityData.EntityType.PLAYER, 0, 1, Direction.DOWN)
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
                new EntityData(EntityData.EntityType.PLAYER, 2, 0, Direction.RIGHT),
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
                new EntityData(EntityData.EntityType.PLAYER, 3, 1, Direction.DOWN)
            },
            1
        ),
        new LevelData(
            4,
            new int[] {
                25,4,4,21,
                22,33,33,12,
                45,30,30,41
            },
            new int[] {
                0, 0, 1, 0,
                0, 0, 0, 0,
                0, 0, 2, 1
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 1, 1, Direction.DOWN),
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
                new EntityData(EntityData.EntityType.PLAYER, 2, 2, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 0, 2, Direction.DOWN),
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
                new EntityData(EntityData.EntityType.PLAYER, 0, 0, Direction.DOWN),
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
                new EntityData(EntityData.EntityType.PLAYER, 3, 1, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 0, 0, Direction.DOWN),
                new EntityData(EntityData.EntityType.GHOST, 1, 1, Direction.LEFT),
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
                new EntityData(EntityData.EntityType.PLAYER, 0, 1, Direction.DOWN),
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
                new EntityData(EntityData.EntityType.PLAYER, 0, 2, Direction.DOWN)
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
                new EntityData(EntityData.EntityType.PLAYER, 4, 4, Direction.LEFT),
                new EntityData(EntityData.EntityType.GHOST, 2, 2, Direction.RIGHT)
            },
            3
        ),
        new LevelData(
            5,
            new int[] {
                9,3,5,3,21,
                13,32,12,29,41,
                42,29,41,36,6,
                13,35,9,6,42,
                36,47,49,36,49
            },
            new int[] {
                1, 1, 1, 1, 1,
                1, 1, 1, 2, 1,
                1, 1, 0, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 2, Direction.UP),
                new EntityData(EntityData.EntityType.GHOST, 4, 1, Direction.UP)
            },
            2
        ),
        new LevelData(
            7,
            new int[] {
                9,48,3,5,48,48,6,
                15,5,32,12,0,0,42,
                29,41,22,12,0,25,24,
                36,48,18,46,48,18,41
            },
            new int[] {
                1, 1, 1, 1, 1, 0, 0,
                1, 1, 1, 2, 1, 0, 0,
                1, 1, 0, 1, 1, 0, 0,
                1, 1, 1, 1, 1, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 2, Direction.UP)
            },
            2
        ),
        new LevelData(
            10,
            new int[] {
                25,5,37,48,48,48,48,48,3,21,
                29,41,42,2,6,0,0,0,45,28,
                42,0,42,0,42,0,0,0,25,24,
                42,0,42,0,42,0,0,0,22,12,
                13,48,47,48,17,48,48,48,32,12,
                42,0,0,0,42,0,0,0,45,28,
                42,0,0,0,42,0,0,0,0,42,
                15,21,0,0,42,0,0,0,0,42,
                22,23,4,4,24,0,0,0,25,24,
                45,30,30,30,46,48,48,48,18,41
            },
            new int[] {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 4, 4, Direction.UP)
            },
            2
        ),

    };

}
