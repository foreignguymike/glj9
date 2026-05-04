package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.glj9.Constants;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.Utils;

public class Player extends Entity {

    private static final float[] IDLE_INTERVAL = new float[] { 0.4f, 0.2f, 0.1f };
    private static final float[] WALK_INTERVAL = new float[] { 1f/20f, 1f/40f, 1f/60f };
    private static final int TOTAL_SUPER_STEPS = 10;

    private final Animation<TextureRegion> animation;
    private final TextureRegion[][][] sheets;
    private final TextureRegion[] idleSprites;
    private final TextureRegion deadImage;

    private int speed;

    private boolean isSuper;
    private int superSteps;

    private boolean isDead;
    private float deadTimer;

    protected Player(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);

        w = 16;
        h = 16;
        sheets = new TextureRegion[][][] {
            context.getImage("playerup").split(w, h),
            context.getImage("playerleft").split(w, h),
            context.getImage("playerdown").split(w, h),
            context.getImage("playerright").split(w, h),
        };
        idleSprites = new TextureRegion[]{sheets[direction.ordinal()][0][0], sheets[direction.ordinal()][0][1]};
        animation = new Animation<>(idleSprites, 0.4f);

        deadImage = context.getImage("playerdead");
    }

    public void redo() {
        started = false;
        isSuper = false;
        superSteps = 0;
        isDead = false;
        deadTimer = 0;
        animation.set(idleSprites, IDLE_INTERVAL[speed - 1]);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        if (!started) animation.set(idleSprites, IDLE_INTERVAL[speed - 1]);
        else animation.set(sheets[direction.ordinal()][1], WALK_INTERVAL[speed - 1]);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        animation.set(sheets[direction.ordinal()][1], 0.05f);
    }

    public int getSuperSteps() {
        return superSteps;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper() {
        isSuper = true;
        superSteps = TOTAL_SUPER_STEPS;
    }

    public void decrementSuperStep() {
        if (!isSuper) return;
        superSteps--;
        if (superSteps <= 0) {
            isSuper = false;
        }
    }

    public void setDead() {
        isDead = true;
        deadTimer = 0;
    }

    @Override
    public void moveDirection(Direction direction) {
        super.moveDirection(direction);
        animation.set(sheets[direction.ordinal()][1], WALK_INTERVAL[speed - 1]);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (transparent) sb.setColor(Constants.TRANSPARENT);
        else sb.setColor(Color.WHITE);
        if (isDead) {
            Utils.drawCentered(sb, deadImage, x, y + 4);
        } else {
            Utils.drawCentered(sb, animation.get(), x, y + 4);
        }
    }
}
