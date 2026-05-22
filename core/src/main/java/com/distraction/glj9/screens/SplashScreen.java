package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.utils.Utils;

public class SplashScreen extends Screen {

    private final BitmapFont font;
    private final GlyphLayout layout;

    private final Animation<TextureRegion> player;
    private final Animation<TextureRegion> ghost;
    private final TextureRegion pellet;
    private final TextureRegion tile1;
    private final TextureRegion tile2;

    private float tilex;
    private float pelletx;
    private float ghosty;

    public SplashScreen(Context context) {
        super(context);

        font = context.getFont();
        layout = new GlyphLayout(font, "Click to play", Constants.WHITE, 0, Align.center, false);

        ignoreInput = false;
        out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));

        player = new Animation<>(context.getImage("playerleft").split(16, 16)[1], 0.05f);
        ghost = new Animation<>(context.getImage("ghost").split(16, 16)[0], 0.1f);
        pellet = context.getImage("pellet");
        tile1 = context.getImage("tileset").split(TileMap.TILE_SIZE, TileMap.TILE_SIZE)[6][5];
        tile2 = context.getImage("tileset2").split(TileMap.TILE_SIZE, TileMap.TILE_SIZE)[6][5];
    }

    @Override
    public void input() {
        if (ignoreInput) return;
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            ignoreInput = true;
            out.start();
        }
    }

    @Override
    public void update(float dt) {
        out.update(dt);
        player.update(dt);
        ghost.update(dt);

        ghosty += dt;
        if (ghosty > MathUtils.PI2) ghosty -= MathUtils.PI2;
        pelletx += 30 * dt;
        if (pelletx > 0) pelletx -= 16;
        tilex += 30 * dt;
        if (tilex > 0) tilex -= 32;
    }

    @Override
    public void render() {
        ScreenUtils.clear(Constants.BLACK);
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Color.WHITE);
        font.draw(sb, layout, Constants.WIDTH / 2f, Constants.HEIGHT / 2f + 5);
        for (int i = 0; i < 8; i++) {
            Utils.drawCentered(sb, tile1, tilex + i * 32, 13);
            Utils.drawCentered(sb, tile2, tilex + i * 32 + 16, 13);
        }
        for (int i = 0; i < 6; i++) Utils.drawCentered(sb, pellet, pelletx + i * 16, 13);
        Utils.drawCentered(sb, player.get(), Constants.WIDTH / 2f, 17);
        Utils.drawCentered(sb, ghost.get(), Constants.WIDTH / 2f + 40, 18 + MathUtils.sin(ghosty * 2) * 2);

        out.render(sb);
        sb.end();
    }
}
