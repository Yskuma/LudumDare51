package com.livelyspark.ludumdare51.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.*;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyFlappyComponent;
import com.livelyspark.ludumdare51.components.genre.GenreSciFiRTypeComponent;
import com.livelyspark.ludumdare51.enums.GameGenres;
import com.livelyspark.ludumdare51.managers.IScreenManager;
import com.livelyspark.ludumdare51.systems.common.MovementSystem;
import com.livelyspark.ludumdare51.systems.common.SpritePositionSystem;
import com.livelyspark.ludumdare51.systems.common.camera.CameraMovementSystem;
import com.livelyspark.ludumdare51.systems.common.physics.GravitySystem;
import com.livelyspark.ludumdare51.systems.common.transition.GenreTransitionSystem;
import com.livelyspark.ludumdare51.systems.common.ui.DebugGameGenreUiSystem;
import com.livelyspark.ludumdare51.systems.fantasy.player.PlayerMovementFantasySystem;
import com.livelyspark.ludumdare51.systems.common.render.SpriteRenderSystem;
import com.livelyspark.ludumdare51.systems.common.ui.DebugPlayerDetailUiSystem;
import com.livelyspark.ludumdare51.systems.scifi.player.PlayerMovementSciFiSystem;

public class GameScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;

    public GameScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.x = width / 2;
        camera.position.y = height / 2;
        camera.update();
    }

    ////948x533
    @Override
    public void show() {
        GlobalGameState gameState = new GlobalGameState();

        camera = new OrthographicCamera(948, 533);

        addEntities();

        engine.addSystem(new GenreTransitionSystem(gameState));

        //Player
        engine.addSystem(new PlayerMovementFantasySystem());
        engine.addSystem(new PlayerMovementSciFiSystem());

        //Move
        engine.addSystem(new GravitySystem());
        engine.addSystem(new MovementSystem());

        //Camera
        engine.addSystem(new CameraMovementSystem(camera));

        //Render
        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new SpriteRenderSystem(camera));

        //Debug
        engine.addSystem(new DebugPlayerDetailUiSystem());
        engine.addSystem(new DebugGameGenreUiSystem(gameState));
    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/spritesheet.atlas", TextureAtlas.class);

        engine.addEntity((new Entity())
                //.add(new GenreFantasyFlappyComponent())
                .add(new GenreSciFiRTypeComponent())
                .add(new SpriteComponent(new Sprite(atlas.findRegion("player_fantasy"))))
                .add(new PositionComponent(100, 200))
                .add(new VelocityComponent())
                //.add(new GravityComponent())
                .add(new PlayerComponent())
                .add(new DebugLabelComponent("Player"))
        );

    }

    @Override
    public void hide() {
    }
}
