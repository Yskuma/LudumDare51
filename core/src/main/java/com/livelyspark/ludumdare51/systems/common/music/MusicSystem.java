package com.livelyspark.ludumdare51.systems.common.music;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.enums.GameGenres;

// Music stuff goes here
public class MusicSystem extends EntitySystem {

    private final GlobalGameState gameState;
    private GameGenres lastGenre;
    private Music music;

    public MusicSystem(GlobalGameState gameState)
    {
        this.gameState = gameState;
        lastGenre = gameState.gameGenre;
        PickMusic(GameGenres.Fantasy);
    }

    @Override
    public void update (float deltaTime) {
        if(gameState.gameGenre != lastGenre)
        {
            PickMusic(gameState.gameGenre);
            lastGenre = gameState.gameGenre;
        }
    }

    private void PickMusic(GameGenres genre){

        if(music != null){
            music.stop();
        }

        switch (genre) {
            case Fantasy:
                music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Unicorn.wav"));
                break;
            case Scifi:
                music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Scifi.wav"));
                break;
        }

        music.setVolume(StaticConstants.musicVolume);
        music.setLooping(true);
        music.play();
    }

}
