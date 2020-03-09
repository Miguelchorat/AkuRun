package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.akurun.entities.Background;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class MenuScreen extends ScreenAdapter {

    private AkuRunGame game;
    private Background background;
    ExtendViewport viewport;
    SpriteBatch batch;
    private Texture play;
    private Texture sound;
    BitmapFont font;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Sound theme;
    private boolean music;

    public MenuScreen(AkuRunGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        theme = Gdx.audio.newSound(Gdx.files.internal(Constants.THEME_SONG));
        theme.loop(Constants.VOLUME);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE*5, Constants.WORLD_SIZE*5);
        background = new Background();
        play = new Texture(Constants.PLAY_HUD);
        sound = new Texture(Constants.SOUND);
        generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_HUD));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 144;
        parameter.borderWidth = 5f;
        parameter.borderColor= Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
        Constants.VOLUME = 1f;
        music = true;
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
        batch.begin();
        font.draw(batch,"Aku-Run",viewport.getWorldWidth()/3f,viewport.getWorldHeight()/1.3f);
        batch.draw(play,viewport.getWorldWidth()/2.3f,viewport.getWorldHeight()/5f,play.getWidth(),play.getHeight());
        batch.draw(sound,viewport.getWorldWidth()/1.1f,viewport.getWorldHeight()/1.2f,play.getWidth()/2,play.getHeight()/2);

        if(Gdx.input.justTouched()){
            Vector3 tmp = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            viewport.getCamera().unproject(tmp);
            Rectangle playBounds = new Rectangle(viewport.getWorldWidth()/2.3f,viewport.getWorldHeight()/5f,play.getWidth(),play.getHeight());
            Rectangle soundBounds = new Rectangle(viewport.getWorldWidth()/1.1f,viewport.getWorldHeight()/1.2f,play.getWidth()/2,play.getHeight()/2);

            if(playBounds.contains(tmp.x,tmp.y)){
                game.showGameplayScreen();
                theme.dispose();
            }

            if (soundBounds.contains(tmp.x,tmp.y)){
                if(music){
                    sound = new Texture(Constants.SOUND_OFF);
                    music = false;
                    Constants.VOLUME = 0f;
                    theme.dispose();
                } else{
                    sound = new Texture(Constants.SOUND);
                    music = true;
                    Constants.VOLUME = 0.5f;
                    theme = Gdx.audio.newSound(Gdx.files.internal(Constants.THEME_SONG));
                }
                theme.loop(Constants.VOLUME);
            }
        }

        batch.end();

    }
}
