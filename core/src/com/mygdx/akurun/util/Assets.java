package com.mygdx.akurun.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AkuAssets akuAssets;
    public PlatformAssets platformAssets;
    public AppleAssets appleAssets;
    private AssetManager assetManager;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS, TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS);
        akuAssets = new AkuAssets(atlas);
        platformAssets = new PlatformAssets(atlas);
        appleAssets = new AppleAssets(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AppleAssets{

        public final Animation appleAnimation;
        public final Animation collectedAnimation;

        public AppleAssets(TextureAtlas atlas){
            Array<TextureAtlas.AtlasRegion> appleFrames = new Array<TextureAtlas.AtlasRegion>();
            appleFrames.add(atlas.findRegion(Constants.APPLE1));
            appleFrames.add(atlas.findRegion(Constants.APPLE2));
            appleFrames.add(atlas.findRegion(Constants.APPLE3));
            appleFrames.add(atlas.findRegion(Constants.APPLE4));
            appleFrames.add(atlas.findRegion(Constants.APPLE5));
            appleFrames.add(atlas.findRegion(Constants.APPLE6));
            appleFrames.add(atlas.findRegion(Constants.APPLE7));
            appleFrames.add(atlas.findRegion(Constants.APPLE8));
            appleFrames.add(atlas.findRegion(Constants.APPLE9));
            appleFrames.add(atlas.findRegion(Constants.APPLE10));
            appleFrames.add(atlas.findRegion(Constants.APPLE11));
            appleFrames.add(atlas.findRegion(Constants.APPLE12));
            appleFrames.add(atlas.findRegion(Constants.APPLE13));
            appleFrames.add(atlas.findRegion(Constants.APPLE14));
            appleFrames.add(atlas.findRegion(Constants.APPLE15));
            appleFrames.add(atlas.findRegion(Constants.APPLE16));
            appleFrames.add(atlas.findRegion(Constants.APPLE17));
            appleAnimation = new Animation(Constants.RUN_LOOP_DURATION, appleFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> collectedFrames = new Array<TextureAtlas.AtlasRegion>();
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED1));
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED2));
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED3));
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED4));
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED5));
            collectedFrames.add(atlas.findRegion(Constants.COLLECTED6));

            collectedAnimation = new Animation(0.1f, collectedFrames, Animation.PlayMode.NORMAL);
        }
    }

    public class AkuAssets{

        public final TextureAtlas.AtlasRegion standingRight;
        public final TextureAtlas.AtlasRegion jumpingRight;
        public final TextureAtlas.AtlasRegion falling;

        public final Animation runningAnimation;
        public final Animation idleAnimation;

        public AkuAssets(TextureAtlas atlas){
            standingRight = atlas.findRegion(Constants.IDLE1);
            jumpingRight = atlas.findRegion(Constants.JUMP);
            falling = atlas.findRegion(Constants.FALL);

            Array<TextureAtlas.AtlasRegion> runningFrames = new Array<TextureAtlas.AtlasRegion>();
            runningFrames.add(atlas.findRegion(Constants.RUN1));
            runningFrames.add(atlas.findRegion(Constants.RUN2));
            runningFrames.add(atlas.findRegion(Constants.RUN3));
            runningFrames.add(atlas.findRegion(Constants.RUN4));
            runningFrames.add(atlas.findRegion(Constants.RUN5));
            runningFrames.add(atlas.findRegion(Constants.RUN6));
            runningFrames.add(atlas.findRegion(Constants.RUN7));
            runningFrames.add(atlas.findRegion(Constants.RUN8));
            runningFrames.add(atlas.findRegion(Constants.RUN9));
            runningFrames.add(atlas.findRegion(Constants.RUN10));
            runningFrames.add(atlas.findRegion(Constants.RUN11));
            runningFrames.add(atlas.findRegion(Constants.RUN12));
            runningAnimation = new Animation(Constants.RUN_LOOP_DURATION, runningFrames, Animation.PlayMode.LOOP);

            Array<TextureAtlas.AtlasRegion> idleFrames = new Array<TextureAtlas.AtlasRegion>();
            idleFrames.add(atlas.findRegion(Constants.IDLE1));
            idleFrames.add(atlas.findRegion(Constants.IDLE2));
            idleFrames.add(atlas.findRegion(Constants.IDLE3));
            idleFrames.add(atlas.findRegion(Constants.IDLE4));
            idleFrames.add(atlas.findRegion(Constants.IDLE5));
            idleFrames.add(atlas.findRegion(Constants.IDLE6));
            idleFrames.add(atlas.findRegion(Constants.IDLE7));
            idleFrames.add(atlas.findRegion(Constants.IDLE8));
            idleFrames.add(atlas.findRegion(Constants.IDLE9));
            idleFrames.add(atlas.findRegion(Constants.IDLE10));
            idleFrames.add(atlas.findRegion(Constants.IDLE11));
            idleAnimation = new Animation(Constants.IDLE_DURATION, idleFrames, Animation.PlayMode.LOOP);

        }
    }

    public class PlatformAssets {

        public final NinePatch platformNinePatch;

        public PlatformAssets(TextureAtlas atlas){
            TextureAtlas.AtlasRegion region = atlas.findRegion(Constants.TERRAIN);
            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region,edge,edge,edge,edge);

        }
    }

}

