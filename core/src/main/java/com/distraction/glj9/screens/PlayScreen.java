package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.HUD;
import com.distraction.glj9.utils.MusicFader;
import com.distraction.glj9.utils.Utils;

import java.util.List;

public class PlayScreen extends Screen {

    private static final Interpolation CAM_START_INTERPOLATION = Interpolation.fastSlow;

    private static final float CAMERA_PAD = 4f;
    private static final float CAMERA_SPEED = 150;

    private final TileMap tileMap;
    private final Background bg;
    private final HUD hud;

    private boolean starting;
    private float startTime;
    private float startx, starty, endx, endy;

    private float minX, maxX, minY, maxY;
    private final boolean lockCamera;
    private boolean up, left, down, right;

    private final MusicFader musicFader;

    public PlayScreen(Context context, int level) {
        super(context);
        context.page = (level - 1) / 12;

        tileMap = new TileMap(context, level);

        float dx = level <= 24 ? 5 : -5;
        float dy = level <= 12 ? -5 : 5;
        bg = new Background(context, Utils.getBgColor(level), Utils.getBgImageColor(level), context.getImage(Utils.getBgImage(level)), dx, dy, 24, 24);
        hud = new HUD(
            context,
            tileMap,
            this::start,
            this::onBack,
            this::redo,
            this::onNext
        );

        // max grid size for locked cam is 5x7
        lockCamera = tileMap.getWidth() < Constants.WIDTH - hud.getWidth() && tileMap.getHeight() < Constants.HEIGHT;

        ignoreInput = true;
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

        String key = level <= 12 ? "easy" : level <= 24 ? "hard" : "tricky";
        if (!context.audio.isPlaying(key)) {
            List<Music> playing = context.audio.getCurrentlyPlayingList();
            if (!playing.isEmpty()) {
                musicFader = new MusicFader(playing, 1f, () -> {
                    context.audio.playMusic(key, 0.5f, true);
                });
            } else {
                musicFader = null;
                context.audio.playMusic(key, 0.5f, true);
            }
        } else {
            musicFader = null;
            context.audio.playMusic(key, 0.5f, true);
        }
    }

    private void setCameraPosition(float x, float y) {
        cam.position.x = MathUtils.clamp(x, minX, maxX);
        cam.position.y = MathUtils.clamp(y, minY, maxY);
        cam.update();
    }

    private void start() {
        if (lockCamera) {
            tileMap.start();
            return;
        }
        if (starting) return;

        startx = cam.position.x;
        starty = cam.position.y;
        endx = MathUtils.clamp(tileMap.player.x + hud.getWidth() / 2f, minX, maxX);
        endy = MathUtils.clamp(tileMap.player.y, minY, maxY);
        float dist = Math.abs(startx - endx) + Math.abs(starty - endy);

        if (dist < 2) {
            tileMap.start();
        } else {
            starting = true;
            startTime = 0;
        }
    }

    private void redo() {
        tileMap.redo();
    }

    private void onNext() {
        if (musicFader != null) musicFader.setActive(false);
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new PlayScreen(context, tileMap.level + 1));
        });
        out.start();
    }

    private void onBack() {
        if (musicFader != null) musicFader.setActive(false);
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

        up = Utils.anyKeyPressed(Input.Keys.W, Input.Keys.UP);
        left = Utils.anyKeyPressed(Input.Keys.A, Input.Keys.LEFT);
        down = Utils.anyKeyPressed(Input.Keys.S, Input.Keys.DOWN);
        right = Utils.anyKeyPressed(Input.Keys.D, Input.Keys.RIGHT);
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
        if (musicFader != null) musicFader.update(dt);

        tileMap.update(dt);
        bg.update(dt);
        hud.update(dt);
        hud.startDown = starting;

        if (!lockCamera) {
            if (tileMap.isStarted()) {
                setCameraPosition(tileMap.player.x + hud.getWidth() / 2f, tileMap.player.y);
            } else if (starting) {
                startTime += dt;
                setCameraPosition(startx + CAM_START_INTERPOLATION.apply(startTime) * (endx - startx), starty + CAM_START_INTERPOLATION.apply(startTime) * (endy - starty));
                float sx = cam.position.x;
                float sy = cam.position.y;
                float ex = MathUtils.clamp(tileMap.player.x + hud.getWidth() / 2f, minX, maxX);
                float ey = MathUtils.clamp(tileMap.player.y, minY, maxY);
                float dist = Math.abs(sx - ex) + Math.abs(sy - ey);
                if (dist < 0.5f) {
                    tileMap.start();
                    starting = false;
                }
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
