package com.livelyspark.ludumdare51.components.player;

import com.badlogic.ashley.core.Component;

public class PlayerRainbowComponent implements Component {
    public float damage;
    public float charge;
    public float drainTime;
    public boolean isCharged;

    public PlayerRainbowComponent()
    {
        damage = 1f;
        charge = 0f;
        drainTime = 0f;
    }
}
