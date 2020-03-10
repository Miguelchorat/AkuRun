package com.mygdx.akurun.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class Collected{

    public Vector2 position; //Posicion de la recolecta de la manzana
    private long startTime; //Comienzo de la animacion
    private Sound sound;
    private AssetManager soundAm;

    public Collected(Vector2 position) {
        this.position = position;
        soundAm = new AssetManager();
        startTime = TimeUtils.nanoTime();
        chooseMusic();
    }

    public void render(SpriteBatch batch) {
        float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        TextureRegion region = Assets.instance.appleAssets.collectedAnimation.getKeyFrame(elapsedTime);
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

    public boolean isFinished() {
        final float elapsedTime = MathUtils.nanoToSec * (TimeUtils.nanoTime()-startTime);
        return Assets.instance.appleAssets.collectedAnimation.isAnimationFinished(elapsedTime);
    }

    public void chooseMusic(){
        soundAm.load(Constants.COIN_SONG, Sound.class);
        soundAm.finishLoading();
        if (soundAm.isLoaded(Constants.COIN_SONG)) {
            sound = soundAm.get(Constants.COIN_SONG, Sound.class);
            sound.play(Constants.VOLUME);
        }
    }
}