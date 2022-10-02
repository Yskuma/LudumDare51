package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;

public class AnimationKeyframeUpdateSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private float stateTime = 0.0f;

    private ComponentMapper<AnimationComponent> sm = ComponentMapper.getFor(AnimationComponent.class);

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        stateTime += deltaTime;
        AnimationComponent animation;

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            animation = sm.get(e);
            animation.timeAlive += deltaTime;
            //animation.getCurrentKeyframe = animation.animation.getKeyFrame(stateTime);
        }

    }
}
