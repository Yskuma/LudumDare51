package com.livelyspark.ludumdare51.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    public float currentHealth;
    public float maxHealth;

    public HealthComponent(float maxHealth)
    {

        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }
}
