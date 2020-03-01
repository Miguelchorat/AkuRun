package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.akurun.overlays.AkurunHud;
import com.mygdx.akurun.overlays.FinishMenu;
import com.mygdx.akurun.overlays.PauseMenu;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.ChaseCam;

public class GameplayScreen extends ScreenAdapter {

    SpriteBatch batch;
    private Level level;
    private ChaseCam chaseCam;
    private AkurunHud hud;
    private PauseMenu pauseMenu;
    private FinishMenu finishMenu;
    private State state;

    @Override
    public void show () {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        state = State.RUN;
        batch = new SpriteBatch();
        hud = new AkurunHud(this);
        pauseMenu = new PauseMenu(this);
        finishMenu = new FinishMenu(this);
        starNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        hud.viewport.update(width,height,true);
        pauseMenu.viewport.update(width,height,true);
        finishMenu.viewport.update(width,height,true);
        level.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render (float delta) {

        switch (state) {
            case RUN:

                level.update(delta);
                chaseCam.update(delta);
                Gdx.gl.glClearColor(Color.SKY.r,Color.SKY.g,Color.SKY.b,Color.SKY.a);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                level.render(batch);
                hud.render(batch,level.getScore());
                if(level.isGameOver()) {
                    state = State.FINISH;
                }
                break;
            case PAUSE:
                pauseMenu.render(batch);
                break;
            case FINISH:
                finishMenu.render(batch,level.getScore());
                break;
            default:
                break;
        }
    }

    public void starNewLevel(){
        level = new Level();
        chaseCam = new ChaseCam();
        chaseCam.camera = level.viewport.getCamera();
        chaseCam.aku = level.getAku();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public enum State
    {
        PAUSE,
        RUN,
        FINISH
    }
}


