package com.livelyspark.ludumdare51.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.entityfactories.*;
import com.livelyspark.ludumdare51.entityfactories.screeneffects.StaticScreenEffectFactory;
import com.livelyspark.ludumdare51.enums.EntityFactories;
import com.livelyspark.ludumdare51.managers.IScreenManager;
import com.livelyspark.ludumdare51.systems.GameOverSystem;
import com.livelyspark.ludumdare51.systems.common.MovementSystem;
import com.livelyspark.ludumdare51.systems.common.ai.EnemyBobberAiSystem;
import com.livelyspark.ludumdare51.systems.common.ai.EnemyShootingSciFiSystem;
import com.livelyspark.ludumdare51.systems.common.cleanup.CleanHealthSystem;
import com.livelyspark.ludumdare51.systems.common.cleanup.CleanLifespanSystem;
import com.livelyspark.ludumdare51.systems.common.cleanup.CleanOutOfBoundsSystem;
import com.livelyspark.ludumdare51.systems.common.collisions.EnemyBulletHitsPlayerSystem;
import com.livelyspark.ludumdare51.systems.common.collisions.PlayerBulletHitsEnemySystem;
import com.livelyspark.ludumdare51.systems.common.render.*;
import com.livelyspark.ludumdare51.systems.fantasy.collisions.PlayerHitsEnemyFantasySystem;
import com.livelyspark.ludumdare51.systems.common.gameStage.GameStage01System;
import com.livelyspark.ludumdare51.systems.common.music.MusicSystem;
import com.livelyspark.ludumdare51.systems.common.physics.BoundingRectangleUpdateSystem;
import com.livelyspark.ludumdare51.systems.common.physics.GravitySystem;
import com.livelyspark.ludumdare51.systems.common.transition.GenreTransitionSystem;
import com.livelyspark.ludumdare51.systems.common.ui.DebugGameGenreUiSystem;
import com.livelyspark.ludumdare51.systems.fantasy.player.PlayerMovementFantasySystem;
import com.livelyspark.ludumdare51.systems.scifi.collisions.PlayerHitsEnemySciFiSystem;
import com.livelyspark.ludumdare51.systems.scifi.player.PlayerMovementSciFiSystem;
import com.livelyspark.ludumdare51.systems.scifi.player.PlayerShootingSciFiSystem;

import java.util.HashMap;

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

        TextureAtlas atlas = assetManager.get("textures/spritesheet.atlas", TextureAtlas.class);
        TextureAtlas atlasStatic = assetManager.get("textures/static.atlas", TextureAtlas.class);

        HashMap<EntityFactories, IEntityFactory> factoryMap = createFactoryMap(atlas, atlasStatic);

        //Stage Control
        engine.addSystem(new GameStage01System(gameState, factoryMap));

        //Genre Transition
        engine.addSystem(new GenreTransitionSystem(gameState, factoryMap));

        //Player
        engine.addSystem(new PlayerMovementFantasySystem());
        engine.addSystem(new PlayerMovementSciFiSystem());
        engine.addSystem(new PlayerShootingSciFiSystem(factoryMap.get(EntityFactories.PlayerBulletFactory)));

        //AI (Can we really call it that?!)
        engine.addSystem(new EnemyShootingSciFiSystem(factoryMap.get(EntityFactories.EnemyBulletFactory)));
        engine.addSystem(new EnemyBobberAiSystem());

        //Move
        engine.addSystem(new GravitySystem());
        engine.addSystem(new MovementSystem());

        // Animation Frames & Bounding Boxes
        engine.addSystem(new AnimationKeyframeUpdateSystem());
        engine.addSystem(new BoundingRectangleUpdateSystem());

        //Render
        engine.addSystem(new BackgroundRenderSystem(camera, gameState, assetManager));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new HealthRenderSystem(camera, atlas));

        //Collisions
        engine.addSystem(new PlayerBulletHitsEnemySystem());
        engine.addSystem(new EnemyBulletHitsPlayerSystem());
        engine.addSystem(new PlayerHitsEnemyFantasySystem());
        engine.addSystem(new PlayerHitsEnemySciFiSystem());

        engine.addSystem(new GameOverSystem(screenManager));

        //Debug
        //engine.addSystem(new DebugPlayerDetailUiSystem());
        engine.addSystem(new DebugGameGenreUiSystem(gameState));
        //engine.addSystem(new DebugBoundBoxRenderSystem(camera));

        //Screen Effects
        engine.addSystem(new ScreenEffectRenderSystem(camera));

        //Cleanup
        engine.addSystem(new CleanOutOfBoundsSystem());
        engine.addSystem(new CleanLifespanSystem());
        engine.addSystem(new CleanHealthSystem());

        //Music
        engine.addSystem(new MusicSystem(gameState));
    }

    private HashMap<EntityFactories, IEntityFactory> createFactoryMap(TextureAtlas atlas, TextureAtlas atlasStatic)
    {
        HashMap<EntityFactories, IEntityFactory> factoryMap = new HashMap<EntityFactories, IEntityFactory>();

        IEntityFactory playerFactory = new PlayerEntityFactory(atlas);
        factoryMap.put(EntityFactories.PlayerFactory, playerFactory);

        IEntityFactory enemyFactory = new EnemyEntityFactory(atlas);
        factoryMap.put(EntityFactories.EnemyFactory, enemyFactory);

        IEntityFactory playerBulletFactory = new PlayerBulletEntityFactory(atlas);
        factoryMap.put(EntityFactories.PlayerBulletFactory, playerBulletFactory);

        IEntityFactory enemyBulletFactory = new EnemyBulletEntityFactory(atlas);
        factoryMap.put(EntityFactories.EnemyBulletFactory, enemyBulletFactory);

        IEntityFactory staticScreenEffectFactory = new StaticScreenEffectFactory(atlasStatic);
        factoryMap.put(EntityFactories.StaticScreenEffectFactory, staticScreenEffectFactory);

        return  factoryMap;
    }

    @Override
    public void hide() {
    }
}
