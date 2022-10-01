package com.livelyspark.ludumdare51.systems.common.gameStage;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;

import java.util.HashMap;

// Spawning stuff goes here
public class GameStage01System extends EntitySystem {

    private final HashMap<EntityFactories, IEntityFactory> factoryMap;
    private final GlobalGameState gameState;
    float stageTime = 0.0f;

    float enemyLast = 1.5f;
    float enemyThreshold = 2.0f;

    private ImmutableArray<Entity> entities;

    public GameStage01System(GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap)
    {
        this.gameState = gameState;
        this.factoryMap = factoryMap;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        IEntityFactory playerFactory = factoryMap.get(EntityFactories.PlayerFactory);
        Entity player = playerFactory.Create(gameState.gameGenre, 100, 400);
        getEngine().addEntity(player);
    }

    @Override
    public void update (float deltaTime) {
        stageTime += deltaTime;

        enemyLast += deltaTime;

        if(enemyLast > enemyThreshold)
        {
            IEntityFactory enemyFactory = factoryMap.get(EntityFactories.EnemyFactory);

            float y =  MathUtils.random() * 500;

            Entity enemy = enemyFactory.Create(gameState.gameGenre, 1000, y);
            getEngine().addEntity(enemy);

            enemyLast = 0.0f;
        }


    }

}
