package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;

public class LifespanComponent implements Component {
    public float maxLifespan;
    public float currentLifespan = 0.0f;

    public LifespanComponent(float maxLifespan)
    {
        this.maxLifespan = maxLifespan;
    }
}
