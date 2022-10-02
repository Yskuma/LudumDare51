package com.livelyspark.ludumdare51.systems.fantasy.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;


public class PlayerMovementFantasySystem extends IteratingSystem {

    private int targetlocx = 100;
    private int fudge = 1;

    private int targetlocy = 300;
    private int speed = 100;
    private int push = 400;
    private int maxVel = 400;

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private GlobalGameState gameState;

    private boolean lastPressed = false;

    public PlayerMovementFantasySystem(GlobalGameState gameState) {
        super(Family.all(GenreFantasyComponent.class, PlayerComponent.class, PositionComponent.class, VelocityComponent.class).get());

        this.gameState = gameState;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        boolean isPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                || Gdx.input.isKeyPressed(Input.Keys.SPACE);

        VelocityComponent vel = vm.get(entity);
        PositionComponent pos = pm.get(entity);

        if(pos.x > targetlocx + fudge)
        {
            vel.x = -speed;
        }
        else if(pos.x < targetlocx - fudge)
        {
            vel.x = speed;
        }
        else
        {
            vel.x = 0;
        }

        if(!gameState.atBoss){

            //vel.x = camspeed;

            if(isPressed && !lastPressed)
            {
                vel.y = Math.min(vel.y + push, maxVel);
            }

            lastPressed = isPressed;
        }
        else{
            if(pos.y > targetlocy + fudge)
            {
                vel.y = -speed;
            }
            else if(pos.y < targetlocy - fudge)
            {
                vel.y = speed;
            }
            else
            {
                vel.y = 0;
            }
        }


    }

}
