package com.livelyspark.ludumdare51.systems.common.cleanup;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.LifespanComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;

import java.util.ArrayList;

public class CleanLifespanSystem extends EntitySystem {

    private ComponentMapper<LifespanComponent> lm = ComponentMapper.getFor(LifespanComponent.class);
    private ImmutableArray<Entity> entities;

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(LifespanComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : entities) {
            LifespanComponent lc = lm.get(e);

            lc.currentLifespan += deltaTime;
            if (lc.currentLifespan > lc.maxLifespan) {
                destroyed.add(e);
            }
        }

        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
