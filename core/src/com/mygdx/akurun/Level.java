package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.akurun.entities.Aku;
import com.mygdx.akurun.entities.Platform;

public class Level {

    Aku aku;
    DelayedRemovalArray<Platform> platforms;

    public Level(){
        aku = new Aku();
        platforms = new DelayedRemovalArray<Platform>();
        platforms.add(new Platform(100,50, 192,50));
        platforms.add(new Platform(300,90, 52,50));
    }

    public void update(float delta){
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
