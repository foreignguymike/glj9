package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;

public class TitleScreen extends Screen {

    private final TextureRegion pixel;
    private final TextureRegion bg;

    private final Button[] buttons;

    public TitleScreen(Context context) {
        super(context);

        pixel = context.getPixel();
        bg = context.getImage("titlescreen");

        buttons = new Button[] {
            new Button(context, context.getImage("playbuttons").split(16, 16)[0], this::onPlay),
            new Button(context, context.getImage("settingsbuttons").split(16, 16)[0], this::onSettings),
            new Button(context, context.getImage("helpbuttons").split(16, 16)[0], this::onHelp),
        };
        buttons[0].x = 105;
        buttons[0].y = 25;
        buttons[1].x = 125;
        buttons[1].y = 20;
        buttons[2].x = 145;
        buttons[2].y = 15;

        in = new Transition(context, Transition.Type.FLASH_IN, 1f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        ignoreInput = true;
    }

    @Override
    public void resume() {
        ignoreInput = false;
    }

    private void onPlay() {
        ignoreInput = true;
        out.setCallback(() -> context.sm.replace(new LevelSelectScreen(context)));
        out.start();
    }

    private void onSettings() {
        ignoreInput = true;
        SettingsScreen s = new SettingsScreen(context);
        s.transparent = true;
        context.sm.push(s);
    }

    private void onHelp() {

    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
        for (Button b : buttons) b.onMouseMoved(m.x, m.y);
        for (Button b : buttons) b.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.SKY);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(bg, 0, 0);
        for (Button b : buttons) b.render(sb);

        in.render(sb);
        out.render(sb);
        sb.end();
    }

}
