package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Direction;
import com.distraction.glj9.tile.Pointer;
import com.distraction.glj9.tile.TileMap;
import com.distraction.glj9.utils.Background;
import com.distraction.glj9.utils.HUD;

public class TutorialScreen1 extends Screen {

    private static final int IN = 0;
    private static final int INTRO_DIALOG = 1;
    private static final int FIRST_PLAY = 2;
    private static final int SPEED_DIALOG = 3;
    private static final int SPEED_TIME = 4;
    private static final int WAITING_FOR_RESET_DIALOG = 5;
    private static final int RESET_DIALOG = 6;
    private static final int WAITING_FOR_RESET = 7;
    private static final int AFTER_RESET = 8;
    private static final int ROTATING_DIALOG = 9;
    private static final int ROTATING = 10;
    private static final int AFTER_ROTATING = 11;
    private static final int ARROW_DIALOG = 12;
    private static final int BEATING = 13;

    private final TileMap tileMap;
    private final Background bg;
    private final HUD hud;

    private final Pointer pointer;

    private float time;

    private Dialog dialog;

    private int stage = AFTER_RESET;

    public TutorialScreen1(Context context) {
        super(context);

        tileMap = new TileMap(context, -1);

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

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        pointer = new Pointer(context);

        context.audio.playMusic("poko", 0.5f, true);
    }

    private void onStart() {
        if (stage == FIRST_PLAY) {
            time = 0;
            tileMap.start();
            pointer.hide();
        } else if (stage == BEATING) {
            tileMap.start();
        }
    }

    private void redo() {
        if (stage == WAITING_FOR_RESET) {
            stage = AFTER_RESET;
            time = 0;
            tileMap.redo();
            pointer.hide();
        } else if (stage == BEATING) {
            tileMap.redo();
        }
    }

    private void onNext() {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> {
            context.sm.replace(new TutorialScreen2(context));
        });
        out.start();
    }

    private void onBack() {
        ignoreInput = true;
        out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> {
            context.sm.replace(new TitleScreen(context));
        });
        out.start();
    }

    @Override
    public void input() {
        if (ignoreInput) return;
        if (dialog != null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                boolean next = dialog.next();
                if (stage == INTRO_DIALOG && next) {
                    if (dialog.getTextIndex() == 3) {
                        pointer.show(Constants.WIDTH - 40, Constants.HEIGHT - 9);
                    } else if (dialog.getTextIndex() == 4) {
                        pointer.show(Constants.WIDTH - 42, 24);
                    }
                }
            }
            return;
        }
        if (stage == IN) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
        tileMap.onMouseMove(m.x, m.y);

        if (stage == ROTATING) {
            Direction previousDirection = tileMap.player.direction;
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) tileMap.rotatePlayer();
            if (tileMap.player.direction == Direction.DOWN && previousDirection != Direction.DOWN) {
                stage = AFTER_ROTATING;
                time = 0;
                pointer.hide();
            }
        }

        if (stage == BEATING) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) tileMap.place();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) tileMap.remove();
        }

        uim.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        uiCam.unproject(uim);
        hud.onMouseMove(uim.x, uim.y);
        int prevSpeed = context.speed;
        hud.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
        if (stage == SPEED_TIME && prevSpeed != 1 && context.speed == 1) {
            pointer.hide();
            stage = WAITING_FOR_RESET_DIALOG;
            time = 0;
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        bg.update(dt);
        hud.update(dt);

        if (stage != SPEED_DIALOG) {
            tileMap.update(dt);
        }

        time += dt;
        if (time > 1 && stage == IN) {
            stage = INTRO_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "How to play",
                    "The goal is to help [POKO]Poko[] eat all the [PELLET]pellets",
                    "and eliminate any [GHOST]Ghosts[] if possible.",
                    "Press the back button to leave any time.",
                    "Press [GREEN]Start[] to begin!"
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
            hud.redoEnabled = false;
            hud.speedEnabled = false;
            time = 0f;
        }
        if (stage == FIRST_PLAY && tileMap.isStarted() && time > 5) {
            stage = SPEED_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "You can press the speed button",
                    "to make the game faster.",
                    "Try cycling through all the speeds!"
                },
                Constants.WIDTH / 2f - hud.getWidth() / 2f,
                Constants.HEIGHT / 2f,
                100,
                50
            );
            dialog.next();
            pointer.show(Constants.WIDTH - 42, 11);
        }
        if (stage == SPEED_DIALOG && dialog.isDone()) {
            dialog = null;
            stage = SPEED_TIME;
            hud.speedEnabled = true;
        }
        if (stage == WAITING_FOR_RESET_DIALOG && time > 1) {
            stage = RESET_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "If Poko gets stuck, press the reset button.",
                },
                Constants.WIDTH / 2f - hud.getWidth() / 2f,
                Constants.HEIGHT / 2f,
                100,
                50
            );
            dialog.next();
            pointer.show(Constants.WIDTH - 23, Constants.HEIGHT - 9);
        }
        if (stage == RESET_DIALOG && dialog.isDone()) {
            dialog = null;
            stage = WAITING_FOR_RESET;
            hud.redoEnabled = true;
        }
        if (stage == AFTER_RESET && time > 1) {
            stage = ROTATING_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "You can change [POKO]Poko's[] starting direction.",
                    "Click on [POKO]Poko[] to turn him all the way around."
                },
                Constants.WIDTH / 2f - hud.getWidth() / 2f,
                Constants.HEIGHT / 2f,
                100,
                50
            );
            dialog.next();
            hud.redoEnabled = false;
            hud.startEnabled = false;
            hud.speedEnabled = false;
        }
        if (stage == ROTATING_DIALOG && dialog.isDone()) {
            stage = ROTATING;
            dialog = null;
            pointer.show(45, Constants.HEIGHT / 2f + 2);
        }
        if (stage == AFTER_ROTATING && time > 1) {
            stage = ARROW_DIALOG;
            dialog = new Dialog(
                context,
                new String[]{
                    "You can also place arrows on any tile.",
                    "[POKO]Poko[] will always try to follow the arrows.",
                    "Click on a placed arrow to rotate it,",
                    "and right click the arrow to remove it.",
                    "Try to beat this level!"
                },
                Constants.WIDTH / 2f - hud.getWidth() / 2f,
                Constants.HEIGHT / 2f,
                100,
                50
            );
            dialog.next();
        }
        if (stage == ARROW_DIALOG && dialog.isDone()) {
            stage = BEATING;
            dialog = null;
            hud.redoEnabled = true;
            hud.startEnabled = true;
            hud.speedEnabled = true;
        }

        if (dialog != null) dialog.update(dt);
        pointer.update(dt);
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
        pointer.render(sb);
        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
