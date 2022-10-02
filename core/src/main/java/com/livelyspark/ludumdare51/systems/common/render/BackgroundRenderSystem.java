package com.livelyspark.ludumdare51.systems.common.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.HealthComponent;
import com.livelyspark.ludumdare51.components.rendering.BoundingRectangleComponent;
import com.livelyspark.ludumdare51.util.BackgroundTexture;

import java.util.ArrayList;

public class BackgroundRenderSystem extends EntitySystem {
    private final GlobalGameState gameState;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private float totalTime = 0.0f;
    private float baseSpeed = 100;

    ArrayList<BackgroundTexture> scifiBackList = new ArrayList<BackgroundTexture>();
    ArrayList<BackgroundTexture> fantasyBackList = new ArrayList<BackgroundTexture>();
    public BackgroundRenderSystem(OrthographicCamera camera, GlobalGameState gameState, AssetManager assetManager) {
        batch = new SpriteBatch();
        this.camera = camera;
        this.gameState = gameState;


        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_1.png", Texture.class),
                0));
        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_2.png", Texture.class),
                0));
        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_3.png", Texture.class),
                baseSpeed * 1.2f));
        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_4.png", Texture.class),
                baseSpeed * 1.4f));
        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_5.png", Texture.class),
                baseSpeed * 1.6f));
        fantasyBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_fantasy/background_6.png", Texture.class),
                baseSpeed * 1.8f));


        scifiBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_scifi/background_1.png", Texture.class),
                baseSpeed * 1.0f));
        scifiBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_scifi/background_2.png", Texture.class),
                baseSpeed * 1.2f));
        scifiBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_scifi/background_3.png", Texture.class),
                baseSpeed * 1.4f));
        scifiBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_scifi/background_4.png", Texture.class),
                baseSpeed * 1.6f));
        scifiBackList.add(new BackgroundTexture(
                assetManager.get("textures/background_scifi/background_5.png", Texture.class),
                baseSpeed * 1.8f));

      }

    @Override
    public void addedToEngine(Engine engine) {
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        if (camera == null) {
            return;
        }

        totalTime += deltaTime;

        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        //948x533
                switch (gameState.gameGenre)
        {
            case Scifi:
                DrawBackground(scifiBackList);
                break;
            case Fantasy:
                DrawBackground(fantasyBackList);
                break;
            default:
                break;
        }

        batch.end();
    }

    private void DrawBackground(ArrayList<BackgroundTexture> fantasyBackList) {
        for(BackgroundTexture bt : fantasyBackList)
        {
            int xOffset = (int)(bt.speed * totalTime);

            TextureRegion tr = new TextureRegion(bt.tex, xOffset, bt.tex.getHeight() - (int)camera.viewportHeight, (int)camera.viewportWidth, (int)camera.viewportHeight);
            batch.draw(tr, 0,0);

        }
    }
}
