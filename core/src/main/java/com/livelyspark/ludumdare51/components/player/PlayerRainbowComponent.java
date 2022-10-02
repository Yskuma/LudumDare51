package com.livelyspark.ludumdare51.components.player;

import com.badlogic.ashley.core.Component;

public class PlayerRainbowComponent implements Component {
    public float damage;
    public float charge;
    public float drainTime;

    public PlayerRainbowComponent(float damage)
    {
        this.damage = damage;
        charge = 0f;
        drainTime = 0f;
    }
}
