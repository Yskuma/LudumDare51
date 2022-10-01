package com.livelyspark.ludumdare51.systems.scifi.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.livelyspark.ludumdare51.components.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.entityfactories.PlayerBulletEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class PlayerShootingSciFiSystem extends EntitySystem {

    private final IEntityFactory bulletFactory;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    private boolean lastPressed = false;
    private ImmutableArray<Entity> entities;
    private float lastShot = 0.0f;
    private float shotDelay = 0.3f;


    public PlayerShootingSciFiSystem(IEntityFactory bulletFactory) {
        this.bulletFactory = bulletFactory;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(GenreSciFiComponent.class, PlayerComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        lastShot += deltaTime;

        boolean isPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if(isPressed && lastShot > shotDelay)
        {
            for(Entity e : entities) {
                PositionComponent pos = pm.get(e);

                Entity bullet = bulletFactory.Create(GameGenres.Scifi, pos.x, pos.y);
                getEngine().addEntity(bullet);

                lastShot = 0.0f;
            }
        }
    }

}