package com.livelyspark.ludumdare51.systems.common.ui;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare51.GlobalGameState;
import com.livelyspark.ludumdare51.ashley.IteratingSystemBetter;
import com.livelyspark.ludumdare51.components.DebugLabelComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;

public class DebugGameGenreUiSystem extends EntitySystem {

    private final GlobalGameState gameState;
    private Stage stage;
    private Table table;
    private Label nameLabel;
    private Label timeLabel;

    public DebugGameGenreUiSystem(GlobalGameState gameState) {

        this.gameState = gameState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        //super.addedToEngine(engine);

        stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");



        table = new Table(uiSkin);
        table.columnDefaults(0).pad(5);
        //table.setDebug(true);
        table.setWidth(100);
        table.setHeight(40);
        table.setX(Gdx.graphics.getWidth() - 100);
        table.setY(stage.getHeight() - table.getHeight());
        table.background(tableBackground);

        table.columnDefaults(0).center();

        nameLabel = table.add("name", "small", Color.BLACK).getActor();
        table.row();
        timeLabel = table.add("time", "small", Color.BLACK).getActor();

        stage.addActor(table);
   }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        //super.update(deltaTime);

        String text = "ffff";

        switch(gameState.gameGenre)
        {
            case FantasyFlappy:
                text = "Fantasy";
                break;
            case ScifiRType:
                text = "SciFi";
        }

        nameLabel.setText(text);
        timeLabel.setText(String.valueOf(gameState.timeInGenre));

        stage.act();
        stage.draw();
    }


    private Drawable backgroundDrawable()
    {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

}
