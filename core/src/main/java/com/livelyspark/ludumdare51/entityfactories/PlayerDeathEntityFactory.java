package com.livelyspark.ludumdare51.entityfactories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.StaticConstants;
import com.livelyspark.ludumdare51.components.DebugLabelComponent;
import com.livelyspark.ludumdare51.components.FactoryComponent;
import com.livelyspark.ludumdare51.components.LifespanComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiComponent;
import com.livelyspark.ludumdare51.components.physics.GravityComponent;
import com.livelyspark.ludumdare51.components.physics.VelocityComponent;
import com.livelyspark.ludumdare51.components.player.PlayerBulletComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class PlayerDeathEntityFactory implements IEntityFactory {

    private final Sound playerScifiDeathSound;
    private final Sound playerFantasyDeathSound;
    TextureAtlas atlas;

    public PlayerDeathEntityFactory(TextureAtlas atlas, AssetManager assetManager)
    {
        this.atlas = atlas;
        playerScifiDeathSound = assetManager.get("sounds/explosion.ogg", Sound.class);
        playerFantasyDeathSound = assetManager.get("sounds/explosion.ogg", Sound.class);
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y)
    {
        Entity e = new Entity()
                .add(new FactoryComponent(EntityFactories.PlayerDeathFactory))
                .add(new PositionComponent(x, y))
                .add(new DebugLabelComponent("PlayerDeath"));

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

        e.add(new GenreSciFiComponent());
        e.add(new LifespanComponent(0.5f));
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("player_scifi_dead"), Animation.PlayMode.NORMAL)
        ));
        playerScifiDeathSound.play(StaticConstants.sfxVolume);

        return e;
    }

    public Entity ToFantasy(Entity e)
    {
        e.remove(GenreSciFiComponent.class);
        e.remove(LifespanComponent.class);
        e.remove(AnimationComponent.class);
        e.remove(VelocityComponent.class);

        e.add(new GenreFantasyComponent());
        e.add(new GravityComponent());
        e.add(new VelocityComponent());
        e.add(new AnimationComponent(
                new Animation<TextureRegion>(0.033f, atlas.findRegions("player_fantasy_dead"), Animation.PlayMode.NORMAL)
        ));
        playerFantasyDeathSound.play(StaticConstants.sfxVolume);

        return e;
    }



}
