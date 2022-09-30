package com.livelyspark.ludumdare51.systems.render;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledRenderSystem extends EntitySystem {

    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer renderer;

    public TiledRenderSystem(OrthogonalTiledMapRenderer renderer, OrthographicCamera camera) {
        super();
        this.camera = camera;
        this.renderer = renderer;
    }

    @Override
    public void update (float deltaTime) {
        renderer.setView(camera);
        renderer.render();
    }

}
