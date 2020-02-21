package com.mygdx.akurun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class Aku {

    public Vector2 position;
    Vector2 lastFramePosition;
    Vector2 velocity;

    Facing facing;
    JumpState jumpState;
    WalkState walkState;

    long jumpStartTime;
    long walkStartTime;
    boolean jump;

    public Aku(){
        position = new Vector2(30, 75);
        lastFramePosition = new Vector2(position);
        velocity = new Vector2();
        jumpState = JumpState.FALLING;
        facing = Facing.RIGHT;
        walkState = WalkState.STANDING;
        jump = false;
    }

    public void update(float delta, DelayedRemovalArray<Platform> platforms) {
        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        if (jumpState != JumpState.JUMPING) {
            jumpState = JumpState.FALLING;

            if (position.y - Constants.AKU_EYE_HEIGHT < 0) {
                jumpState = JumpState.GROUNDED;
                position.y = Constants.AKU_EYE_HEIGHT;
                velocity.y = 0;
            }
        }

        for (Platform platform : platforms) {
            if (landedOnPlatform(platform)) {
                jumpState = JumpState.GROUNDED;
                velocity.y = 0;
                position.y = platform.top + Constants.AKU_EYE_HEIGHT;
            }
        }
        moveRight(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            switch (jumpState) {
                case GROUNDED:
                    jump =false;
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    doubleJump();
            }
        } else {
            endJump();
        }
    }

    private void doubleJump(){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !jump) {

            startJump();
            jump =true;
        }
    }
    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && walkState != WalkState.WALKING) {
            walkStartTime = TimeUtils.nanoTime();
        }
        walkState = WalkState.WALKING;
        facing = Facing.RIGHT;
        position.x += delta * Constants.AKU_MOVE_SPEED;
    }

    private void startJump() {

        if(jump)
            jumpStartTime = TimeUtils.nanoTime()+TimeUtils.nanoTime();
        else
            jumpStartTime = TimeUtils.nanoTime();
        jumpState = JumpState.JUMPING;
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }

    boolean landedOnPlatform(Platform platform) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;

        if (lastFramePosition.y - Constants.AKU_EYE_HEIGHT >= platform.top &&
                position.y - Constants.AKU_EYE_HEIGHT < platform.top) {

            float leftFoot = position.x - Constants.AKU_STANCE_WIDTH / 3;
            float rightFoot = position.x + Constants.AKU_STANCE_WIDTH / 3;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }

    public void render(SpriteBatch batch){
        TextureRegion region = Assets.instance.akuAssets.standingRight;

        if (jumpState == JumpState.JUMPING) {
            region = Assets.instance.akuAssets.jumpingRight;
        } else if (jumpState == JumpState.FALLING) {
            region = Assets.instance.akuAssets.falling;
        } else if (walkState == WalkState.STANDING) {
            float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.akuAssets.idleAnimation.getKeyFrame(idleTimeSeconds);
        } else if (walkState == WalkState.WALKING) {
            float walkTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - walkStartTime);
            region = Assets.instance.akuAssets.walkingRightAnimation.getKeyFrame(walkTimeSeconds);
        }

        batch.draw(
                region.getTexture(),
                position.x - Constants.AKU_EYE_POSITION.x,
                position.y - Constants.AKU_EYE_POSITION.y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                false,
                false);
    }

    enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
    }

    enum Facing {
        RIGHT
    }

    enum WalkState {
        STANDING,
        WALKING
    }
}
