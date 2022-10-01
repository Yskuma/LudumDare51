package com.livelyspark.ludumdare51.systems.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import com.livelyspark.ludumdare51.components.GravityComponent;
import com.livelyspark.ludumdare51.components.PlayerComponent;
import com.livelyspark.ludumdare51.components.SpriteComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;


public class PlayerMovementSystem extends IteratingSystem {
    private int push = 100;
    private int maxVel = 500;

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    private boolean lastPressed = false;

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        boolean isPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if(isPressed && !lastPressed)
        {
            VelocityComponent vel = vm.get(entity);
            vel.y = Math.min(vel.y + push, maxVel);
        }

        lastPressed = isPressed;
    }

}
