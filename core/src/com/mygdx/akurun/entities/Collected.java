package com.mygdx.akurun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class Collected{

    private final Vector2 position; //Posicion de la recolecta de la manzana
    private final long startTime; //Comienzo de la animacion

    public Collected(Vector2 position) {
        this.position = position;
        startTime = TimeUtils.nanoTime();
    }

    public void render(SpriteBatch batch) {
        TextureRegion region = Assets.instance.appleAssets.collectedAnimation.getKeyFrame(startTime);
        batch.draw(
                region.getTexture(),
                position.x - Constants.APPLE_CENTER,
                position.y - Constants.APPLE_CENTER,
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

    public boolean isFinished() {
        final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime()-startTime);
        return Assets.instance.appleAssets.collectedAnimation.isAnimationFinished(elapsedTime);
    }
}