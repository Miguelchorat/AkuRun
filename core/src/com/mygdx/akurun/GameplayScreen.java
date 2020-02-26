package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.ChaseCam;
import com.mygdx.akurun.util.Constants;

public class GameplayScreen extends ScreenAdapter {

    SpriteBatch batch;
    ExtendViewport viewport;
    private Level level;
    private ChaseCam chaseCam;

    @Override
    public void show () {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        level = new Level();
        chaseCam = new ChaseCam(viewport.getCamera(),level.aku);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(Color.SKY.r,Color.SKY.g,Color.SKY.b,Color.SKY.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.update(delta);
        viewport.apply();
        chaseCam.update(delta);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch);
    }
}

