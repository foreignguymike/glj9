package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.HUD;

public class PlayScreen extends Screen {

    private final TileMap tileMap;
    private final Background bg;
    private final HUD hud;

    public PlayScreen(Context context, int level) {
        super(context);

        tileMap = new TileMap(context, level);

        bg = new Background(context, context.getImage("bg1"), 3, 3, 16, 16);
        hud = new HUD(
            context,
            tileMap,
            tileMap::start,
            this::redo
        );

        cam.position.x = (Constants.WIDTH - hud.getWidth()) / 2f;
        cam.position.y = tileMap.getHeight() / 2f;
        cam.update();

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);
    }

    private void redo() {
        tileMap.redo();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
        tileMap.onMouseMove(m.x, m.y);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) tileMap.place();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) tileMap.remove();

        uim.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        uiCam.unproject(uim);
        hud.onMouseMove(uim.x, uim.y);

        hud.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        tileMap.update(dt);
        bg.update(dt);
        hud.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.LEVEL_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        bg.render(sb);
        hud.render(sb);

        sb.setProjectionMatrix(cam.combined);
        tileMap.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
