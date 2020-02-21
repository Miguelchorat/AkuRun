package com.mygdx.akurun.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AkuAssets akuAssets;
    public PlatformAssets platformAssets;
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
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AkuAssets{

        public final TextureAtlas.AtlasRegion standingRight;
        public final TextureAtlas.AtlasRegion jumpingRight;
        public final TextureAtlas.AtlasRegion falling;

        public final Animation walkingRightAnimation;
        public final Animation idleAnimation;
        public final Animation doubleJumpAnimation;

        public AkuAssets(TextureAtlas atlas){
            standingRight = atlas.findRegion(Constants.IDLE1);
            jumpingRight = atlas.findRegion(Constants.JUMP);
            falling = atlas.findRegion(Constants.FALL);

            Array<TextureAtlas.AtlasRegion> walkingRightFrames = new Array<TextureAtlas.AtlasRegion>();
            walkingRightFrames.add(atlas.findRegion(Constants.RUN1));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN2));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN3));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN4));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN5));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN6));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN7));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN8));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN9));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN10));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN11));
            walkingRightFrames.add(atlas.findRegion(Constants.RUN12));
            walkingRightAnimation = new Animation(Constants.RUN_LOOP_DURATION, walkingRightFrames, Animation.PlayMode.LOOP);

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

            Array<TextureAtlas.AtlasRegion> doubleJumpFrames = new Array<TextureAtlas.AtlasRegion>();
            idleFrames.add(atlas.findRegion(Constants.DOUBLE_JUMP1));
            idleFrames.add(atlas.findRegion(Constants.DOUBLE_JUMP1));
            idleFrames.add(atlas.findRegion(Constants.DOUBLE_JUMP2));
            idleFrames.add(atlas.findRegion(Constants.DOUBLE_JUMP3));
            idleFrames.add(atlas.findRegion(Constants.DOUBLE_JUMP3));

            doubleJumpAnimation = new Animation(Constants.IDLE_DURATION, doubleJumpFrames);
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

