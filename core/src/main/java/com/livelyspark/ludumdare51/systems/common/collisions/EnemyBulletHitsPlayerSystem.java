package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.*;

import java.util.ArrayList;


public class EnemyBulletHitsPlayerSystem extends EntitySystem {

    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);

    private ImmutableArray<Entity> enemyBulletEntities;
    private ImmutableArray<Entity> playerEntities;

    @Override
    public void addedToEngine(Engine engine) {
        enemyBulletEntities = engine.getEntitiesFor(Family.all(EnemyBulletComponent.class, BoundingRectangleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BoundingRectangleComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity p : playerEntities) {

            BoundingRectangleComponent er = rm.get(p);

            for (Entity e : enemyBulletEntities) {

                BoundingRectangleComponent pr = rm.get(e);

                if (pr.rectangle.overlaps(er.rectangle)) {
                    destroyed.add(p);
                    destroyed.add(e);
                }
            }
        }

        for(Entity e : destroyed)
        {
            getEngine().removeEntity(e);
        }

    }

}
