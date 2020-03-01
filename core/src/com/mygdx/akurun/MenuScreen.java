package com.mygdx.akurun;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.akurun.entities.Background;
import com.mygdx.akurun.util.Constants;

public class MenuScreen extends ScreenAdapter {

    private AkuRunGame game;
    private Background background;
    ExtendViewport viewport;
    SpriteBatch batch;

    public MenuScreen(AkuRunGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        background = new Background();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        background.renderMenu(batch);

    }
}
