package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;

import java.util.ArrayList;


public class PlayerHitsEnemySystem extends EntitySystem {

    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);

    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerEntities;

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, BoundingRectangleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BoundingRectangleComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : enemyEntities) {

            BoundingRectangleComponent er = rm.get(e);

            for (Entity p : playerEntities) {

                BoundingRectangleComponent pr = rm.get(p);

                if (pr.rectangle.overlaps(er.rectangle)) {
                    destroyed.add(p);
                }
            }
        }

        for(Entity e : destroyed)
        {
            getEngine().removeEntity(e);
        }

    }

}
