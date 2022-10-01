package com.livelyspark.ludumdare51.systems.common.transition;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class GenreTransitionSystem extends EntitySystem {
    GlobalGameState gameState;

    //float timeInThisGenre = 0.0f;

    public GenreTransitionSystem(GlobalGameState gameState)
    {
        this.gameState = gameState;
    }

    @Override
    public void update (float deltaTime) {
        gameState.timeInGenre += deltaTime;

        if(gameState.timeInGenre > 10)
        {
            switch(gameState.gameGenre)
            {
                case FantasyFlappy:
                    gameState.gameGenre = GameGenres.ScifiRType;
                    break;
                case ScifiRType:
                    gameState.gameGenre = GameGenres.FantasyFlappy;
                    break;
            }

            gameState.timeInGenre = 0.0f;
        }

    }

}
