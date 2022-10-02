package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShooterComponent implements Component {
    public float shotTime = 0.0f;
    public float shotThreshold;

    public EnemyShooterComponent(float shotThreshold) {
        this.shotThreshold = shotThreshold;
    }
}
