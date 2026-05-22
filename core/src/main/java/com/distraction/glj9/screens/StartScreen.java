package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;

public class StartScreen extends Screen {

    private final BitmapFont font;
    private final GlyphLayout layout;

    public StartScreen(Context context) {
        super(context);

        font = context.getFont();
        layout = new GlyphLayout(font, "Click to play", Constants.WHITE, 0, Align.center, false);

        ignoreInput = false;
        out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));
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
    }

    @Override
    public void render() {
        ScreenUtils.clear(Constants.BLACK);
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Color.WHITE);
        font.draw(sb, layout, Constants.WIDTH / 2f, Constants.HEIGHT / 2f + 5);

        out.render(sb);
        sb.end();
    }
}
