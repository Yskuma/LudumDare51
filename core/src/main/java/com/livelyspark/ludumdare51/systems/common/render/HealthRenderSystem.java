package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;

public class HealthRenderSystem extends EntitySystem {
    private final TextureAtlas atlas;
    private ImmutableArray<Entity> entities;

    private final NinePatch progressNp;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);

    public HealthRenderSystem(OrthographicCamera camera, TextureAtlas atlas) {
        batch = new SpriteBatch();
        this.camera = camera;
        this.atlas = atlas;
        progressNp = new NinePatch(atlas.findRegion("health_bar"), 2, 2, 2, 2);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HealthComponent.class, BoundingRectangleComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        if (camera == null) {
            return;
        }


        HealthComponent health;
        BoundingRectangleComponent rect;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {

            Entity e = entities.get(i);

            health = hm.get(e);
            rect = rm.get(e);

            if(health.currentHealth < health.maxHealth) {
                progressNp.setColor(Color.RED);
                progressNp.draw(batch, rect.rectangle.x,
                        rect.rectangle.y - 6,
                        rect.rectangle.width * (health.currentHealth / health.maxHealth),
                        6);
            }
        }

        batch.end();
    }
}
