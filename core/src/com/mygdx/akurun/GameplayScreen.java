package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class GameplayScreen extends ScreenAdapter {

    SpriteBatch batch;
    ExtendViewport viewport;
    private Level level;

    @Override
    public void show () {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        level = new Level();
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }

    @Override
    public void render (float delta) {
        level.update(delta);
        viewport.apply();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        level.render(batch);
    }
}
