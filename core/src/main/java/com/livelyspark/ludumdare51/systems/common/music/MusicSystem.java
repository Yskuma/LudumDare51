package com.livelyspark.ludumdare51.systems.common.music;

import com.badlogic.ashley.core.EntitySystem;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.enums.GameGenres;

import static com.livelyspark.ludumdare51.managers.MusicManager.PickMusic;

// Music stuff goes here
public class MusicSystem extends EntitySystem {

    private final GlobalGameState gameState;
    private GameGenres lastGenre;

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
}
