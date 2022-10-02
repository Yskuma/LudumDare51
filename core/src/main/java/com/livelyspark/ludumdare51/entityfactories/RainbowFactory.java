package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerBulletComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

import java.util.Random;

public class RainbowFactory implements IEntityFactory {

    TextureAtlas atlas;

    public RainbowFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.RainbowFactory))
                .add(new PositionComponent(x, y))
                .add(new VelocityComponent())
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
        e.remove(AnimationComponent.class);
        e.remove(PlayerBulletComponent.class);

        e.add(new GenreSciFiComponent());
        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.add(new GenreFantasyComponent());
        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = 5 * StaticConstants.camSpeed;
        Random rand = new Random();
        vel.y = 150 * (rand.nextFloat() - 0.5f);

        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("rainbow_bullet"), Animation.PlayMode.LOOP)
        ));
        e.add(new PlayerBulletComponent(350.0f));

        return e;
    }

}
