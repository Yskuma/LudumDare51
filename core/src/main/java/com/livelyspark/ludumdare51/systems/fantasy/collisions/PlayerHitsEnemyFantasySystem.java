package com.livelyspark.ludumdare51.systems.fantasy.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.entityfactories.PlayerDeathEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;

import java.util.ArrayList;
import java.util.HashMap;


public class PlayerHitsEnemyFantasySystem extends EntitySystem {

    private final HashMap<EntityFactories, IEntityFactory> factoryMap;
    private final GlobalGameState gameState;
    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerEntities;

    public PlayerHitsEnemyFantasySystem(AssetManager assetManager, GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap) {
        this.factoryMap = factoryMap;
        this.gameState = gameState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, BoundingRectangleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(GenreFantasyComponent.class,PlayerComponent.class, BoundingRectangleComponent.class).get());
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
                    PositionComponent pos = pm.get(p);
                    if(pos != null) {
                        IEntityFactory fact = factoryMap.get(EntityFactories.PlayerDeathFactory);
                        getEngine().addEntity(fact.Create(gameState.gameGenre, pos.x, pos.y));
                    }

                    destroyed.add(p);
                }
            }
        }

        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
