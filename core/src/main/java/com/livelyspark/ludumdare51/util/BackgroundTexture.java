package com.livelyspark.ludumdare51.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundTexture {
    public Texture tex;
    public float speed;

    public BackgroundTexture(Texture tex, float speed)
    {
        this.tex = tex;
        //this.tex.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        this.speed = speed;
    }
}
