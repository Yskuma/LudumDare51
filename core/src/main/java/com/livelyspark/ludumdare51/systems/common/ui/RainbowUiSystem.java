package com.livelyspark.ludumdare51.systems.common.ui;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.genre.GenreFantasyComponent;
import com.livelyspark.ludumdare51.components.player.PlayerComponent;
import com.livelyspark.ludumdare51.components.player.PlayerRainbowComponent;
import com.livelyspark.ludumdare51.enums.GameGenres;
import text.formic.Stringf;


public class RainbowUiSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private final GlobalGameState gameState;
    private SpriteBatch batch;
    private final NinePatch progressNp;
    private ShapeRenderer shapeRenderer;
    private int progress;

    private ComponentMapper<PlayerRainbowComponent> rm = ComponentMapper.getFor(PlayerRainbowComponent.class);

    public RainbowUiSystem(GlobalGameState gameState, TextureAtlas atlas) {

        this.gameState = gameState;
        progressNp = new NinePatch(atlas.findRegion("rainbow"), 2, 2, 0, 0);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void addedToEngine(Engine engine) {
        //super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(PlayerRainbowComponent.class).get());
        batch = new SpriteBatch();
   }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        if(gameState.gameGenre == GameGenres.Fantasy && gameState.atBoss){
            int x = 200;
            int width = Gdx.graphics.getWidth() - (2 * x);
            float increment = width/100f;
            int height = 50;
            progress = 0;

            for (int i = 0; i < entities.size(); ++i){
                Entity e = entities.get(i);
                PlayerRainbowComponent rc = rm.get(e);
                if(rc.charge == 0f){
                    progress = 0;
                }
                else {
                    progress = Math.round(increment * rc.charge);
                }
            }

            int y = Gdx.graphics.getHeight() - height - 5;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();

            if(progress != 0f){
                batch.begin();
                progressNp.draw(batch, x, y, progress , height);
                batch.end();
            }
        }
    }
}
