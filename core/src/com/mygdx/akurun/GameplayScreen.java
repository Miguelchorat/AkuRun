package com.mygdx.akurun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.akurun.overlays.AkurunHud;
import com.mygdx.akurun.overlays.FinishMenu;
import com.mygdx.akurun.overlays.PauseMenu;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.ChaseCam;
import com.mygdx.akurun.util.Constants;

public class GameplayScreen extends ScreenAdapter {

    SpriteBatch batch;
    private Level level;
    private ChaseCam chaseCam;
    private AkurunHud hud;
    private PauseMenu pauseMenu;
    private FinishMenu finishMenu;
    private State state;
    private AkuRunGame game;
    private Sound theme;
    private AssetManager soundAm;

    public GameplayScreen(AkuRunGame game){
        this.game = game;
    }

    @Override
    public void show () {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);
        soundAm = new AssetManager();
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
        chooseMusic();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public AkuRunGame getGame() {
        return game;
    }

    public Sound getTheme() {
        return theme;
    }

    public enum State
    {
        PAUSE,
        RUN,
        FINISH
    }

    public void chooseMusic(){
        soundAm.load(Constants.THEME_SONG, Sound.class);
        soundAm.finishLoading();
        if (soundAm.isLoaded(Constants.THEME_SONG)) {
            theme = soundAm.get(Constants.THEME_SONG, Sound.class);
            theme.loop(Constants.VOLUME);
        }
    }
}


