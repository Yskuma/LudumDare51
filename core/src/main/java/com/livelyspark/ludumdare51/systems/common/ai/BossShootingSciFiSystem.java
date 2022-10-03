package com.livelyspark.ludumdare51.systems.common.ai;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.BossComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class BossShootingSciFiSystem extends EntitySystem {

    private final IEntityFactory enemyBulletFactory;
    private final IEntityFactory bossBulletFactory;
    private final Sound bossSmallShootingSound;
    private final Sound bossLargeShootingSound;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ImmutableArray<Entity> entities;

    private float stageTime = 0;
    private int firingMode = 0;
    private float nextFan = 0.0f;
    private int fanCount = 0;
    private float pauseTime;

    public BossShootingSciFiSystem(AssetManager assetManager, IEntityFactory enemyBulletFactory, IEntityFactory bossBulletFactory) {

        this.enemyBulletFactory = enemyBulletFactory;
        this.bossBulletFactory = bossBulletFactory;

        this.bossSmallShootingSound = assetManager.get("sounds/pew.ogg", Sound.class);
        this.bossLargeShootingSound = assetManager.get("sounds/pew.ogg", Sound.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(GenreSciFiComponent.class, BossComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        if (entities.size() < 1) {
            return;
        }

        stageTime += deltaTime;
        PositionComponent pos = pm.get(entities.first());

        switch (firingMode) {
            case 0:
                FiringPause(1.0f);
                break;
            case 1:
                BigBullet(pos);
                break;
            case 2:
                FiringPause(1.0f);
                break;
            case 3:
                BulletFan(pos, 10, 45, 0);
                break;
            case 4:
                FiringPause(1.0f);
                break;
            case 5:
                BigBullet(pos);
                break;
            case 6:
                FiringPause(1.0f);
                break;
            case 7:
                BulletFan(pos, 10, 45, 5);
                break;
            default:
                firingMode = 0;
                break;
        }
    }

    private void BulletFan(PositionComponent pos, float step, float range, float offset) {

        if (fanCount < (range / step)) {
            if (stageTime > nextFan) {
                float start = -(45 / 2) + 180 + offset;
                float act = start + (step * fanCount);

                Entity e = enemyBulletFactory.Create(GameGenres.Scifi, pos.x, pos.y);
                VelocityComponent vel = vm.get(e);
                vel.setAngleDeg(act);
                bossSmallShootingSound.play(StaticConstants.sfxVolume - 0.07f);
                getEngine().addEntity(e);

                fanCount++;
                nextFan = nextFan + 0.05f;
            }

        } else {
            stageTime = 0;
            nextFan = 0;
            fanCount = 0;
            firingMode++;
        }
    }

    private void BigBullet(PositionComponent pos) {
            getEngine().addEntity(bossBulletFactory.Create(GameGenres.Scifi, pos.x, pos.y));
            bossLargeShootingSound.play(StaticConstants.sfxVolume - 0.05f);
            stageTime = 0;
            firingMode++;
    }

    private void FiringPause(float pauseTime) {
        this.pauseTime = pauseTime;

        if (stageTime > this.pauseTime) {
            stageTime = 0;
            firingMode++;
        }
    }


}



