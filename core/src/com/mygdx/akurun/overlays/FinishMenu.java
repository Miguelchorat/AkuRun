package com.mygdx.akurun.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.akurun.GameplayScreen;
import com.mygdx.akurun.util.Constants;

public class FinishMenu {

    public final Viewport viewport;
    private Texture window;
    private Texture restartIcon;
    private Texture exitIcon;
    private Texture header;
    private Texture table;
    GameplayScreen gameplayScreen;
    BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public FinishMenu(GameplayScreen gameplayScreen) {
        this.gameplayScreen = gameplayScreen;
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        window = new Texture(Constants.WINDOW_HUD);
        restartIcon = new Texture(Constants.RESTART_HUD);
        exitIcon = new Texture(Constants.EXIT_HUD);
        header = new Texture(Constants.HEADER_LOSE_HUD);
        table = new Texture(Constants.TABLE_HUD);
        generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_HUD));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 2f;
        parameter.borderColor= Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void render(SpriteBatch batch,int score) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(window,viewport.getWorldWidth()/3f,viewport.getWorldHeight()/6,window.getWidth()/3f,window.getHeight()/3f);
        batch.draw(table,viewport.getWorldWidth()/2.8f,viewport.getWorldHeight()/2.8f,window.getWidth()/3.5f,window.getHeight()/4.5f);
        font.draw(batch,"Score : "+score,viewport.getWorldWidth()/2.3f,viewport.getWorldHeight()/1.7f);
        batch.draw(header,viewport.getWorldWidth()/3.5f,viewport.getWorldHeight()/1.5f,header.getWidth()/2.5f,header.getHeight()/3.5f);
        batch.draw(restartIcon,viewport.getWorldWidth()/1.8f,viewport.getWorldHeight()/4.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
        batch.draw(exitIcon,viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);

        if(Gdx.input.justTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            viewport.getCamera().unproject(tmp);
            Rectangle restartBounds = new Rectangle(viewport.getWorldWidth()/1.8f,viewport.getWorldHeight()/4.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);
            Rectangle exitBounds = new Rectangle(viewport.getWorldWidth()/2.5f,viewport.getWorldHeight()/4.5f,Constants.HUD_MARGIN*1.4f,Constants.HUD_MARGIN*1.4f);


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
