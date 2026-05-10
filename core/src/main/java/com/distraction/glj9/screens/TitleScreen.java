package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;
import com.distraction.glj9.utils.MusicFader;

import java.util.ArrayList;
import java.util.List;

public class TitleScreen extends Screen {

    private final static Interpolation INTERPOLATION = Interpolation.fastSlow;
    private final static float DURATION = 2f;

    private final TextureRegion pixel;
    private final TextureRegion bg;
    private final Vector2 bgp;
    private final TextureRegion title;
    private final Vector2 titlep;

    private final Button[] buttons;

    private final List<MusicFader> musicFaders;

    private float time;

    public TitleScreen(Context context) {
        super(context);

        pixel = context.getPixel();
        bg = context.getImage("titlebg");
        bgp = new Vector2(-200, -200);
        title = context.getImage("title");
        titlep = new Vector2(160, 90);

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

        musicFaders = new ArrayList<>();
        List<Music> currentlyPlaying = context.audio.getCurrentlyPlaying();
        for (Music m : currentlyPlaying) {
            musicFaders.add(new MusicFader(m, 0.5f));
        }
    }

    @Override
    public void resume() {
        ignoreInput = false;
    }

    private void onPlay() {
        context.audio.playSound("select", 0.4f);
        ignoreInput = true;
        out.setCallback(() -> context.sm.replace(new LevelSelectScreen(context)));
        out.start();
    }

    private void onSettings() {
        context.audio.playSound("select2", 0.2f);
        ignoreInput = true;
        SettingsScreen s = new SettingsScreen(context);
        s.transparent = true;
        context.sm.push(s);
    }

    private void onHelp() {
        context.audio.playSound("select", 0.4f);
        ignoreInput = true;
        out.setCallback(() -> context.sm.replace(new TutorialScreen1(context)));
        out.start();
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
        for (MusicFader m : musicFaders) m.update(dt);

        time += dt;
        time = MathUtils.clamp(time + dt, 0, DURATION);
        float a = INTERPOLATION.apply(time / DURATION);
        float f = 170 * (1 - a);

        bgp.set(a * 200 - 200, a * 200 - 200);
        titlep.set(300 - a * 220, 290 - a * 250);
        buttons[0].x = 105 + f;
        buttons[0].y = 25 - f;
        buttons[1].x = 125 + f;
        buttons[1].y = 20 - f;
        buttons[2].x = 145 + f;
        buttons[2].y = 15 - f;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.SKY);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Color.WHITE);
        sb.draw(bg, bgp.x, bgp.y);
        sb.draw(title, titlep.x, titlep.y);
        for (Button b : buttons) b.render(sb);

        in.render(sb);
        out.render(sb);
        sb.end();
    }

}
