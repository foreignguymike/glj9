package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;

public class PlayScreen extends Screen {

    private final TileMap tileMap;

    public PlayScreen(Context context) {
        super(context);

        tileMap = new TileMap(context);
        tileMap.loadLevel(0);
    }

    @Override
    public void input() {
        if (ignoreInput) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) tileMap.start();
    }

    @Override
    public void update(float dt) {
        tileMap.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.LEVEL_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setProjectionMatrix(cam.combined);
        tileMap.render(sb);
        sb.end();
    }

}
