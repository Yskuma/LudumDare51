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
import com.livelyspark.ludumdare51.systems.common.gameStage.Events.EnemySpawnEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// Spawning stuff goes here
public class GameStage01System extends EntitySystem {

    private final HashMap<EntityFactories, IEntityFactory> factoryMap;
    private final GlobalGameState gameState;
    float stageTime = 0.0f;

    float enemyLast = 1.5f;
    float enemyThreshold = 2.0f;

    private ImmutableArray<Entity> entities;

    private ArrayList<IGameStageEvent> events = new ArrayList<IGameStageEvent>();

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

        IEntityFactory enemyFactory = factoryMap.get(EntityFactories.EnemyFactory);
        for(int i = 0; i < 180; i++)
        {
            if(i%2 == 0) {
                float y = MathUtils.random() * 500;
                events.add(new EnemySpawnEvent(i, getEngine(), gameState, enemyFactory, 1000, y));
            }
        }

        for(int posy = 0; posy < 600; posy+=100)
        {
            events.add(new EnemySpawnEvent(5, getEngine(), gameState, enemyFactory, 1000, posy));
        }

        events.sort(new Comparator<IGameStageEvent>() {
            @Override
            public int compare(IGameStageEvent o1, IGameStageEvent o2) {
                if(o1.getEventTime() < o2.getEventTime()) return -1;
                if(o1.getEventTime() > o2.getEventTime()) return 1;
                return 0;
            }
        });
    }

    @Override
    public void update (float deltaTime) {
        stageTime += deltaTime;

        IGameStageEvent nextEvent = events.get(0);

        if(nextEvent != null && stageTime > nextEvent.getEventTime())
        {
            nextEvent.event();
            events.remove(0);
        }
    }

}
