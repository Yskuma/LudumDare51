package com.livelyspark.ludumdare51.systems.scifi.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class PlayerShootingSciFiSystem extends EntitySystem {

    private final IEntityFactory bulletFactory;
    private Sound pew;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    private boolean lastPressed = false;
    private ImmutableArray<Entity> entities;
    private float lastShot = 0.0f;
    private float shotDelay = 0.3f;


    public PlayerShootingSciFiSystem(IEntityFactory bulletFactory) {

        this.bulletFactory = bulletFactory;
        this.pew = Gdx.audio.newSound(Gdx.files.internal("sounds/Pew.wav"));
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
                pew.play(StaticConstants.sfxVolume);
                getEngine().addEntity(bullet);

                lastShot = 0.0f;
            }
        }
    }

}
