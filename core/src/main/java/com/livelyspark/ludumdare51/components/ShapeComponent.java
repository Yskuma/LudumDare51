package com.livelyspark.ludumdare51.components;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare51.enums.Shapes;

public class ShapeComponent implements Component {

    public Shapes shape;
    public Color color;
    public float size;

    public ShapeComponent(Shapes shape, Color color, float size)
    {
        this.shape = shape;
        this.color = color;
        this.size = size;
    }

}
