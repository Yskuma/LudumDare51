package com.livelyspark.ludumdare51.systems.common.physics;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;

public class BoundingRectangleUpdateSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);

    public BoundingRectangleUpdateSystem() {
        super(Family.all(PositionComponent.class, AnimationComponent.class, BoundingRectangleComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        AnimationComponent animation = am.get(entity);
        BoundingRectangleComponent rectangle = rm.get(entity);

        TextureRegion currentTexture = animation.getCurrentKeyframe();
        float width = currentTexture.getRegionWidth();
        float height = currentTexture.getRegionHeight();

        float x = position.x - (width/2);
        float y = position.y - (height/2);

        rectangle.rectangle.x = x;
        rectangle.rectangle.y = y;
        rectangle.rectangle.width = width;
        rectangle.rectangle.height = height;
    }
}
