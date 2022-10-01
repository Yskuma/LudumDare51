package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.ShapeComponent;
import com.livelyspark.ludumdare51.components.SpriteComponent;

public class ShapeRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    private ShapeRenderer renderer;

    private ComponentMapper<ShapeComponent> sm = ComponentMapper.getFor(ShapeComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ShapeRenderSystem(OrthographicCamera camera) {
        renderer = new ShapeRenderer();

        //TODO: Fix so shapes are sorted by Type and we manually flush between types
        renderer.setAutoShapeType(true);
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ShapeComponent.class, PositionComponent.class).get());
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
        renderer.begin();


        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            ShapeComponent shape = sm.get(e);
            PositionComponent p = pm.get(e);

            renderer.setColor(shape.color);

            switch(shape.shape)
            {
                case SQUARE:
                    renderer.rect(p.x,p.y,shape.size,shape.size);
                    break;
                case CIRCLE:
                    renderer.circle(p.x,p.y,shape.size/2);
                    break;
            }
        }

        renderer.end();
    }
}
