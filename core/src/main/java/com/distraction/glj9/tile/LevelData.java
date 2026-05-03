package com.distraction.glj9.tile;

public class LevelData {

    public int[][] tiles;
    public int[][] collectibles;
    public EntityData[] entityDataList;

    public LevelData(int cols, int[] tiles, int[] collectibles, EntityData[] entityDataList) {
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
    }

    public static LevelData[] levels = new LevelData[] {
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
                1, 1, 1, 1, 1,
                1, 1, 0, 1, 1,
                1, 1, 1, 1, 1,
                1, 1, 1, 1, 1,
            },
            new EntityData[]{
                new EntityData(EntityData.EntityType.PLAYER, 2, 2, Direction.UP)
            }
        )
    };

}
