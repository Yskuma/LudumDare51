package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.GravityComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class PlayerEntityFactory implements IEntityFactory {

    TextureAtlas atlas;

    public PlayerEntityFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.PlayerFactory))
                .add(new PositionComponent(x, y))
                .add(new VelocityComponent())
                .add(new PlayerComponent())
                .add(new DebugLabelComponent("Player"))
                .add(new BoundingRectangleComponent());

        return ConvertGenre(e, gameGenre);
    }

    @Override
    public Entity ConvertGenre(Entity e, GameGenres gameGenre)
    {
        switch (gameGenre)
        {
            case Scifi:
                return ToSciFi(e);
            case Fantasy:
                return ToFantasy(e);
        }

        return e;
    }

    public Entity ToSciFi(Entity e)
    {
        e.remove(GenreFantasyComponent.class);
        e.remove(GravityComponent.class);
        e.remove(AnimationComponent.class);

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.y = 0;
        vel.x = 0;

        e.add(new GenreSciFiComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.1f, atlas.findRegions("player_scifi"), Animation.PlayMode.LOOP)
        ));

        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(AnimationComponent.class);

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.y = 0;
        vel.x = 0;

        e.add(new GenreFantasyComponent());
        e.add(new GravityComponent());
        e.add(new AnimationComponent(
            new Animation<TextureRegion>(0.1f, atlas.findRegions("player_fantasy"), Animation.PlayMode.LOOP)
    ));

        return e;
    }

}
