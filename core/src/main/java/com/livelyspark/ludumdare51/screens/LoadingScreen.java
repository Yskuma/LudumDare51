package com.livelyspark.ludumdare51.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare51.enums.Screens;
import com.livelyspark.ludumdare51.managers.IScreenManager;
import com.livelyspark.ludumdare51.managers.MusicManager;

public class LoadingScreen extends AbstractScreen {

    private final MusicManager musicManager;
    private Stage stage;

    private float startX, endX;
    private float percent;

    private Label titleLabel;
    private ProgressBar progressBar;

    public LoadingScreen(IScreenManager screenManager, AssetManager assetManager, MusicManager musicManager) {
        super(screenManager, assetManager);
        this.musicManager = musicManager;
    }

    @Override
    public void show() {
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));

        stage = new Stage();
        titleLabel = new Label("Loading", uiSkin, "font", Color.WHITE);
        progressBar = new ProgressBar(0f, 1f, 0.01f, false, uiSkin);

        loadAssets();
    }

    public void loadAssets() {
        // Add everything to be loaded, for instance:

        assetManager.load("textures/spritesheet.atlas", TextureAtlas.class);

        assetManager.load("textures/background_fantasy/background_1.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_2.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_3.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_4.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_5.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_6.png", Texture.class);
        assetManager.load("textures/background_fantasy/background_7.png", Texture.class);

        assetManager.load("textures/background_scifi/background_1.png", Texture.class);
        assetManager.load("textures/background_scifi/background_2.png", Texture.class);
        assetManager.load("textures/background_scifi/background_3.png", Texture.class);
        assetManager.load("textures/background_scifi/background_4.png", Texture.class);
        assetManager.load("textures/background_scifi/background_5.png", Texture.class);

        assetManager.load("textures/static/static_0.png", Texture.class);
        assetManager.load("textures/static/static_1.png", Texture.class);
        assetManager.load("textures/static/static_2.png", Texture.class);
        assetManager.load("textures/static/static_3.png", Texture.class);
        assetManager.load("textures/static/static_4.png", Texture.class);

        assetManager.load("sounds/explosion.ogg", Sound.class);
        assetManager.load("sounds/hit.ogg", Sound.class);
        assetManager.load("sounds/pew.ogg", Sound.class);
        assetManager.load("sounds/big_pew.ogg", Sound.class);
        assetManager.load("sounds/static.ogg", Sound.class);
        assetManager.load("sounds/brum.ogg", Sound.class);

        assetManager.load("sounds/music_fantasy.ogg", Music.class);
        assetManager.load("sounds/music_scifi.ogg", Music.class);


        assetManager.load("textures/title_screen.png", Texture.class);

    }

    @Override
    public void resize(int width, int height) {
        progressBar.setWidth(stage.getWidth() * 0.8f);
        progressBar.setX((stage.getWidth() - progressBar.getWidth()) / 2);
        progressBar.setY((stage.getHeight() - progressBar.getHeight()) / 2);

        titleLabel.setX((stage.getWidth() - titleLabel.getWidth()) / 2);
        titleLabel.setY(progressBar.getY() + 50);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // True if we've finished loading assets
        if (assetManager.update()) {
            musicManager.LoadMusic();
            screenManager.switchScreen(Screens.MainMenu);
        }

        percent = Interpolation.linear.apply(percent, assetManager.getProgress(), 0.1f);
        progressBar.setValue(percent);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets if we have any
        // assetManager.unload("data/loading.pack");
    }
}
