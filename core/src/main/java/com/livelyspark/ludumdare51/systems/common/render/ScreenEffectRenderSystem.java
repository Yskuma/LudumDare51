package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.AnimationComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.ScreenEffectComponent;

public class ScreenEffectRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float stateTime = 0.0f;

    private ComponentMapper<AnimationComponent> sm = ComponentMapper.getFor(AnimationComponent.class);

    public ScreenEffectRenderSystem(OrthographicCamera camera) {
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, ScreenEffectComponent.class).get());
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

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.setColor(1,1,1,0.6f);

        for (int i = 0; i < entities.size(); ++i) {

            Entity e = entities.get(i);

            animation = sm.get(e);

            TextureRegion tex = animation.getCurrentKeyframe();
            batch.draw(tex,0,0);
        }

        batch.end();
    }
}
