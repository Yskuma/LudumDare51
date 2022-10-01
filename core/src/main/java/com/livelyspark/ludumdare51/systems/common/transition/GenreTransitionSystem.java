package com.livelyspark.ludumdare51.systems.common.transition;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.entityfactories.PlayerEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

import java.util.HashMap;


public class GenreTransitionSystem extends EntitySystem {
    GlobalGameState gameState;

    //float timeInThisGenre = 0.0f;
    HashMap<EntityFactories, IEntityFactory> factoryMap;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<FactoryComponent> fm = ComponentMapper.getFor(FactoryComponent.class);

    public GenreTransitionSystem(GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap) {
        this.gameState = gameState;
        this.factoryMap = factoryMap;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FactoryComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        gameState.timeInGenre += deltaTime;

        if (gameState.timeInGenre > 10) {
            switch (gameState.gameGenre) {
                case FantasyFlappy:
                    gameState.gameGenre = GameGenres.ScifiRType;
                    break;
                case ScifiRType:
                    gameState.gameGenre = GameGenres.FantasyFlappy;
                    break;
            }

            for(Entity e : entities)
            {
                FactoryComponent fc = fm.get(e);
                IEntityFactory fact = factoryMap.get(fc.entityFactory);

                if(fact != null)
                {
                    fact.ConvertGenre(e, gameState.gameGenre);
                }

            }

            gameState.timeInGenre = 0.0f;
        }

    }

}
