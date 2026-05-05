package com.distraction.glj9;

import com.badlogic.gdx.graphics.Color;

public class Constants {

    public static final String TITLE = "TBD";

    public static final int WIDTH = 160;
    public static final int HEIGHT = 90;

    public static final int DWIDTH = 960;
    public static final int DHEIGHT = 540;

    public static final boolean FULLSCREEN = false;

    // tofu 20k
    public static final Color[] COLORS = new Color[]{
        Color.valueOf("120a1a"), // 0
        Color.valueOf("2a2a41"), // 1
        Color.valueOf("404863"), // 2
        Color.valueOf("59607a"), // 3
        Color.valueOf("787d8b"), // 4
        Color.valueOf("9da5ae"), // 5
        Color.valueOf("c8d6ac"), // 6
        Color.valueOf("feffe5"), // 7
        Color.valueOf("e4cd5a"), // 8
        Color.valueOf("d49733"), // 9
        Color.valueOf("d68552"), // 10
        Color.valueOf("be5a1e"), // 11
        Color.valueOf("894835"), // 12
        Color.valueOf("602631"), // 13
        Color.valueOf("4b0c30"), // 14
        Color.valueOf("81173f"), // 15
        Color.valueOf("cc1825"), // 16
        Color.valueOf("dc4926"), // 17
        Color.valueOf("f1934c"), // 18
        Color.valueOf("fad5af"), // 19
        Color.valueOf("ed9d7c"), // 20
        Color.valueOf("d16363"), // 21
        Color.valueOf("b7ab76"), // 22
        Color.valueOf("b59857"), // 23
        Color.valueOf("926d3c"), // 24
        Color.valueOf("8b5b37"), // 25
        Color.valueOf("ff82a0"), // 26
        Color.valueOf("ff26a8"), // 27
        Color.valueOf("422490"), // 28
        Color.valueOf("2749d0"), // 29
        Color.valueOf("4477ff"), // 30
        Color.valueOf("4cc5e4"), // 31
        Color.valueOf("8bf5c6"), // 32
        Color.valueOf("85c448"), // 33
        Color.valueOf("439d40"), // 34
        Color.valueOf("29694e") // 35
    };

    public static final Color LEVEL_SELECT_BG = COLORS[6];
    public static final Color LEVEL_SELECT_TOP = COLORS[3];
    public static final Color DIM_BG = Color.valueOf("00000080");
    public static final Color LEVEL_BG = COLORS[21];

    public static final Color[] HUD_BORDER_COLORS = new Color[] {
        COLORS[19], COLORS[18], COLORS[11]
    };

    public static final Color WHITE = COLORS[7];
    public static final Color PINK = COLORS[26];
    public static final Color TRANSPARENT = new Color(1, 1, 1, 0.5f);
}
