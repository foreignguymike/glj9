package com.distraction.glj9;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.distraction.glj9.screens.Dialog;
import com.distraction.glj9.tile.Direction;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Context context;

    private FrameBuffer fbo;
    private TextureRegion region;
    private OrthographicCamera cam;

    private int count;
    private float dialogTime;

    @Override
    public void create() {
        Gdx.input.setCatchKey(Input.Keys.UP, true);
        Gdx.input.setCatchKey(Input.Keys.LEFT, true);
        Gdx.input.setCatchKey(Input.Keys.DOWN, true);
        Gdx.input.setCatchKey(Input.Keys.RIGHT, true);

        Colors.put("GREEN", Constants.GREEN_TEXT_COLOR);
        Colors.put("POKO", Constants.POKO_TEXT_COLOR);
        Colors.put("PELLET", Constants.PELLET_TEXT_COLOR);
        Colors.put("GHOST", Constants.GHOST_TEXT_COLOR);

        context = new Context();

        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Constants.WIDTH, Constants.HEIGHT, false);
        fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        region = new TextureRegion(fbo.getColorBufferTexture());
        region.flip(false, true);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
    }

    @Override
    public void render() {
        checkSequence();
        context.sm.input();
        context.sm.update(Gdx.graphics.getDeltaTime());

        if (context.pixelPerfect) {
            fbo.begin();
        }
        context.sm.render();
        renderDialog();
        if (context.pixelPerfect) {
            fbo.end();
            context.sb.begin();
            context.sb.setColor(Color.WHITE);
            context.sb.setProjectionMatrix(cam.combined);
            context.sb.draw(region, 0, 0);
            context.sb.end();
        }
    }

    private void renderDialog() {
        if (context.dialog == null) return;
        if (!context.dialog.isStarted()) {
            dialogTime = 1.5f;
            context.dialog.next();
        }
        dialogTime -= Gdx.graphics.getDeltaTime();
        if (dialogTime < 0) {
            context.dialog.next();
        }
        context.dialog.update(Gdx.graphics.getDeltaTime());
        context.sb.begin();
        context.sb.setColor(Color.WHITE);
        context.sb.setProjectionMatrix(cam.combined);
        context.dialog.render(context.sb);
        context.sb.end();
        if (context.dialog.isDone()) context.dialog = null;
    }

    @Override
    public void dispose() {
        context.dispose();
        fbo.dispose();
    }

    private void checkSequence() {
        if (Constants.SECRET_UNLOCKED) return;
        if (!Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && Constants.SEQUENCE[count] == Direction.UP
            || Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Constants.SEQUENCE[count] == Direction.LEFT
            || Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Constants.SEQUENCE[count] == Direction.DOWN
            || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Constants.SEQUENCE[count] == Direction.RIGHT) {
            count++;
        } else {
            if (count != 0) context.audio.playSound("back", 0.4f);
            count = 0;
        }
        if (count == Constants.SEQUENCE.length) {
            Constants.SECRET_UNLOCKED = true;
            context.audio.playSound("select", 0.4f);
            context.dialog = new Dialog(context, new String[] { "Secret Unlocked!"}, Constants.WIDTH / 2f, 10f, Constants.WIDTH * 2, 10);
        }
    }
}
