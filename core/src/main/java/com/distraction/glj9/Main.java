package com.distraction.glj9;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private Context context;

    private FrameBuffer fbo;
    private TextureRegion region;
    private OrthographicCamera cam;

    @Override
    public void create() {
        context = new Context();

        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Constants.WIDTH, Constants.HEIGHT, false);
        fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        region = new TextureRegion(fbo.getColorBufferTexture());
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) context.pixelPerfect = !context.pixelPerfect;
        context.sm.input();
        context.sm.update(Gdx.graphics.getDeltaTime());

        if (context.pixelPerfect) {
            fbo.begin();
        }
        context.sm.render();
        if (context.pixelPerfect) {
            fbo.end();
            context.sb.begin();
            context.sb.setColor(Color.WHITE);
            context.sb.setProjectionMatrix(cam.combined);
            context.sb.draw(region, 0, 0);
            context.sb.end();
        }
    }

    @Override
    public void dispose() {
        context.dispose();
        fbo.dispose();
    }
}
