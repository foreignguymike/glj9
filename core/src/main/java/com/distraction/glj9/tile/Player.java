package com.distraction.glj9.tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.distraction.glj9.Constants;
import com.distraction.glj9.utils.Animation;
import com.distraction.glj9.Context;
import com.distraction.glj9.utils.LauchInterpolation;
import com.distraction.glj9.utils.Utils;

import java.util.Arrays;

public class Player extends Entity {

    private static final Interpolation launchInterpolation = new LauchInterpolation(7);

    private static final float[] IDLE_INTERVAL = new float[] { 0.4f, 0.2f, 0.1f };
    private static final float[] WALK_INTERVAL = new float[] { 1f/20f, 1f/40f, 1f/60f };
    private static final float[] WIN_INTERVAL = new float[] { 1f/10f, 1f/20f, 1f/30f };
    private static final int TOTAL_SUPER_STEPS = 10;

    private final Animation<TextureRegion> animation;
    private final TextureRegion[][][] sheets;
    private final TextureRegion[][] idleSprites;
    private final TextureRegion[][] walkSprites;
    private final TextureRegion[] winSprites;
    private final TextureRegion deadImage;

    private Direction lastStartedDirection;

    private int speed;
    private float[] currentInterval;

    private boolean isSuper;
    private int superSteps;

    private boolean isDead;
    private float deady;
    private float deadTimer;

    private boolean win;

    protected Player(Context context, int row, int col, Direction direction) {
        super(context, row, col, direction);
        lastStartedDirection = direction;

        w = 16;
        h = 16;
        sheets = new TextureRegion[][][] {
            context.getImage("playerup").split(w, h),
            context.getImage("playerleft").split(w, h),
            context.getImage("playerdown").split(w, h),
            context.getImage("playerright").split(w, h),
        };
        idleSprites = new TextureRegion[][] {
            Arrays.copyOfRange(sheets[0][0], 0, 2),
            Arrays.copyOfRange(sheets[1][0], 0, 2),
            Arrays.copyOfRange(sheets[2][0], 0, 2),
            Arrays.copyOfRange(sheets[3][0], 0, 2)
        };
        walkSprites = new TextureRegion[][] {
            sheets[0][1], sheets[1][1], sheets[2][1], sheets[3][1],
        };
        winSprites = context.getImage("playerwin").split(w, h)[0];
        speed = context.speed;
        currentInterval = IDLE_INTERVAL;
        animation = new Animation<>(idleSprites[direction.ordinal()], currentInterval[speed - 1]);

        deadImage = context.getImage("playerdead");
    }

    public void redo() {
        win = false;
        started = false;
        isSuper = false;
        superSteps = 0;
        isDead = false;
        deadTimer = 0;
        direction = lastStartedDirection;
        animation.set(idleSprites[direction.ordinal()], IDLE_INTERVAL[speed - 1]);
        currentInterval = IDLE_INTERVAL;
    }

    public void setLastStartedDirection(Direction direction) {
        lastStartedDirection = direction;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        animation.setInterval(currentInterval[speed - 1]);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        animation.set(walkSprites[direction.ordinal()], 0.05f);
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
        deady = y;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setWin() {
        win = true;
        currentInterval = WIN_INTERVAL;
        animation.set(winSprites, currentInterval[speed - 1]);
    }

    public boolean isWin() {
        return win;
    }

    @Override
    public void start(Direction direction) {
        super.start(direction);
        setLastStartedDirection(direction);
    }

    @Override
    public void rotate() {
        super.rotate();
        setLastStartedDirection(direction);
        currentInterval = IDLE_INTERVAL;
        animation.set(idleSprites[direction.ordinal()], currentInterval[speed - 1]);
    }

    @Override
    public void moveDirection(Direction direction) {
        super.moveDirection(direction);
        currentInterval = WALK_INTERVAL;
        animation.set(walkSprites[direction.ordinal()], currentInterval[speed - 1]);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        if (isDead) {
            deadTimer += dt;
            if (deadTimer >= 1) y = deady + launchInterpolation.apply(deadTimer - 1) * 20;
        }
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
