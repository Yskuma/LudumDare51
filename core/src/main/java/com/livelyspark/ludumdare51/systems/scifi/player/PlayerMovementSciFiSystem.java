package com.livelyspark.ludumdare51.systems.scifi.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;


public class PlayerMovementSciFiSystem extends IteratingSystem {

    private int camspeed = 100;
    private int speed = 200;
    private int min = 0;
    private int max = 500;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    private boolean lastPressed = false;

    public PlayerMovementSciFiSystem() {
        super(Family.all(GenreSciFiComponent.class, PlayerComponent.class, PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        boolean up = Gdx.input.isKeyPressed(Input.Keys.W) ||
                Gdx.input.isKeyPressed(Input.Keys.UP);

        boolean down = Gdx.input.isKeyPressed(Input.Keys.S) ||
                Gdx.input.isKeyPressed(Input.Keys.DOWN);

        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) ||
                Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) ||
                Gdx.input.isKeyPressed(Input.Keys.LEFT);


        VelocityComponent vel = vm.get(entity);

        float y = 0;
        float x = 0;

        if(up)
        {
            y += speed;
        }

        if(down)
        {
            y += -speed;
        }

        if(right)
        {
            x += speed;
        }

        if(left)
        {
            x += -speed;
        }

        vel.set(new Vector2(x,y));

    }

}
