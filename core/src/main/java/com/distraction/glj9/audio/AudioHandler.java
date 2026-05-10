package com.distraction.glj9.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioHandler {

    private final Map<String, Music> music;
    private final Map<String, Sound> sounds;

    private final Map<String, MusicConfig> playing;

    public AudioHandler() {
        music = new HashMap<>();
        addMusic("levelselect", "music/levelselect.ogg");
        addMusic("easy", "music/easy.ogg");
        addMusic("tricky", "music/tricky.ogg");

        sounds = new HashMap<>();
        addSound("activate", "sfx/activate.wav");
        addSound("back", "sfx/back.wav");
        addSound("select", "sfx/select.wav");
        addSound("select2", "sfx/selectshort2.wav");
        addSound("select3", "sfx/selectshort.wav");
        addSound("button", "sfx/button.wav");
        addSound("speed", "sfx/speed.wav");
        addSound("dialog", "sfx/dialog.wav");
        addSound("pop", "sfx/pop.wav");

        addSound("reset", "sfx/kenney/back_003.ogg");
        addSound("switch", "sfx/kenney/switch_001.ogg");
        addSound("killghost", "sfx/kenney/minimize_008.ogg");
        addSound("dead", "sfx/kenney/error_006.ogg");
        addSound("power", "sfx/kenney/confirmation_004.ogg");
        addSound("cheer", "sfx/DennisH18/crowd-cheering.wav");

        playing = new HashMap<>();
    }

    private void addMusic(String key, String fileName) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(fileName)));
    }

    private void addSound(String key, String fileName) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(fileName)));
    }

    public void playMusic(String key, float volume, boolean looping) {
        Music newMusic = music.get(key);
        if (newMusic == null) {
            throw new IllegalArgumentException("Unknown music: " + key);
        }
        if (playing.containsKey(key)) {
            playing.get(key).setVolume(volume);
            playing.get(key).play();
        } else {
            MusicConfig musicConfig = new MusicConfig(music.get(key), volume, looping);
            musicConfig.play();
            playing.put(key, musicConfig);
        }
    }

    public void stopMusic() {
        for (Map.Entry<String, MusicConfig> entry : playing.entrySet()) {
            entry.getValue().stop();
        }
    }

    public void stopMusic(String key) {
        if (playing.containsKey(key)) {
            playing.get(key).stop();
        }
    }

    public boolean isPlaying(String key) {
        MusicConfig c = playing.get(key);
        if (c != null) {
            return c.getMusic().isPlaying();
        }
        return false;
    }

    public List<Music> getCurrentlyPlayingList() {
        List<Music> list = new ArrayList<>();
        for (Map.Entry<String, MusicConfig> e : playing.entrySet()) {
            Music m = e.getValue().getMusic();
            if (m.isPlaying()) list.add(m);
        }
        return list;
    }

    public void playSound(String key) {
        playSound(key, 1, false, 1f);
    }

    public void playSound(String key, float volume) {
        playSound(key, volume, false, 1f);
    }

    public void playSound(String key, float volume, float pitch) {
        playSound(key, volume, false, pitch);
    }

    public void playSoundCut(String key, float volume) {
        playSound(key, volume, true, 1f);
    }

    public void playSound(String key, float volume, boolean cut, float pitch) {
        for (Map.Entry<String, Sound> entry : sounds.entrySet()) {
            if (entry.getKey().equals(key)) {
                if (cut) entry.getValue().stop();
                entry.getValue().play(volume, pitch, 0f);
            }
        }
    }

    public void dispose() {
        for (Music m : music.values()) m.dispose();
        for (Sound s : sounds.values()) s.dispose();
    }

}
