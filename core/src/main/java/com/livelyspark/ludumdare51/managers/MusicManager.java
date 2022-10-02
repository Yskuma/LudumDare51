package com.livelyspark.ludumdare51.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class MusicManager {
    private final AssetManager assetManager;
    public Music TheMusic;

    Music musicFantasy;
    Music musicSciFi;


    public MusicManager(AssetManager assetManager)
    {
        this.assetManager = assetManager;
    }

    public void LoadMusic()
    {
        musicFantasy = assetManager.get("sounds/music_fantasy.wav", Music.class);
        musicSciFi = assetManager.get("sounds/music_scifi.wav", Music.class);
    }

    public void PickMusic(GameGenres genre){
        StopMusic();

        switch (genre) {
            case Fantasy:
                TheMusic = musicFantasy;
                break;
            case Scifi:
                TheMusic = musicSciFi;
                break;
        }

        TheMusic.setVolume(StaticConstants.musicVolume);
        TheMusic.setLooping(true);
        TheMusic.play();
    }

    public void StopMusic(){
        if(TheMusic != null){
            TheMusic.stop();
        }
    }
}
