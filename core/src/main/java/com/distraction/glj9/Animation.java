package com.distraction.glj9;

public class Animation<T> {

    private T[] list;
    private float time;
    private float interval;
    private int index;

    public Animation(T[] list, float interval) {
        this.list = list;
        this.interval = interval;
    }

    public void update(float dt) {
        time += dt;
        if (time > interval) {
            time -= interval;
            index++;
            if (index >= list.length) index = 0;
        }
    }

    public T get() {
        return list[index];
    }

    public float getProgress() {
        return time / interval;
    }

}
