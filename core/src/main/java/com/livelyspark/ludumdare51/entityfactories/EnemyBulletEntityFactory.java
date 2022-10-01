package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class EnemyBulletEntityFactory implements IEntityFactory {

    TextureAtlas atlas;

    public EnemyBulletEntityFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.EnemyBulletFactory))
                .add(new PositionComponent(x, y))
                .add(new VelocityComponent());
                //.add(new DebugLabelComponent("EnemyBullet"));

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
        e.remove(SpriteComponent.class);

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = -3 * StaticConstants.camSpeed;
        vel.y = 0;

        e.add(new GenreSciFiComponent());
        e.add(new SpriteComponent(new Sprite(atlas.findRegion("bullet_enemy"))));

        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(SpriteComponent.class);

        e.add(new GenreFantasyComponent());

        return e;
    }

}
