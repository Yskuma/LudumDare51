package com.livelyspark.ludumdare51.systems.common.gameStage.Events;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.systems.common.gameStage.IGameStageEvent;

public class EnemySpawnEvent implements IGameStageEvent {

    private final IEntityFactory enemyFactory;
    private final GlobalGameState gameState;
    private final float x;
    private final float y;
    private final Engine engine;

    float eventTime;

    @Override
    public float getEventTime() {
        return eventTime;
    }

    @Override
    public void event() {
        Entity e = enemyFactory.Create(gameState.gameGenre, x, y);
        engine.addEntity(e);
    }

    public EnemySpawnEvent(float eventTime, Engine engine, GlobalGameState gameState, IEntityFactory enemyFactory, float x, float y)
    {
        this.eventTime = eventTime;
        this.gameState = gameState;
        this.enemyFactory = enemyFactory;
        this.engine = engine;
        this.x = x;
        this.y = y;
    }
}
