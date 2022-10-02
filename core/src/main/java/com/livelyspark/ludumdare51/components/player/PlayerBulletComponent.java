package com.livelyspark.ludumdare51.components.player;

import com.badlogic.ashley.core.Component;

public class PlayerBulletComponent implements Component {
    public float damage;

    public PlayerBulletComponent(float damage)
    {
        this.damage = damage;
    }
}
