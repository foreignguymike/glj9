package com.distraction.glj9.utils;

import com.badlogic.gdx.audio.Music;

public class MusicFader {

    private final Music music;
    private final float duration;
    private final float startVolume;
    private final float endVolume;

    private float timer;

    public MusicFader(Music music, float duration) {
        this(music, duration, music.getVolume(), 0);
    }

    public MusicFader(Music music, float duration, float startVolume, float endVolume) {
        this.music = music;
        this.duration = duration;
        this.startVolume = startVolume;
        this.endVolume = endVolume;
    }

    public void update(float dt) {
        if (timer > duration) return;

        timer += dt;
        float progress = Math.min(timer / duration, 1);
        float volume = Math.max(startVolume + (endVolume - startVolume) * progress, 0);
        music.setVolume(volume);
        if (volume <= 0) music.stop();
    }
}
