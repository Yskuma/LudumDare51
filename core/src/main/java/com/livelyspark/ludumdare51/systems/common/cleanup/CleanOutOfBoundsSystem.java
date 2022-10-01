package com.livelyspark.ludumdare51.systems.common.cleanup;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;

import java.util.ArrayList;


public class CleanOutOfBoundsSystem extends EntitySystem {


    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private boolean lastPressed = false;
    private ImmutableArray<Entity> entities;

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();


        for (Entity e : entities) {
            PositionComponent pc = pm.get(e);
            if (pc.x < -200 ||
                    pc.x > 1200 ||
                    pc.y < -200 ||
                    pc.y > 700) {
                destroyed.add(e);
            }
        }


        for (Entity e : destroyed) {
            getEngine().removeEntity(e);
        }

    }

}
