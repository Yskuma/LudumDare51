package com.livelyspark.ludumdare51.systems.common.music;

import com.badlogic.ashley.core.EntitySystem;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.enums.GameGenres;
import com.livelyspark.ludumdare51.managers.MusicManager;

// Music stuff goes here
public class MusicSystem extends EntitySystem {

    private final GlobalGameState gameState;
    private final MusicManager musicManager;
    private GameGenres lastGenre;

    public MusicSystem(GlobalGameState gameState, MusicManager musicManager)
    {
        this.gameState = gameState;
        this.musicManager = musicManager;
        lastGenre = gameState.gameGenre;
        musicManager.PickMusic(GameGenres.Fantasy);
    }

    @Override
    public void update (float deltaTime) {
        if(gameState.gameGenre != lastGenre)
        {
            musicManager.PickMusic(gameState.gameGenre);
            lastGenre = gameState.gameGenre;
        }
    }
}
