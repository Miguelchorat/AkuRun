package com.mygdx.akurun.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.akurun.GameplayScreen;
import com.mygdx.akurun.util.Constants;



public class AkurunHud {

    public final Viewport viewport;
    private Texture appleIcon;
    private Texture scoreIcon;
    private Texture pauseIcon;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    BitmapFont font;
    GameplayScreen gameplayScreen;

    public AkurunHud(GameplayScreen gameplayScreen){

        this.gameplayScreen = gameplayScreen;
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE,Constants.HUD_VIEWPORT_SIZE);
        appleIcon = new Texture(Constants.APPLE_HUD);
        scoreIcon = new Texture(Constants.SCORE_HUD);
        pauseIcon = new Texture(Constants.PAUSE_HUD);
        generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_HUD));
        parameter = new FreeTypeFontParameter();
        parameter.size = 25;
        parameter.borderWidth = 2f;
        parameter.borderColor= Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void render(SpriteBatch batch, int score){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(scoreIcon,Constants.HUD_MARGIN - Constants.APPLE_CENTER*2.4f,viewport.getWorldHeight() - Constants.HUD_MARGIN - appleIcon.getHeight()*2.1f,Constants.SCCORE_WIDTH,Constants.SCCORE_HEIGHT);
        font.draw(batch,""+score,Constants.HUD_MARGIN + Constants.APPLE_CENTER*4,viewport.getWorldHeight() - Constants.HUD_MARGIN/2.7f - appleIcon.getHeight());
        batch.draw(appleIcon,Constants.HUD_MARGIN - Constants.APPLE_CENTER,viewport.getWorldHeight() - Constants.HUD_MARGIN/2 - appleIcon.getHeight()*2.5f,appleIcon.getWidth()*2.5f,appleIcon.getHeight()*2.5f);
//        batch.draw(pauseIcon,viewport.getWorldWidth() - Constants.HUD_MARGIN*2,viewport.getWorldHeight() - Constants.HUD_MARGIN*2,pauseIcon.getWidth()*3,pauseIcon.getHeight()*3);
        batch.draw(pauseIcon,viewport.getWorldWidth() - Constants.HUD_MARGIN*2,viewport.getWorldHeight() - Constants.HUD_MARGIN*2,Constants.HUD_MARGIN*1.5f,Constants.HUD_MARGIN*1.5f);


        if(Gdx.input.justTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            viewport.getCamera().unproject(tmp);
            Rectangle settingBounds = new Rectangle(viewport.getWorldWidth() - Constants.HUD_MARGIN*2,viewport.getWorldHeight() - Constants.HUD_MARGIN*2,Constants.HUD_MARGIN*1.5f,Constants.HUD_MARGIN*1.5f);

            if(settingBounds.contains(tmp.x,tmp.y)){
                Gdx.app.getApplicationListener().pause();
                if (gameplayScreen.getState() == GameplayScreen.State.RUN)
                    gameplayScreen.setState(GameplayScreen.State.PAUSE);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            if (gameplayScreen.getState() == GameplayScreen.State.RUN)
                gameplayScreen.setState(GameplayScreen.State.PAUSE);
        }

        batch.end();
    }


}
