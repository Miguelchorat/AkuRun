package com.mygdx.akurun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.akurun.entity.Aku;

public class Level {
    Aku aku;

    public Level(){
        aku = new Aku();
    }

    public void update(float delta){
        aku.update(delta);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        aku.render(batch);
        batch.end();
    }
}
