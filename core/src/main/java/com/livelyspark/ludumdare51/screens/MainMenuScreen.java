package com.livelyspark.ludumdare51.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.rendering.AnimationComponent;
import com.livelyspark.ludumdare51.enums.Screens;
import com.livelyspark.ludumdare51.managers.IScreenManager;
import com.livelyspark.ludumdare51.systems.common.render.AnimationKeyframeUpdateSystem;
import com.livelyspark.ludumdare51.systems.common.render.SpriteRenderSystem;

public class MainMenuScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private Label titleLabel;
    private Label clickContinueLabel;
    //private Sprite sprite;
    //private PositionComponent spritePos;
    private Label hintLabel1;
    private Label hintLabel2;

    public MainMenuScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) { // If the screen is touched after the game is done loading, go to the main menu screen
            screenManager.switchScreen(Screens.Game);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.x = width/2;
        camera.position.y = height/2;
        camera.update();

        float midX = (stage.getWidth() / 2);
        float midY = (stage.getHeight() / 2);

        titleLabel.setX(midX - (titleLabel.getWidth() / 2));
        titleLabel.setY((midY + titleLabel.getHeight()));

        //spritePos.x = width/2;
        //spritePos.y = height / 2;

        hintLabel1.setX(midX - (hintLabel1.getWidth() / 2));
        hintLabel1.setY((midY + titleLabel.getHeight()  - 40));

        hintLabel2.setX(midX - (hintLabel2.getWidth() / 2));
        hintLabel2.setY((midY + titleLabel.getHeight()  - 60));

        clickContinueLabel.setX(midX - (clickContinueLabel.getWidth() / 2));
        clickContinueLabel.setY((midY + titleLabel.getHeight()  - 100));
    }
////948x533
    @Override
    public void show() {
        camera = new OrthographicCamera(948, 533);

        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        titleLabel = new Label("Uni-Type", uiSkin, "title", Color.WHITE);
        clickContinueLabel = new Label("Press Space To Continue", uiSkin, "medium", Color.WHITE);

        hintLabel1 = new Label("SPACE to flap/fire", uiSkin, "medium", Color.WHITE);
        hintLabel2 = new Label("WASD to move", uiSkin, "medium", Color.WHITE);

        stage.addActor(titleLabel);
        stage.addActor(clickContinueLabel);
        stage.addActor(hintLabel1);
        stage.addActor(hintLabel2);

        addEntities();

        engine.addSystem(new AnimationKeyframeUpdateSystem());
        engine.addSystem(new SpriteRenderSystem(camera));
    }

    private void addEntities() {

        Texture background = assetManager.get("textures/title_screen.png", Texture.class);
        TextureRegion tr = new TextureRegion(background);
        Animation<TextureRegion> anim = new Animation<TextureRegion>(1.0f, tr);
        engine.addEntity(new Entity()
                        .add(new AnimationComponent(anim))
                        .add(new PositionComponent(camera.viewportWidth/2, camera.viewportHeight/2))
        );
    }

    @Override
    public void hide() {
    }
}
