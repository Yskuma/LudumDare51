package com.livelyspark.ludumdare51.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class MusicManager {
    public static Music TheMusic;

    public static void PickMusic(GameGenres genre){
        StopMusic();

        switch (genre) {
            case Fantasy:
                TheMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Unicorn.wav"));
                break;
            case Scifi:
                TheMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Scifi.wav"));
                break;
        }

        TheMusic.setVolume(StaticConstants.musicVolume);
        TheMusic.setLooping(true);
        TheMusic.play();
    }

    public static void StopMusic(){
        if(TheMusic != null){
            TheMusic.stop();
        }
    }
}
