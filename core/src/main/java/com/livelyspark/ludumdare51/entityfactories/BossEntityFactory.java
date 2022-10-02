package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.DebugLabelComponent;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.enemy.BossComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyBobberAiComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyComponent;
import com.livelyspark.ludumdare51.components.enemy.EnemyShooterComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class BossEntityFactory implements IEntityFactory {

    TextureAtlas atlas;

    public BossEntityFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.BossFactory))
                .add(new EnemyComponent())
                .add(new BossComponent())
                .add(new PositionComponent(x, y))
                .add(new VelocityComponent())
                .add(new DebugLabelComponent("Enemy"))
                .add(new BoundingRectangleComponent())
                .add(new HealthComponent(1000.0f));;

                VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = -StaticConstants.camSpeed;
        vel.y = 0;

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
        e.remove(EnemyBobberAiComponent.class);

        e.add(new GenreSciFiComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("boss_scifi"), Animation.PlayMode.LOOP)
        ));
        e.add(new EnemyShooterComponent(2.0f));
        e.add(new EnemyBobberAiComponent(100, 0.5f));


        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(EnemyBobberAiComponent.class);

        e.add(new GenreFantasyComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("boss_fantasy"), Animation.PlayMode.LOOP)
        ));
        e.add(new EnemyBobberAiComponent(10, 4.0f));

        return e;
    }

}
