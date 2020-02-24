package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.ChaseCam;
import com.mygdx.akurun.util.Constants;

public class GameplayScreen extends ScreenAdapter {

    SpriteBatch batch;
    ExtendViewport viewport;
    private Level level;
    private ChaseCam chaseCam;
    int sourceX;
    private Texture backgroundTexture;
    private Texture moon;

    @Override
    public void show () {

        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        level = new Level();
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        chaseCam = new ChaseCam(viewport.getCamera(),level.aku);
        backgroundTexture = new Texture(Constants.BACKGROUND);
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        moon = new Texture(Constants.MOON);
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

        level.update(delta);
        viewport.apply();
        chaseCam.update(delta);
        sourceX+=1;
        Gdx.gl.glClearColor(Color.SKY.r,Color.SKY.g,Color.SKY.b,Color.SKY.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(moon,chaseCam.getCamera().position.x+55,135,45,45);
        batch.draw(backgroundTexture,chaseCam.getCamera().position.x-256,0,512,192,sourceX,0,backgroundTexture.getWidth()+backgroundTexture.getWidth(),backgroundTexture.getHeight(),false,false);
        batch.end();

        level.render(batch);
    }
}
