package com.livelyspark.ludumdare51.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.enums.Screens;
import com.livelyspark.ludumdare51.managers.IScreenManager;

import static com.livelyspark.ludumdare51.managers.MusicManager.StopMusic;


public class GameOverSystem extends EntitySystem {

    private final IScreenManager screenManager;
    private final Stage stage;
    private final Skin uiSkin;
    private ImmutableArray<Entity> entities;

    private float gameOverTime = 0.0f;
    private float gameOverThreshold = 3.0f;

    public GameOverSystem(IScreenManager screenManager) {
        this.screenManager = screenManager;
        uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();
        Label titleLabel = new Label("Game Over", uiSkin, "title", Color.WHITE);
        stage.addActor(titleLabel);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        if (entities.size() == 0) {
            gameOverTime += deltaTime;
            stage.act();
            stage.draw();
        }

        if (gameOverTime > gameOverThreshold) {
            StopMusic();
            screenManager.switchScreen(Screens.MainMenu);
        }
    }
}
