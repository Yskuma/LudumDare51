package com.livelyspark.ludumdare51.systems.common.cleanup;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.BossComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;

import java.util.ArrayList;
import java.util.HashMap;

public class CleanHealthSystem extends EntitySystem {

    private final HashMap<EntityFactories, IEntityFactory> factoryMap;
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<FactoryComponent> fm = ComponentMapper.getFor(FactoryComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> entities;
    GlobalGameState gameState;

    public CleanHealthSystem(GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap){
        this.factoryMap = factoryMap;
        this.gameState = gameState;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : entities) {
            HealthComponent hc = hm.get(e);

            if (hc.currentHealth <= 0) {

                PositionComponent pos = pm.get(e);
                FactoryComponent fact = fm.get(e);

                if(pos != null && fact != null)
                {
                    IEntityFactory factDeath = null;
                    switch (fact.entityFactory)
                    {
                        case EnemyFactory:
                            factDeath = factoryMap.get(EntityFactories.EnemyDeathFactory);
                            break;
                        case BossFactory:
                            factDeath = factoryMap.get(EntityFactories.BossDeathFactory);
                            break;
                        case PlayerFactory:
                            factDeath = factoryMap.get(EntityFactories.PlayerDeathFactory);
                            break;
                        default:
                    }
                    if(factDeath != null) {
                        getEngine().addEntity(factDeath.Create(gameState.gameGenre, pos.x, pos.y));
                    }
                }

                destroyed.add(e);
            }
        }

        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
