package com.livelyspark.ludumdare51.systems.common.camera;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.enums.GameGenres;


public class CameraMovementSystem extends EntitySystem {


    float speed = 100;
    private final OrthographicCamera camera;

    public CameraMovementSystem(OrthographicCamera camera)
    {
        this.camera = camera;
    }

    @Override
    public void update (float deltaTime) {
        camera.translate(new Vector3(speed,0,0).scl(deltaTime));
    }

}
