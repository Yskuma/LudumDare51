package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.player.PlayerBulletComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;

import java.util.ArrayList;


public class PlayerBulletHitsEnemySystem extends EntitySystem {

    private final Sound playerBulletHitsEnemySound;
    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerBulletEntities;

    public PlayerBulletHitsEnemySystem(AssetManager assetManager) {
        playerBulletHitsEnemySound = assetManager.get("sounds/hit.ogg", Sound.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, BoundingRectangleComponent.class, HealthComponent.class, PositionComponent.class).get());
        playerBulletEntities = engine.getEntitiesFor(Family.all(PlayerBulletComponent.class, BoundingRectangleComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : enemyEntities) {

            BoundingRectangleComponent er = rm.get(e);

            for (Entity p : playerBulletEntities) {

                BoundingRectangleComponent pr = rm.get(p);

                if (pr.rectangle.overlaps(er.rectangle)) {
                    HealthComponent h = hm.get(e);
                    h.currentHealth -= 50.0f;

                    destroyed.add(p);
                }
            }
        }

        for(Entity e : destroyed)
        {
            playerBulletHitsEnemySound.play(StaticConstants.sfxVolume);
            getEngine().removeEntity(e);
        }

    }

}
