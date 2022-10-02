package com.livelyspark.ludumdare51.entityfactories.screeneffects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.genre.ScreenEffectComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class StaticScreenEffectFactory implements IEntityFactory {

    TextureAtlas atlas;

    public StaticScreenEffectFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new ScreenEffectComponent())
                .add(new AnimationComponent(
                        new Animation<TextureRegion>(0.1f, atlas.findRegions("static"), Animation.PlayMode.LOOP)
                ))
                .add(new LifespanComponent(0.5f));

        return ConvertGenre(e, gameGenre);
    }

    @Override
    public Entity ConvertGenre(Entity e, GameGenres gameGenre)
    {
        return e;
    }

}
