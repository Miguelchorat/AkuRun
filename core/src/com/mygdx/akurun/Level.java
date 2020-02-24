package com.mygdx.akurun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.akurun.entities.Aku;
import com.mygdx.akurun.entities.Apple;
import com.mygdx.akurun.entities.Platform;
import com.mygdx.akurun.util.Constants;

public class Level {

    Aku aku;
    DelayedRemovalArray<Platform> platforms;
    DelayedRemovalArray<Apple> apples;
    int left;
    int top;
    int width;
    int height;

    public Level(){
        aku = new Aku(new Vector2(50,75));
        init();
    }

    public void init() {
        left = 170;
        top = 0;
        width = 0;
        height = 75;
        platforms = new DelayedRemovalArray<Platform>();
        apples = new DelayedRemovalArray<Apple>();
        platforms.add(new Platform(0,50,225,50));

    }

    public void generator(){

        left +=MathUtils.random(175,190);
        top=MathUtils.random(15,70);
        width=MathUtils.random(75,105);
        Platform newPlatform = new Platform(left,top,width,height);
        Apple apple = new Apple((left+(width/3)),top+5);

        platforms.add(newPlatform);
        apples.add(apple);

    }

    public void update(float delta){

        generator();
        platforms.begin();
        apples.begin();
        for (int i = 0; i < platforms.size; i++) {
            if (platforms.get(i).getLeft() < aku.position.x- Constants.WORLD_SIZE*3) {
                platforms.removeIndex(i);
                apples.removeIndex(i);
            }
        }
        platforms.end();
        apples.end();
        aku.update(delta,platforms,apples);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (Platform platform : platforms) {
            platform.render(batch);
        }
        for (Apple a : apples){
            a.render(batch);
        }
        aku.render(batch);

        batch.end();
    }
}
