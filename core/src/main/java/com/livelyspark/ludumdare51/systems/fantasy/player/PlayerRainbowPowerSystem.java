package com.livelyspark.ludumdare51.systems.fantasy.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.player.PlayerRainbowComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class PlayerRainbowPowerSystem extends EntitySystem {

    private final IEntityFactory rainbowFactory;
    private Sound charge;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
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

    public PlayerRainbowPowerSystem(IEntityFactory rainbowFactory, GlobalGameState gameState, AssetManager assetManager) {
        this.rainbowFactory = rainbowFactory;
        this.gameState = gameState;
        this.charge = assetManager.get("sounds/pew.wav", Sound.class);
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
                        PositionComponent pc = pm.get(e);
                        getEngine().addEntity(rainbowFactory.Create(gameState.gameGenre, pc.x, pc.y));
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

}
