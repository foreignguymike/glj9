package com.distraction.glj9.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.distraction.glj9.Constants;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.MyViewport;

public abstract class Screen {

    protected Context context;

    protected final TextureRegion pixel;

    public boolean transparent = false;

    protected Viewport viewport;
    protected OrthographicCamera cam;
    protected final Vector3 m;
    protected Viewport uiViewport;
    protected OrthographicCamera uiCam;
    protected final Vector3 uim;

    protected SpriteBatch sb;

    protected boolean ignoreInput;

    public Transition in = null;
    public Transition out = null;

    protected Screen(Context context) {
        this.context = context;
        this.sb = context.sb;

        pixel = context.getPixel();

        viewport = new MyViewport(Constants.WIDTH, Constants.HEIGHT);
        cam = (OrthographicCamera) viewport.getCamera();
        uiViewport = new MyViewport(Constants.WIDTH, Constants.HEIGHT);
        uiCam = (OrthographicCamera) uiViewport.getCamera();

        m = new Vector3();
        uim = new Vector3();
    }

    public void resume() {

    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public abstract void input();

    public abstract void update(float dt);

    public abstract void render();

}
