package com.livelyspark.ludumdare51.systems.common.ui;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare51.ashley.IteratingSystemBetter;
import com.livelyspark.ludumdare51.components.DebugLabelComponent;
import com.livelyspark.ludumdare51.components.PlayerComponent;
import com.livelyspark.ludumdare51.components.PositionComponent;
import com.livelyspark.ludumdare51.components.VelocityComponent;
import text.formic.Stringf;

public class DebugPlayerDetailUiSystem extends IteratingSystemBetter {


    private Stage stage;
    private Table table;

    private ComponentMapper<DebugLabelComponent> dm = ComponentMapper.getFor(DebugLabelComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> mm = ComponentMapper.getFor(VelocityComponent.class);

    public DebugPlayerDetailUiSystem() {
        super(Family.all(DebugLabelComponent.class,
                PositionComponent.class,
                VelocityComponent.class).get());
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");


        table = new Table(uiSkin);

        //table.setDebug(true);
        table.setWidth(400);
        table.setHeight(100);
        table.setX(0);
        table.setY(stage.getHeight() - table.getHeight());
        table.background(tableBackground);

        table.columnDefaults(0).center();

        stage.addActor(table);
   }

   @Override
   public void startProcessing()
   {
       table.reset();
       table.columnDefaults(0).pad(4);
   }

    @Override
    public void endProcessing()
    {
        stage.act();
        stage.draw();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DebugLabelComponent debug = dm.get(entity);
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = mm.get(entity);

        String text = debug.label.concat(": ");
        text = text.concat("pos(" + Stringf.format("%.2f",position.x) + "," + Stringf.format("%.2f",position.y) + ") ");
        text = text.concat("vel(" + Stringf.format("%.2f",velocity.x) + "," + Stringf.format("%.2f",velocity.y) + ") ");

        table.add(text, "small", Color.BLACK).getActor();
        table.row();
    }

    private Drawable backgroundDrawable()
    {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

}
