package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;

public class DebugBoundBoxRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    private ShapeRenderer renderer;

    private ComponentMapper<BoundingRectangleComponent> rm = ComponentMapper.getFor(BoundingRectangleComponent.class);

    public DebugBoundBoxRenderSystem(OrthographicCamera camera) {
        renderer = new ShapeRenderer();
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(BoundingRectangleComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        camera.update();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);


        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            BoundingRectangleComponent rectComp = rm.get(e);
            Rectangle rect = rectComp.rectangle;
            renderer.setColor(Color.RED);
            renderer.rect(rect.x,rect.y,rect.width,rect.height);
        }

        renderer.end();
    }
}
