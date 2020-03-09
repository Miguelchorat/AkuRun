package com.mygdx.akurun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class Enemy {

    private long startTime;
    public Vector2 position;
    private Aku aku;

    public Enemy(Vector2 position,Aku aku){
        this.position = position;
        this.aku=aku;
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta){
        position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
        /*if(position.y>aku.position.y){
            position.y-=Constants.ENEMY_MOVEMENT_SPEED * delta/2;
        }else if(position.y<aku.position.y){
            position.y+=Constants.ENEMY_MOVEMENT_SPEED * delta/2;
        }*/

    }

    public void render(SpriteBatch batch){
        float runningSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        TextureRegion region = Assets.instance.enemyAssets.runningAnimation.getKeyFrame(runningSeconds);

        batch.draw(
                region.getTexture(),
                position.x - Constants.AKU_EYE_POSITION.x,
                position.y - Constants.AKU_EYE_POSITION.y,
                0,
                0,
                Constants.ENEMY_SIZE,
                Constants.ENEMY_SIZE,
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                true,
                false);
    }
}
