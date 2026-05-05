package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.HUD;

public class PlayScreen extends Screen {

    private static final float CAMERA_PAD = 4f;
    private static final float CAMERA_SPEED = 60;

    private final TileMap tileMap;
    private final Background bg;
    private final HUD hud;

    private float minX, maxX, minY, maxY;
    private final boolean lockCamera;
    private boolean up, left, down, right;

    public PlayScreen(Context context, int level) {
        super(context);

        tileMap = new TileMap(context, level);

        bg = new Background(context, context.getImage("bg1"), -2, 2, 16, 16);
        hud = new HUD(
            context,
            tileMap,
            tileMap::start,
            this::redo
        );

        lockCamera = tileMap.getWidth() < Constants.WIDTH - hud.getWidth() && tileMap.getHeight() < Constants.HEIGHT;
//        updateCameraPosition(tileMap.player.x + hud.getWidth() / 2f, tileMap.player.y);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        // cam bounds
        float mapW = tileMap.getWidth();
        float mapH = tileMap.getHeight();
        float viewW = cam.viewportWidth;
        float viewH = cam.viewportHeight;
        float hudW = hud.getWidth();
        float playW = viewW - hudW;
        minX = viewW / 2f - CAMERA_PAD;
        maxX = mapW - playW + viewW / 2f + CAMERA_PAD;
        minY = viewH / 2f - CAMERA_PAD;
        maxY = mapH - viewH / 2f + CAMERA_PAD;
        if (maxX < minX) {
            float centerX = mapW / 2f + hudW / 2f;
            minX = maxX = centerX;
        }
        if (maxY < minY) {
            float centerY = mapH / 2f;
            minY = maxY = centerY;
        }

        setCameraPosition(tileMap.player.x + hud.getWidth() / 2f, tileMap.player.y);
    }

    private void setCameraPosition(float x, float y) {
        cam.position.x = MathUtils.clamp(x, minX, maxX);
        cam.position.y = MathUtils.clamp(y, minY, maxY);
        cam.update();
    }

    private void redo() {
        tileMap.redo();
    }

    private void onBack() {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new LevelSelectScreen(context));
        });
        out.start();
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) onBack();

        up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        tileMap.update(dt);
        bg.update(dt);
        hud.update(dt);

        if (!lockCamera) {
            if (tileMap.isStarted()) {
                setCameraPosition(tileMap.player.x + hud.getWidth() / 2f, tileMap.player.y);
            } else {
                float dx = 0f;
                float dy = 0f;
                if (up || left || down || right) {
                    if (up) dy += CAMERA_SPEED * dt;
                    if (left) dx -= CAMERA_SPEED * dt;
                    if (down) dy -= CAMERA_SPEED * dt;
                    if (right) dx += CAMERA_SPEED * dt;
                    setCameraPosition(cam.position.x + dx, cam.position.y + dy);
                }
            }
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.LEVEL_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        bg.render(sb);

        sb.setProjectionMatrix(cam.combined);
        tileMap.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        hud.render(sb);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
