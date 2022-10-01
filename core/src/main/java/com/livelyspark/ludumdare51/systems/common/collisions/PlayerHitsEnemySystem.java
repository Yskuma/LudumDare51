package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.livelyspark.ludumdare51.components.EnemyComponent;
import com.livelyspark.ludumdare51.components.PlayerBulletComponent;
import com.livelyspark.ludumdare51.components.PlayerComponent;
import com.livelyspark.ludumdare51.components.SpriteComponent;

import java.util.ArrayList;


public class PlayerHitsEnemySystem extends EntitySystem {

    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerEntities;

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, SpriteComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class, SpriteComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : enemyEntities) {

            SpriteComponent es = sm.get(e);
            Rectangle er = es.sprite.getBoundingRectangle();

            for (Entity p : playerEntities) {

                SpriteComponent ps = sm.get(p);
                Rectangle pr = ps.sprite.getBoundingRectangle();

                if (pr.overlaps(er)) {
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
