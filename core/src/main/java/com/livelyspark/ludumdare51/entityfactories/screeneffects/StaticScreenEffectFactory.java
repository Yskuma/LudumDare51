package com.livelyspark.ludumdare51.entityfactories.screeneffects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.ScreenEffectComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.entityfactories.IEntityFactory;
import com.livelyspark.ludumdare51.enums.GameGenres;

public class StaticScreenEffectFactory implements IEntityFactory {

    private final AssetManager assetMananger;

    public StaticScreenEffectFactory(AssetManager assetManager) {
        this.assetMananger = assetManager;
    }

    @Override
    public Entity Create(GameGenres gameGenre, float x, float y) {
        Entity e = new Entity()
                .add(new ScreenEffectComponent())
                .add(new AnimationComponent(
                        new Animation(0.1f, new Array<TextureRegion>(new TextureRegion[]
                                {
                                        new TextureRegion(assetMananger.get("textures/static/static_0.png", Texture.class)),
                                        new TextureRegion(assetMananger.get("textures/static/static_1.png", Texture.class)),
                                        new TextureRegion(assetMananger.get("textures/static/static_2.png", Texture.class)),
                                        new TextureRegion(assetMananger.get("textures/static/static_3.png", Texture.class)),
                                        new TextureRegion(assetMananger.get("textures/static/static_4.png", Texture.class))
                                }), Animation.PlayMode.LOOP)
                ))
                .add(new LifespanComponent(0.5f));

        return ConvertGenre(e, gameGenre);
    }

    @Override
    public Entity ConvertGenre(Entity e, GameGenres gameGenre) {
        return e;
    }

}
