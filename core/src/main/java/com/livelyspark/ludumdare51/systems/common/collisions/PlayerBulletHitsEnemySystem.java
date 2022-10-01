package com.livelyspark.ludumdare51.systems.common.collisions;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;
import java.util.ArrayList;


public class PlayerBulletHitsEnemySystem extends EntitySystem {

    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    private ImmutableArray<Entity> enemyEntities;
    private ImmutableArray<Entity> playerBulletEntities;

    @Override
    public void addedToEngine(Engine engine) {
        enemyEntities = engine.getEntitiesFor(Family.all(EnemyComponent.class, SpriteComponent.class).get());
        playerBulletEntities = engine.getEntitiesFor(Family.all(PlayerBulletComponent.class, SpriteComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        ArrayList<Entity> destroyed = new ArrayList<Entity>();

        for (Entity e : enemyEntities) {

            SpriteComponent es = sm.get(e);
            Rectangle er = es.sprite.getBoundingRectangle();

            for (Entity b : playerBulletEntities) {

                SpriteComponent bs = sm.get(b);
                Rectangle br = bs.sprite.getBoundingRectangle();

                if (br.overlaps(er)) {
                    destroyed.add(e);
                    destroyed.add(b);
                }
            }
        }

        for(Entity e : destroyed)
        {
            getEngine().removeEntity(e);
        }

    }

}
