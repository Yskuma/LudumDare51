package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent extends Vector2 implements Component {

    public VelocityComponent (float x, float y) {
        super(x,y);
    }

    public VelocityComponent() {
        super();
    }
}
