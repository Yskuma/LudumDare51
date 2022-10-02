package com.livelyspark.ludumdare51.systems.common.ai;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyBobberAiComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyShooterComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class EnemyBobberAiSystem extends EntitySystem {

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<EnemyBobberAiComponent> em = ComponentMapper.getFor(EnemyBobberAiComponent.class);
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(EnemyBobberAiComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        for (Entity e : entities) {
            EnemyBobberAiComponent ai = em.get(e);
            ai.currentCycle += deltaTime;

            if (ai.currentCycle > ai.cycleDuration) {
                ai.speed = ai.speed * -1;
                ai.currentCycle = 0;
            }

            VelocityComponent vel = vm.get(e);
            vel.y = ai.speed;
        }
    }


}
