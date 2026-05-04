package com.distraction.glj9.utils;

public class Animation<T> {

    private T[] list;
    private float time;
    private float interval;
    private int index;

    public Animation(T[] list, float interval) {
        set(list, interval);
    }

    public void set(T[] list, float interval) {
        this.list = list;
        this.interval = interval;
        time = 0;
        index = 0;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public void update(float dt) {
        if (interval < 0) return;
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
