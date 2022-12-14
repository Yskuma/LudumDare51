package com.livelyspark.ludumdare51.systems.scifi.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;

import java.util.ArrayList;


public class PlayerHitsEnemySciFiSystem extends EntitySystem {

    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    private ComponentMapper<FactoryComponent> fm = ComponentMapper.getFor(FactoryComponent.class);

    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerEntities;

    public PlayerHitsEnemySciFiSystem(AssetManager assetManager) {

    }

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, BoundingRectangleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(GenreSciFiComponent.class,PlayerComponent.class, BoundingRectangleComponent.class, HealthComponent.class).get());
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

                    FactoryComponent f = fm.get(e);

                    HealthComponent h = hm.get(p);
                    h.currentHealth -= 50.0f;

                    if(f.entityFactory != null && f.entityFactory == EntityFactories.EnemyFactory) {
                        destroyed.add(e);
                    }
                }
            }
        }

        for(Entity e : destroyed)
        {
            getEngine().removeEntity(e);
        }

    }

}
