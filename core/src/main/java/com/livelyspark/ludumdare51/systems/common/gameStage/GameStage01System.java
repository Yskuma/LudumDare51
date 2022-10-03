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
    int bossTime = 3;

    float enemyLast = 1.5f;
    float enemyThreshold = 2.0f;

    private ImmutableArray<Entity> entities;

    private ArrayList<IGameStageEvent> events = new ArrayList<IGameStageEvent>();

    public GameStage01System(GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap)
    {
        this.gameState = gameState;
        this.factoryMap = factoryMap;
        gameState.atBoss = false;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        IEntityFactory playerFactory = factoryMap.get(EntityFactories.PlayerFactory);
        Entity player = playerFactory.Create(gameState.gameGenre, 100, 400);
        getEngine().addEntity(player);

        AddEnemies();

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

        if(events.isEmpty())
        {
            return;
        }

        IGameStageEvent nextEvent = events.get(0);

        if(stageTime > nextEvent.getEventTime())
        {
            nextEvent.event();
            events.remove(0);
        }

        if(stageTime > bossTime && !gameState.atBoss){
            gameState.atBoss = true;
        }
    }

    private void AddEnemies()
    {
        IEntityFactory enemyFactory = factoryMap.get(EntityFactories.EnemyFactory);
        IEntityFactory bossFactory = factoryMap.get(EntityFactories.BossFactory);
/*
        events.add(new EnemySpawnEvent(0, getEngine(), gameState, enemyFactory, 1000, 30));
        events.add(new EnemySpawnEvent(2, getEngine(), gameState, enemyFactory, 1000, 150));
        events.add(new EnemySpawnEvent(4, getEngine(), gameState, enemyFactory, 1000, 270));
        events.add(new EnemySpawnEvent(6, getEngine(), gameState, enemyFactory, 1000, 490));
        events.add(new EnemySpawnEvent(6, getEngine(), gameState, enemyFactory, 1000, 430));
        events.add(new EnemySpawnEvent(6, getEngine(), gameState, enemyFactory, 1000, 370));
        events.add(new EnemySpawnEvent(6, getEngine(), gameState, enemyFactory, 1000, 310));
        events.add(new EnemySpawnEvent(6, getEngine(), gameState, enemyFactory, 1000, 250));
        events.add(new EnemySpawnEvent(10, getEngine(), gameState, enemyFactory, 1000, 390));
        events.add(new EnemySpawnEvent(11, getEngine(), gameState, enemyFactory, 1000, 450));
        events.add(new EnemySpawnEvent(11, getEngine(), gameState, enemyFactory, 1000, 330));
        events.add(new EnemySpawnEvent(12, getEngine(), gameState, enemyFactory, 1000, 420));
        events.add(new EnemySpawnEvent(12, getEngine(), gameState, enemyFactory, 1000, 360));
        events.add(new EnemySpawnEvent(14, getEngine(), gameState, enemyFactory, 1000, 210));
        events.add(new EnemySpawnEvent(15, getEngine(), gameState, enemyFactory, 1000, 270));
        events.add(new EnemySpawnEvent(15, getEngine(), gameState, enemyFactory, 1000, 150));
        events.add(new EnemySpawnEvent(16, getEngine(), gameState, enemyFactory, 1000, 240));
        events.add(new EnemySpawnEvent(16, getEngine(), gameState, enemyFactory, 1000, 180));
        events.add(new EnemySpawnEvent(20, getEngine(), gameState, enemyFactory, 1000, 450));
        events.add(new EnemySpawnEvent(21, getEngine(), gameState, enemyFactory, 1000, 390));
        events.add(new EnemySpawnEvent(23, getEngine(), gameState, enemyFactory, 1000, 90));
        events.add(new EnemySpawnEvent(24, getEngine(), gameState, enemyFactory, 1000, 30));
        events.add(new EnemySpawnEvent(27, getEngine(), gameState, enemyFactory, 1000, 270));
        events.add(new EnemySpawnEvent(28, getEngine(), gameState, enemyFactory, 1000, 210));
        events.add(new EnemySpawnEvent(30, getEngine(), gameState, enemyFactory, 1000, 430));
        events.add(new EnemySpawnEvent(31, getEngine(), gameState, enemyFactory, 1000, 460));
        events.add(new EnemySpawnEvent(31, getEngine(), gameState, enemyFactory, 1000, 400));
        events.add(new EnemySpawnEvent(32, getEngine(), gameState, enemyFactory, 1000, 490));
        events.add(new EnemySpawnEvent(32, getEngine(), gameState, enemyFactory, 1000, 370));
        events.add(new EnemySpawnEvent(33, getEngine(), gameState, enemyFactory, 1000, 250));
        events.add(new EnemySpawnEvent(34, getEngine(), gameState, enemyFactory, 1000, 280));
        events.add(new EnemySpawnEvent(34, getEngine(), gameState, enemyFactory, 1000, 220));
        events.add(new EnemySpawnEvent(35, getEngine(), gameState, enemyFactory, 1000, 310));
        events.add(new EnemySpawnEvent(35, getEngine(), gameState, enemyFactory, 1000, 190));
        events.add(new EnemySpawnEvent(36, getEngine(), gameState, enemyFactory, 1000, 70));
        events.add(new EnemySpawnEvent(37, getEngine(), gameState, enemyFactory, 1000, 100));
        events.add(new EnemySpawnEvent(37, getEngine(), gameState, enemyFactory, 1000, 40));
        events.add(new EnemySpawnEvent(38, getEngine(), gameState, enemyFactory, 1000, 130));
        events.add(new EnemySpawnEvent(38, getEngine(), gameState, enemyFactory, 1000, 10));
        events.add(new EnemySpawnEvent(42, getEngine(), gameState, enemyFactory, 1000, 450));
        events.add(new EnemySpawnEvent(42, getEngine(), gameState, enemyFactory, 1000, 370));
        events.add(new EnemySpawnEvent(45, getEngine(), gameState, enemyFactory, 1000, 330));
        events.add(new EnemySpawnEvent(45, getEngine(), gameState, enemyFactory, 1000, 250));
        events.add(new EnemySpawnEvent(48, getEngine(), gameState, enemyFactory, 1000, 210));
        events.add(new EnemySpawnEvent(48, getEngine(), gameState, enemyFactory, 1000, 130));
*/
        events.add(new EnemySpawnEvent(bossTime, getEngine(), gameState, bossFactory, 1000, 300));
    }

}
