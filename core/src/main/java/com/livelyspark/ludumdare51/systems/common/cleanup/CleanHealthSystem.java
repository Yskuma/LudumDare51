package com.livelyspark.ludumdare51.systems.common.cleanup;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.LifespanComponent;

import java.util.ArrayList;

public class CleanHealthSystem extends EntitySystem {

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ImmutableArray<Entity> entities;

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
                destroyed.add(e);
            }
        }

        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
