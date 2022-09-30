package com.livelyspark.ludumdare51.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.livelyspark.ludumdare51.managers.IScreenManager;

public abstract class AbstractScreen implements Screen {

    protected AssetManager assetManager;
    protected IScreenManager screenManager;

    public AbstractScreen(IScreenManager screenManager, AssetManager assetManager) {
        this.screenManager = screenManager;
        this.assetManager = assetManager;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
