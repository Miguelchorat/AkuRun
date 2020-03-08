package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    ChaseCam chaseCam;
    public Viewport viewport;
    private Aku aku;
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
    private boolean enemyActive;
    private boolean gameOver;
    private int score;
    private int spikeCount;

    public Level(){
        init();
    }

    public void init() {
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        score = 0;
        aku = new Aku(new Vector2(170,200),this);
        enemyActive=false;
        background = new Background(aku);
        valorAux = 0;
        newSpike = 0;
        left = 0;
        top = 0;
        width = 0;
        height = 25;
        platforms = new DelayedRemovalArray<Platform>();
        apples = new DelayedRemovalArray<Apple>();
        collecteds = new DelayedRemovalArray<Collected>();
        enemys = new DelayedRemovalArray<Enemy>();
        spikes = new DelayedRemovalArray<Spike>(false,16);
        spikeCount = 0;
        gameOver = false;
    }

    public void generator() {

        left += MathUtils.random(175, 185);
        top = MathUtils.random(50, 75);
        width = MathUtils.random(75, 105);

        if (spikeCount < 6){
            spikes.add(new Spike(newSpike, 0));
             newSpike += 128;
             spikeCount++;
        }

        Platform newPlatform = new Platform(left,top,width,height);
        Apple apple = new Apple((left-(int)Constants.APPLE_CENTER/2)+(width/2),top);
        platforms.add(newPlatform);
        apples.add(apple);

        valorAux = MathUtils.random(0,(int)Constants.WORLD_SIZE/2);
        if(valorAux==1 && !enemyActive ){
            enemys.add(new Enemy(new Vector2(aku.position.x+Constants.WORLD_SIZE*4,95),aku));
            enemyActive=true;
        }
    }

    public void generator2() {
        enemyActive = false;
        left += MathUtils.random(55, 125);
        top = MathUtils.random(25, 85);
        width = MathUtils.random(15,75);
        height = MathUtils.random(15, 65);
        Platform newPlatform = new Platform(left,top,width,height);
        platforms.add(newPlatform);

        if (spikeCount < 6){
            spikes.add(new Spike(newSpike, 0));
            newSpike += 128;
            spikeCount++;
            if(spikeCount>5)
                enemyActive=true;
        }

        Apple apple = new Apple((left-(int)Constants.APPLE_CENTER/2)+(width/2),top);
        apples.add(apple);


        if(enemyActive ){
            enemys.add(new Enemy(new Vector2(aku.position.x+Constants.WORLD_SIZE*2,aku.position.y),aku));
            enemyActive = false;
            valorAux = 0;
        }

    }

    public void update(float delta){

        generator2();

        platforms.begin();
        apples.begin();
        spikes.begin();
        collecteds.begin();
        enemys.begin();

        for (int i = 0; i < spikes.size; i++) {
            if (spikes.get(i).position.x < aku.position.x - Constants.WORLD_SIZE*2) {
                spikes.removeIndex(i);
                spikeCount--;
            }
        }
        for (int i = 0; i < platforms.size; i++) {
            if (platforms.get(i).getLeft() < aku.position.x - Constants.WORLD_SIZE*2) {
                platforms.removeIndex(i);
            }

        }for (int i = 0; i < apples.size; i++) {
            if (apples.get(i).position.x < aku.position.x - Constants.WORLD_SIZE) {
                apples.removeIndex(i);
            }
        }

        for (int i = 0 ; i<collecteds.size ; i++){
            if ((collecteds.get(i).position.x < aku.position.x - Constants.WORLD_SIZE) || collecteds.get(i).isFinished()) {
                collecteds.removeIndex(i);
            }
        }

        for (int i = 0 ; i<enemys.size ; i++){
            enemys.get(i).update(delta);
            if (enemys.get(i).position.x < aku.position.x - Constants.WORLD_SIZE/2) {
                enemys.removeIndex(i);
                enemyActive = false;
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
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
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

    public ChaseCam getChaseCam() {
        return chaseCam;
    }

    public void setChaseCam(ChaseCam chaseCam) {
        this.chaseCam = chaseCam;
    }

    public DelayedRemovalArray<Enemy> getEnemys() {
        return enemys;
    }

    public void spawnCollected(Vector2 position){
        collecteds.add(new Collected(position));
    }

    public Aku getAku() {
        return aku;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void point(){
        score++;
    }

}
