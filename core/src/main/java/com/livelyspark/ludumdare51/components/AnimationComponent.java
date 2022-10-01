package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;
    public float timeAlive = 0.0f;
    public TextureRegion getCurrentKeyframe()
    {
        return animation.getKeyFrame(timeAlive);
    }

    public AnimationComponent(Animation<TextureRegion> animation) {
        this.animation = animation;
    }
}
