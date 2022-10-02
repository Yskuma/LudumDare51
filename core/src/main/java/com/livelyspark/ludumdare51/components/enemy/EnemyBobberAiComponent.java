package com.livelyspark.ludumdare51.components.enemy;

import com.badlogic.ashley.core.Component;

public class EnemyBobberAiComponent implements Component {

    public float speed;
    public float cycleDuration;
    public float currentCycle;

    public EnemyBobberAiComponent(float speed, float cycleDuration) {
        this.speed = speed;
        this.cycleDuration = cycleDuration;
        this.currentCycle = cycleDuration / 2;
    }
}
