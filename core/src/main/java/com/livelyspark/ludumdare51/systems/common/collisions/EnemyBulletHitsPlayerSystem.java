package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyBulletComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;

import java.util.ArrayList;


public class EnemyBulletHitsPlayerSystem extends EntitySystem {

    private final Sound enemyBulletHitsPlayerSound;
    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);
    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

    private ImmutableArray<Entity> enemyBulletEntities;
    private ImmutableArray<Entity> playerEntities;

    public EnemyBulletHitsPlayerSystem(AssetManager assetManager) {
        enemyBulletHitsPlayerSound = assetManager.get("sounds/hit.ogg", Sound.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        enemyBulletEntities = engine.getEntitiesFor(Family.all(EnemyBulletComponent.class, BoundingRectangleComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class, BoundingRectangleComponent.class, HealthComponent.class).get());
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
                    HealthComponent h = hm.get(p);
                    h.currentHealth -= 20.0f;

                    destroyed.add(e);
                }
            }
        }

        for(Entity e : destroyed)
        {
            enemyBulletHitsPlayerSound.play(StaticConstants.sfxVolume);
            getEngine().removeEntity(e);
        }

    }

}
