package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;

public class SpriteRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float stateTime = 0.0f;

    private ComponentMapper<AnimationComponent> sm = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public SpriteRenderSystem (OrthographicCamera camera) {
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        stateTime += deltaTime;

        AnimationComponent animation;
        PositionComponent pos;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {

            Entity e = entities.get(i);

            animation = sm.get(e);
            pos = pm.get(e);

            TextureRegion tex = animation.getCurrentKeyframe();
            batch.draw(tex,
                    pos.x - (tex.getRegionWidth()/2),
                    pos.y- (tex.getRegionHeight()/2));
        }

        batch.end();
    }
}
