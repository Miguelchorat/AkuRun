package com.mygdx.akurun.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.akurun.GameplayScreen;
import com.mygdx.akurun.util.Constants;

public class PauseMenu {

    public final Viewport viewport;
    private Texture window;
    private Texture playIcon;
    private Texture restartIcon;
    private Texture exitIcon;
    private Texture header;
    GameplayScreen gameplayScreen;

    public PauseMenu(GameplayScreen gameplayScreen) {
        this.gameplayScreen = gameplayScreen;
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        window = new Texture(Constants.WINDOW_HUD);
        playIcon = new Texture(Constants.PLAY_HUD);
        restartIcon = new Texture(Constants.RESTART_HUD);
        exitIcon = new Texture(Constants.EXIT_HUD);
        header = new Texture(Constants.HEADER_HUD);
    }

    public void render(SpriteBatch batch) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(window,viewport.getWorldWidth()/3f,viewport.getWorldHeight()/3,window.getWidth()/3f,window.getHeight()/6f);
        batch.draw(header,viewport.getWorldWidth()/3.5f,viewport.getWorldHeight()/2f,header.getWidth()/2.5f,header.getHeight()/3.5f);
        batch.draw(playIcon,viewport.getWorldWidth()/1.7f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
        batch.draw(restartIcon,viewport.getWorldWidth()/2.09f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
        batch.draw(exitIcon,viewport.getWorldWidth()/2.7f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);

        if(Gdx.input.justTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            viewport.getCamera().unproject(tmp);
            Rectangle playBounds = new Rectangle(viewport.getWorldWidth()/1.7f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
            Rectangle restartBounds = new Rectangle(viewport.getWorldWidth()/2.09f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
            Rectangle exitBounds = new Rectangle(viewport.getWorldWidth()/2.7f,viewport.getWorldHeight()/2.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);

            if(playBounds.contains(tmp.x,tmp.y)){
                if (gameplayScreen.getState() == GameplayScreen.State.PAUSE)
                    gameplayScreen.setState(GameplayScreen.State.RUN);
            }

            if(restartBounds.contains(tmp.x,tmp.y)){
                gameplayScreen.getTheme().dispose();
                gameplayScreen.show();
            }

            if(exitBounds.contains(tmp.x,tmp.y)){
                gameplayScreen.getTheme().dispose();
                gameplayScreen.getGame().showMenuScreen();
                gameplayScreen.dispose();
            }
        }

        batch.end();

    }
}
