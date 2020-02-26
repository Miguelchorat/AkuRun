package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.akurun.entities.Aku;
import com.mygdx.akurun.entities.Apple;
import com.mygdx.akurun.entities.Background;
import com.mygdx.akurun.entities.Collected;
import com.mygdx.akurun.entities.Enemy;
import com.mygdx.akurun.entities.Platform;
import com.mygdx.akurun.entities.Spike;
import com.mygdx.akurun.util.ChaseCam;
import com.mygdx.akurun.util.Constants;

public class Level {

    Aku aku;
    Background background;
    DelayedRemovalArray<Platform> platforms;
    DelayedRemovalArray<Apple> apples;
    DelayedRemovalArray<Spike> spikes;
    DelayedRemovalArray<Collected> collecteds;
    DelayedRemovalArray<Enemy> enemys;
    int left;
    int top;
    int width;
    int height;
    int newSpike;
    int valorAux;
    boolean enemyActive;

    public Level(){
        init();
    }

    public void init() {
        aku = new Aku(new Vector2(50,100),this);
        enemyActive=false;
        background = new Background(aku);
        valorAux = 0;
        newSpike = 0;
        left = 170;
        top = 0;
        width = 0;
        height = 25;
        platforms = new DelayedRemovalArray<Platform>();
        apples = new DelayedRemovalArray<Apple>();
        collecteds = new DelayedRemovalArray<Collected>();
        enemys = new DelayedRemovalArray<Enemy>();
        spikes = new DelayedRemovalArray<Spike>(false,16);
        platforms.add(new Platform(0,75,250,25));
    }

    public void generator(){

        left +=MathUtils.random(175,190);
        top=MathUtils.random(70,95);
        width=MathUtils.random(75,105);
        newSpike+=18;
        spikes.add(new Spike(newSpike,0));
        Platform newPlatform = new Platform(left,top,width,height);
        Apple apple = new Apple((left-(int)Constants.APPLE_CENTER/2)+(width/2),top);
        platforms.add(newPlatform);
        apples.add(apple);

        valorAux = MathUtils.random(0,52);
        if(valorAux==1 && !enemyActive){
            enemys.add(new Enemy(new Vector2(aku.position.x+Constants.WORLD_SIZE*4,95),aku));
            enemyActive=true;
        }

    }

    public void update(float delta){

        generator();

        platforms.begin();
        apples.begin();
        spikes.begin();
        collecteds.begin();
        enemys.begin();
        for (int i = 0; i < spikes.size; i++) {
            if (spikes.get(i).position.x < aku.position.x - Constants.WORLD_SIZE*3) {
                spikes.removeIndex(i);
            }
        }
        for (int i = 0; i < platforms.size; i++) {
            if (platforms.get(i).getLeft() < aku.position.x - Constants.WORLD_SIZE*3) {
                platforms.removeIndex(i);
            }

        }for (int i = 0; i < apples.size; i++) {
            if (apples.get(i).position.x < aku.position.x - Constants.WORLD_SIZE*3) {
                apples.removeIndex(i);
            }
        }

        for (int i = 0 ; i<collecteds.size ; i++){
            if ((collecteds.get(i).position.x < aku.position.x - Constants.WORLD_SIZE*3) || collecteds.get(i).isFinished()) {
                collecteds.removeIndex(i);
            }
        }

        for (int i = 0 ; i<enemys.size ; i++){
            enemys.get(i).update(delta);
            if (enemys.get(i).position.x < aku.position.x - Constants.WORLD_SIZE) {
                enemys.removeIndex(i);
                enemyActive=false;
            }
        }
        enemys.end();
        collecteds.end();
        spikes.end();
        platforms.end();
        apples.end();
        aku.update(delta,platforms,apples);
    }

    public void render(SpriteBatch batch) {
        background.render(batch);
        batch.begin();

        for (Enemy e : enemys){
            e.render(batch);
        }

        for (Platform platform : platforms) {
            platform.render(batch);
        }

        for (Apple a : apples){
            a.render(batch);
        }

        for (Spike s : spikes){
            s.render(batch);
        }

        for (Collected c : collecteds)
            c.render(batch);

        for (Enemy e : enemys){
            e.render(batch);
        }
        aku.render(batch);
        batch.end();
    }

    public DelayedRemovalArray<Enemy> getEnemys() {
        return enemys;
    }

    public void spawnCollected(Vector2 position){
        collecteds.add(new Collected(position));
    }
}
