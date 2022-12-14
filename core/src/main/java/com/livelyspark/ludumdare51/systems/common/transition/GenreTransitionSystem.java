package com.livelyspark.ludumdare51.systems.common.transition;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

import java.util.HashMap;


public class GenreTransitionSystem extends EntitySystem {
    GlobalGameState gameState;
    Sound staticSound;
    //float timeInThisGenre = 0.0f;
    HashMap<EntityFactories, IEntityFactory> factoryMap;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<FactoryComponent> fm = ComponentMapper.getFor(FactoryComponent.class);

    public GenreTransitionSystem(GlobalGameState gameState, HashMap<EntityFactories, IEntityFactory> factoryMap, AssetManager assetManager) {
        this.gameState = gameState;
        this.factoryMap = factoryMap;
        this.staticSound = assetManager.get("sounds/static.ogg", Sound.class);
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(FactoryComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        gameState.timeInGenre += deltaTime;

        if (gameState.timeInGenre > 10) {
            switch (gameState.gameGenre) {
                case Fantasy:
                    gameState.gameGenre = GameGenres.Scifi;
                    break;
                case Scifi:
                    gameState.gameGenre = GameGenres.Fantasy;
                    break;
            }

            IEntityFactory screenEffFact = factoryMap.get(EntityFactories.StaticScreenEffectFactory);
            staticSound.play(StaticConstants.sfxVolume + 0.1f);

            getEngine().addEntity(screenEffFact.Create(gameState.gameGenre,0,0));

            for(Entity e : entities)
            {
                FactoryComponent fc = fm.get(e);
                IEntityFactory fact = factoryMap.get(fc.entityFactory);

                if(fact != null)
                {
                    fact.ConvertGenre(e, gameState.gameGenre);
                }
            }

            gameState.timeInGenre = 0.0f;
        }

    }

}
