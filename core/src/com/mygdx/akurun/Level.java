package com.mygdx.akurun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.akurun.entities.Aku;
import com.mygdx.akurun.entities.Platform;

public class Level {

    Aku aku;
    DelayedRemovalArray<Platform> platforms;
    int left;
    int top;
    int width;
    int height;
    public Level(){
        aku = new Aku();
        init();
    }

    public void init() {
        left = 0;
        top = 0;
        width = 0;
        height = 0;
        platforms = new DelayedRemovalArray<Platform>(false, 10);
    }

    public void generator(float delta){

        if (MathUtils.random() < delta * 10) {
            left +=100;
            Platform newPlatform = new Platform(left,30,40,50);
            platforms.add(newPlatform);
        }
    }

    public void update(float delta){

        generator(delta);
        platforms.begin();
        for (int i = 0; i < platforms.size; i++) {
            if (platforms.get(i).getLeft() < -aku.position.x-192) {
                platforms.removeIndex(i);
            }
        }
        platforms.end();
        aku.update(delta,platforms);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (Platform platform : platforms) {
            platform.render(batch);
        }
        aku.render(batch);

        batch.end();
    }
}
