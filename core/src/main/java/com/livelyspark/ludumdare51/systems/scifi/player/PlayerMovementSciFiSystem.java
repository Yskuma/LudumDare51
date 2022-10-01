package com.livelyspark.ludumdare51.systems.scifi.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.components.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyFlappyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiRTypeComponent;


public class PlayerMovementSciFiSystem extends IteratingSystem {

    private int speed = 100;
    private int min = 0;
    private int max = 500;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private boolean lastPressed = false;

    public PlayerMovementSciFiSystem() {
        super(Family.all(GenreSciFiRTypeComponent.class, PlayerComponent.class, PositionComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        boolean up = Gdx.input.isKeyPressed(Input.Keys.W) ||
                Gdx.input.isKeyPressed(Input.Keys.UP);

        boolean down = Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.DOWN);


        PositionComponent pos = pm.get(entity);
        pos = (PositionComponent) pos.add((new Vector2(100,0)).scl(deltaTime));

        if(up)
        {
            pos = (PositionComponent) pos.add((new Vector2(0,speed)).scl(deltaTime));
        }

        if(down)
        {
            pos = (PositionComponent) pos.add((new Vector2(0,-speed)).scl(deltaTime));
        }

        pos.y = Math.min(pos.y, max);
        pos.y = Math.max(pos.y, 0);

    }

}
