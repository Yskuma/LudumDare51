package com.livelyspark.ludumdare51.systems.common.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.components.GravityComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;

public class GravitySystem extends IteratingSystem {

    private float gravity = -200;
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public GravitySystem() {
        super(Family.all(GravityComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        VelocityComponent vel = vm.get(entity);
        vel.add(new Vector2(0, gravity).scl(deltaTime));
    }
}
