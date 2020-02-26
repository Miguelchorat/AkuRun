package com.mygdx.akurun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.util.Assets;

public class Apple {

    public Vector2 position; //Posicion de la manzana
    private long startTime;

    public Apple(int x,int y){
        position = new Vector2(x, y);
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        TextureRegion region = Assets.instance.appleAssets.appleAnimation.getKeyFrame(elapsedTime);

        batch.draw(
                region.getTexture(),
                position.x,
                position.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

}
