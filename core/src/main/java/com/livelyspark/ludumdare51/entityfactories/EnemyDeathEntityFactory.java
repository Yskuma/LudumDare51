package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class EnemyDeathEntityFactory implements IEntityFactory {

    private final Sound enemyDeathSound;
    TextureAtlas atlas;

    public EnemyDeathEntityFactory(TextureAtlas atlas, AssetManager assetManager)
    {
        this.atlas = atlas;
        enemyDeathSound = assetManager.get("sounds/explosion.ogg", Sound.class);
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.EnemyDeathFactory))
                .add(new PositionComponent(x, y))
                .add(new LifespanComponent(0.133f));

        switch (gameGenre)
        {
            case Scifi:
                return CreateSciFi(e);
            case Fantasy:
                return CreateFantasy(e);
        }

        return e;
    }

    private Entity CreateSciFi(Entity e)
    {
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("enemy_scifi_dead"), Animation.PlayMode.NORMAL)
        ));
        enemyDeathSound.play(StaticConstants.sfxVolume);
        return e;
    }

    private Entity CreateFantasy(Entity e)
    {
        return e;
    }

    @Override
    public Entity ConvertGenre(Entity e, GameGenres gameGenre)
    {
        e.getComponent(LifespanComponent.class).maxLifespan = 0.0f;
        return e;
    }

}
