package com.distraction.glj9.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.tile.Button;
import com.distraction.glj9.tile.Toggle;
import com.distraction.glj9.utils.Utils;

public class SettingsScreen extends Screen {

    private final TextureRegion pixel;
    private final TextureRegion border;
    private final TextureRegion pellet;

    private float a;

    private final BitmapFont font;
    private final GlyphLayout pixelPerfectText;
    private final GlyphLayout pixelPerfectText2;

    private final Toggle toggle;
    private final Button backButton;

    private float time;

    public SettingsScreen(Context context) {
        super(context);

        pixel = context.getPixel();
        border = context.getImage("uiborder");
        pellet = context.getImage("superpellet");

        cam.position.x = Constants.WIDTH / 2f;
        cam.position.y = Constants.HEIGHT;
        cam.update();

        ignoreInput = true;

        in = new Transition(
            context,
            Transition.Type.PAN,
            cam,
            new Vector2(Constants.WIDTH / 2f, 2 * Constants.HEIGHT),
            new Vector2(Constants.WIDTH / 2f, Constants.HEIGHT / 2f),
            0.3f,
            () -> ignoreInput = false
        );
        in.start();
        out = new Transition(
            context,
            Transition.Type.PAN,
            cam,
            new Vector2(Constants.WIDTH / 2f, Constants.HEIGHT / 2f),
            new Vector2(Constants.WIDTH / 2f, 2 * Constants.HEIGHT),
            0.3f,
            () -> context.sm.pop()
        );

        font = context.getFont();
        pixelPerfectText = new GlyphLayout(font, "Pixel", Constants.WHITE, 10, Align.left, false);
        pixelPerfectText2 = new GlyphLayout(font, "Perfect", Constants.WHITE, 10, Align.left, false);

        toggle = new Toggle(context, context.pixelPerfect);
        toggle.x = 100;
        toggle.y = 62;
        backButton = new Button(context, context.getImage("backbuttons2").split(16, 16)[0], this::onBack);
        backButton.x = Constants.WIDTH / 2f;
        backButton.y = 24;
    }

    private void onBack() {
        ignoreInput = true;
        out.start();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);
        backButton.onMouseMoved(m.x, m.y);
        backButton.onMousePressed(Gdx.input.isButtonPressed(Input.Buttons.LEFT));
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (toggle.contains(m.x, m.y)) {
                toggle.toggle();
                context.pixelPerfect = toggle.on;
            }
        }
    }

    @Override
    public void update(float dt) {
        time += dt;
        in.update(dt);
        out.update(dt);
        if (in.started() || in.isFinished()) a += 3 * dt;
        if (out.started() || out.isFinished()) a -= 7 * dt;
        a = MathUtils.clamp(a, 0, 0.7f);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(0, 0, 0, a);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.SETTINGS_BG);
        sb.draw(
            pixel,
            Constants.WIDTH / 2f - border.getRegionWidth() / 2f + 2,
            Constants.HEIGHT / 2f - border.getRegionHeight() / 2f + 2.5f,
            border.getRegionWidth() - 4,
            border.getRegionHeight() - 4
        );
        sb.setColor(Color.WHITE);
        Utils.drawCentered(sb, border, Constants.WIDTH / 2f, Constants.HEIGHT / 2f + 0.5f);
        font.draw(sb, pixelPerfectText, 50, 70);
        font.draw(sb, pixelPerfectText2, 50, 60);
        toggle.render(sb);

        Utils.drawCentered(sb, pellet, Constants.WIDTH / 2f + MathUtils.sin(time * 2) * 15, 42);
        backButton.render(sb);

        in.render(sb);
        out.render(sb);
        sb.end();
    }

}
