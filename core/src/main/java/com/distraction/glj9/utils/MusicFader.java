package com.distraction.glj9.utils;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicFader {

    private final List<Music> music;
    private final List<Float> startVolumes;
    private final float duration;
    private final float endVolume;
    private final SimpleCallback callback;

    private float timer;

    public MusicFader(List<Music> music, float duration) {
        this(music, duration, null);
    }

    public MusicFader(List<Music> music, float duration, SimpleCallback callback) {
        this.music = music;
        this.duration = duration;
        this.startVolumes = new ArrayList<>();
        for (Music m : music) startVolumes.add(m.getVolume());
        this.endVolume = 0;
        this.callback = callback;
    }

    public void update(float dt) {
        if (timer > duration) return;

        timer += dt;
        float progress = Math.min(timer / duration, 1);
        for (int i = 0; i < music.size(); i++) {
            Music m = music.get(i);
            float startVolume = startVolumes.get(i);
            float volume = Math.max(startVolume + (endVolume - startVolume) * progress, 0);
            m.setVolume(volume);
            if (volume <= 0) m.stop();
        }

        if (timer > duration) {
            if (callback != null) callback.callback();
        }
    }
}
