package com.livelyspark.ludumdare51.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.managers.IScreenManager;
import com.livelyspark.ludumdare51.systems.MovementSystem;
import com.livelyspark.ludumdare51.systems.SpritePositionSystem;
import com.livelyspark.ludumdare51.systems.physics.GravitySystem;
import com.livelyspark.ludumdare51.systems.player.PlayerMovementSystem;
import com.livelyspark.ludumdare51.systems.render.SpriteRenderSystem;
import com.livelyspark.ludumdare51.systems.ui.DebugPlayerDetailUiSystem;

public class GameScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;

    public GameScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        stage.act();
        stage.draw();

        if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the main menu screen
            //screenManager.switchScreen(Screens.Level1);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.x = width / 2;
        camera.position.y = height / 2;
        camera.update();

        float midX = (stage.getWidth() / 2);
        float midY = (stage.getHeight() / 2);
    }

    ////948x533
    @Override
    public void show() {
        camera = new OrthographicCamera(948, 533);

        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        addEntities();

        engine.addSystem(new GravitySystem());
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new SpriteRenderSystem(camera));

        //Debug
        engine.addSystem(new DebugPlayerDetailUiSystem());
    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/spritesheet.atlas", TextureAtlas.class);

        engine.addEntity((new Entity())
                .add(new SpriteComponent(new Sprite(atlas.findRegion("player_fantasy"))))
                .add(new PositionComponent(100, 200))
                .add(new VelocityComponent())
                .add(new GravityComponent())
                .add(new PlayerComponent())
                .add(new DebugLabelComponent("Player"))
        );

    }

    @Override
    public void hide() {
    }
}
