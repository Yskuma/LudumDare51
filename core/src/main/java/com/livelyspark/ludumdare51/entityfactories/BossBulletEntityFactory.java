package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyBulletComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class BossBulletEntityFactory implements IEntityFactory {

    TextureAtlas atlas;

    public BossBulletEntityFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.BossBulletFactory))
                .add(new PositionComponent(x, y))
                .add(new BoundingRectangleComponent());

                e.addAndReturn(new VelocityComponent()).add(-4 * StaticConstants.camSpeed,0);

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

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = -4 * StaticConstants.camSpeed;
        vel.y = 0;

        e.add(new GenreSciFiComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("bullet_boss"), Animation.PlayMode.LOOP)
        ));
        e.add(new EnemyBulletComponent(50.0f));


        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(AnimationComponent.class);
        e.remove(EnemyBulletComponent.class);

        e.add(new GenreFantasyComponent());

        return e;
    }

}
