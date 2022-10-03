package com.livelyspark.ludumdare51.systems.scifi.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;


public class PlayerMovementRestrictionSciFiSystem extends IteratingSystem {

    private final OrthographicCamera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public PlayerMovementRestrictionSciFiSystem(OrthographicCamera camera) {
        super(Family.all(GenreSciFiComponent.class, PlayerComponent.class, PositionComponent.class).get());
        this.camera = camera;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PositionComponent p = pm.get(entity);

        p.y = Math.max(0, p.y);
        p.y = Math.min(camera.viewportHeight, p.y);

        p.x = Math.max(0, p.x);
        p.x = Math.min(camera.viewportWidth, p.x);
    }

}
