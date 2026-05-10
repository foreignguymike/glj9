package com.distraction.glj9.audio;

import com.badlogic.gdx.audio.Music;

public class MusicConfig {

    private final Music music;
    private float volume;
    private boolean looping;

    public MusicConfig(Music music, float volume, boolean looping) {
        this.music = music;
        this.volume = volume;
        this.looping = looping;
    }

    public Music getMusic() {
        return music;
    }

    public void mute() {
        music.setVolume(0f);
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void play() {
        music.setVolume(volume);
        music.setLooping(looping);
        music.play();
    }

    public void stop() {
        music.setVolume(1);
        music.setLooping(false);
        music.setOnCompletionListener(null);
        music.stop();
    }
}
