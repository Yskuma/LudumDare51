package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.*;
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

public class EnemyEntityFactory implements IEntityFactory {

    TextureAtlas atlas;

    public EnemyEntityFactory(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.EnemyFactory))
                .add(new EnemyComponent())
                .add(new PositionComponent(x, y))
                .add(new VelocityComponent())
                .add(new DebugLabelComponent("Enemy"))
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
        e.remove(EnemyBobberAiComponent.class);

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = -StaticConstants.camSpeed;
        vel.y = 0;

        e.add(new GenreSciFiComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("enemy_scifi"), Animation.PlayMode.LOOP)
        ));
        e.add(new EnemyShooterComponent(2.0f));
        e.add(new EnemyBobberAiComponent(100, 1.5f));

        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(AnimationComponent.class);
        e.remove(EnemyShooterComponent.class);
        e.remove(EnemyBobberAiComponent.class);

        VelocityComponent vel = e.getComponent(VelocityComponent.class);
        vel.x = -StaticConstants.camSpeed;
        vel.y = 0;

        e.add(new GenreFantasyComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("enemy_fantasy"), Animation.PlayMode.LOOP)
        ));
        e.add(new EnemyBobberAiComponent(10, 4.0f));

        return e;
    }

}
