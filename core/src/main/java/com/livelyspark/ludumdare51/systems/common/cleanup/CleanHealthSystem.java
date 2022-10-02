package com.livelyspark.ludumdare51.systems.common.cleanup;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.LifespanComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;

import java.util.ArrayList;

public class CleanHealthSystem extends EntitySystem {

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ImmutableArray<Entity> entities;

    IEntityFactory deathAnimationFactory;
    GlobalGameState gameState;

    public CleanHealthSystem(IEntityFactory deathAnimationFactory, GlobalGameState gameState){
        this.deathAnimationFactory = deathAnimationFactory;
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
                PositionComponent pc = e.getComponent(PositionComponent.class);
                EnemyComponent ec = e.getComponent(EnemyComponent.class);

                if(pc != null && ec != null){
                    getEngine().addEntity(deathAnimationFactory.Create(gameState.gameGenre, pc.x, pc.y));
                }
                
                destroyed.add(e);
            }
        }

        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
