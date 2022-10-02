package com.livelyspark.ludumdare51.systems.common.ai;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.BossComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyBobberAiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;

public class BossPositioningSystem extends EntitySystem {

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, VelocityComponent.class, BossComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        for (Entity e : entities) {
            PositionComponent pc = pm.get(e);

            if (pc.x < 800) {
                VelocityComponent vc = vm.get(e);
                vc.x = 0;
            }
        }
    }


}
