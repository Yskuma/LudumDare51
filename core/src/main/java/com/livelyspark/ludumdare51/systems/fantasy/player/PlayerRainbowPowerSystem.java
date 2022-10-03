package com.livelyspark.ludumdare51.systems.fantasy.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.player.PlayerRainbowComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class PlayerRainbowPowerSystem extends EntitySystem {

    private final IEntityFactory rainbowFactory;
    private Sound rainbowCannonChargeSound;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PlayerRainbowComponent> rm = ComponentMapper.getFor(PlayerRainbowComponent.class);

    private GlobalGameState gameState;

    private float drainMultiplier = 2.0f;
    private float drainThreshold = 0.1f;
    private boolean lastPressed = false;
    private ImmutableArray<Entity> entities;
    private float lastShot = 0.0f;
    private float shotDelay = 0.1f;

    private float rainbowShotDelay = 0.02f;
    private float rainbowTime = 0f;
    private final float sprayRange = 7.5f;

    private int rainbowCount = 0;

    public PlayerRainbowPowerSystem(IEntityFactory rainbowFactory, GlobalGameState gameState, AssetManager assetManager) {
        this.rainbowFactory = rainbowFactory;
        this.gameState = gameState;
        this.rainbowCannonChargeSound = assetManager.get("sounds/pew.ogg", Sound.class);
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, PositionComponent.class, PlayerRainbowComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        lastShot += deltaTime;
        rainbowTime += deltaTime;

        if(gameState.atBoss && gameState.gameGenre == GameGenres.Fantasy){
            boolean isPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT)
                    || Gdx.input.isKeyPressed(Input.Keys.SPACE);

            for(Entity e : entities) {
                PlayerRainbowComponent rainbow = rm.get(e);
                rainbow.drainTime += deltaTime;

                if(isPressed && lastShot > shotDelay && !lastPressed)
                {
                    rainbow.charge += 20f;
                    lastShot = 0.0f;
                }

                if(rainbow.charge >= 100f){
                    rainbow.isCharged = true;
                }

                if(rainbow.drainTime > drainThreshold){
                    rainbow.charge -= drainMultiplier;
                    if(rainbow.charge < 0){
                        rainbow.charge = 0f;
                    }
                    rainbow.drainTime -= drainThreshold;
                }

                if(rainbow.isCharged){
                    rainbow.charge = 100f;


                    if(rainbowTime > rainbowShotDelay){
                        rainbowTime = 0f;
                        rainbowCount++;

                        PositionComponent pc = pm.get(e);
                        Entity rainE = rainbowFactory.Create(gameState.gameGenre, pc.x, pc.y);
                        VelocityComponent v = vm.get(rainE);

                        v.setAngleDeg(SawVal(sprayRange, rainbowCount));

                        getEngine().addEntity(rainE);
                    }
                }
            }
            lastPressed = isPressed;
        }
        else {
            for(Entity e : entities) {
                PlayerRainbowComponent rainbow = rm.get(e);
                rainbow.drainTime = 0f;
                rainbow.charge = 0f;
                rainbow.isCharged = false;
            }
        }
    }

    private float SawVal(float amp, float x)
    {
        float inner = ((x - (amp/4)) % amp ) - (amp/2);
        return  (4 * Math.abs(inner)) - amp;
    }

}
