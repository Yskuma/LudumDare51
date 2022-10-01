package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyFlappyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiRTypeComponent;
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
                .add(new DebugLabelComponent("Player"));

        return ConvertGenre(e, gameGenre);
    }

    @Override
    public Entity ConvertGenre(Entity e, GameGenres gameGenre)
    {
        switch (gameGenre)
        {
            case ScifiRType:
                return ToSciFi(e);
            case FantasyFlappy:
                return ToFantasy(e);
        }

        return e;
    }

    public Entity ToSciFi(Entity e)
    {
        e.remove(GenreFantasyFlappyComponent.class);
        e.remove(GravityComponent.class);
        e.remove(SpriteComponent.class);

        e.add(new GenreSciFiRTypeComponent());
        e.add(new SpriteComponent(new Sprite(atlas.findRegion("player_scifi"))));

        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiRTypeComponent.class);
        e.remove(SpriteComponent.class);

        e.add(new GenreFantasyFlappyComponent());
        e.add(new GravityComponent());
        e.add(new SpriteComponent(new Sprite(atlas.findRegion("player_fantasy"))));

        return e;
    }

}
