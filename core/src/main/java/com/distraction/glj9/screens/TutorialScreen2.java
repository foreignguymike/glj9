package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.HUD;

public class TutorialScreen2 extends Screen {

    private static final int IN = 0;
    private static final int INTRO_DIALOG = 1;
    private static final int FIRST_PLAY = 2;

    private final TileMap tileMap;
    private final Background bg;
    private final HUD hud;

    private float time;

    private Dialog dialog;

    private int stage = IN;

    public TutorialScreen2(Context context) {
        super(context);

        tileMap = new TileMap(context, -2);

        bg = new Background(context, context.getImage("bgt"), 5, -5, 24, 24);
        hud = new HUD(
            context,
            tileMap,
            this::onStart,
            this::onBack,
            this::redo,
            this::onNext
        );

        cam.position.x = (Constants.WIDTH - hud.getWidth()) / 2f;
        cam.position.y = tileMap.getHeight() / 2f;
        cam.update();

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);
    }

    private void onStart() {
        time = 0;
        tileMap.start();
    }

    private void redo() {
        tileMap.redo();
    }

    private void onNext() {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new TutorialScreen3(context));
        });
        out.start();
    }

    private void onBack() {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new TitleScreen(context));
        });
        out.start();
    }

    @Override
    public void input() {
        if (ignoreInput) return;
        if (dialog != null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                dialog.next();
            }
            return;
        }
        if (stage == IN) return;

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

        bg.update(dt);
        hud.update(dt);

        tileMap.update(dt);

        time += dt;
        if (time > 1 && stage == IN) {
            stage = INTRO_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "If Poko runs into a wall...",
                    "he will try to turn right if he can,",
                    "then try to turn left if he can,",
                    "otherwise he will turn around."
                },
                Constants.WIDTH / 2f - hud.getWidth() / 2f,
                Constants.HEIGHT / 2f,
                100,
                50
            );
            dialog.next();
        }
        if (stage == INTRO_DIALOG && dialog.isDone()) {
            dialog = null;
            stage = FIRST_PLAY;
            time = 0f;
        }

        if (dialog != null) dialog.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(Constants.TUTORIAL_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Color.WHITE);
        bg.render(sb);

        sb.setProjectionMatrix(cam.combined);
        tileMap.render(sb);

        sb.setProjectionMatrix(uiCam.combined);
        hud.render(sb);
        if (dialog != null) dialog.render(sb);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
