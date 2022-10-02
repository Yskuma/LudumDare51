package com.livelyspark.ludumdare51.components.enemy;

import com.badlogic.ashley.core.Component;

public class EnemyBulletComponent implements Component {
    public float damage;

    public EnemyBulletComponent(float damage)
    {
        this.damage = damage;
    }
}
