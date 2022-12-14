package com.livelyspark.ludumdare51.systems.common.ai;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.enemy.EnemyShooterComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class EnemyShootingSciFiSystem extends EntitySystem {

    private final IEntityFactory bulletFactory;

    private Sound enemyShootingSound;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<EnemyShooterComponent> sm = ComponentMapper.getFor(EnemyShooterComponent.class);
    private ImmutableArray<Entity> entities;

    public EnemyShootingSciFiSystem(IEntityFactory bulletFactory, AssetManager assetManager) {

        this.bulletFactory = bulletFactory;
        this.enemyShootingSound = assetManager.get("sounds/pew.ogg", Sound.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(GenreSciFiComponent.class, EnemyShooterComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        for (Entity e : entities) {
            EnemyShooterComponent es = sm.get(e);
            es.shotTime += deltaTime;

            if (es.shotTime > es.shotThreshold) {

                PositionComponent pos = pm.get(e);

                Entity bullet = bulletFactory.Create(GameGenres.Scifi, pos.x, pos.y);
                enemyShootingSound.play(StaticConstants.sfxVolume - 0.07f);
                getEngine().addEntity(bullet);

                es.shotTime = 0;
            }
        }
    }


}
