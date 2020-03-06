package com.mygdx.akurun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.akurun.Level;
import com.mygdx.akurun.util.Assets;
import com.mygdx.akurun.util.Constants;

public class Aku {

    public Vector2 position; //Posicion del personaje
    Vector2 lastFramePosition; //Ultimo sitio donde estaba ubicado
    Vector2 velocity; //Velocidad a la que se mueve

    JumpState jumpState; //Diferentes estados de salto que tiene

    long jumpStartTime; //Tiempo en el que salto
    long startTime; //Tiempo en el que empezo a correr
    boolean jump; //Comprobar si puede realizar otro salto adicional
    boolean appearing; //Comprobar si ha aparecido
    Level level; //Nivel de partida el que esta jugando

    public Aku(Vector2 position,Level level){

        this.position = new Vector2(position.x, position.y);
        this.level = level;
        init();

    }

    public void init(){

        lastFramePosition = new Vector2(position);
        velocity = new Vector2();
        jumpState = JumpState.FALLING;
        jump = false;
        appearing = false;
    }

    public void update(float delta, DelayedRemovalArray<Platform> platforms,DelayedRemovalArray<Apple> apples) {
        lastFramePosition.set(position);
        velocity.y -= Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        /*
            Hitbox del personaje
         */
        Rectangle akuBounds = new Rectangle(
                position.x -Constants.AKU_STANCE_WIDTH/4 ,
                position.y -  Constants.AKU_EYE_HEIGHT,
                Constants.AKU_STANCE_WIDTH - Constants.AKU_STANCE_WIDTH,
                Constants.AKU_EYE_HEIGHT
        );

        /*
            Estado del salto
         */
        if (jumpState != JumpState.JUMPING) {
            if (jumpState != JumpState.RECOILING) {
                jumpState = JumpState.FALLING;
            }

            if (position.y - Constants.AKU_EYE_HEIGHT < 0) {
                jumpState = JumpState.GROUNDED;
                position.y = Constants.AKU_EYE_HEIGHT;
                velocity.y = 0;
            }
        }

        /*
            Movimimiento permanente del personaje hacia la derecha
         */
        if (jumpState != JumpState.RECOILING) {
            moveRight(delta);
        }
        /*
            Controlar si el personaje ha pisado una plataforma
         */
        for (Platform platform : platforms) {
            if (landedOnPlatform(platform)) {
                jumpState = JumpState.GROUNDED;
                velocity.y = 0;
                velocity.x = 0;
                position.y = platform.top + Constants.AKU_EYE_HEIGHT;
            }
        }

        /*
            Controlar si el personaje ha tocado una manzana
         */
        for (int i=0; i < apples.size ; i++){
            Rectangle applesBounds = new Rectangle(
                    apples.get(i).position.x - Constants.APPLE_CENTER,
                    apples.get(i).position.y -  Constants.APPLE_CENTER,
                    Assets.instance.appleAssets.appleAnimation.getKeyFrame(0).getRegionWidth(),
                    Assets.instance.appleAssets.appleAnimation.getKeyFrame(0).getRegionHeight()
            );
            if(akuBounds.overlaps(applesBounds)) {
                level.point();
                level.spawnCollected(apples.get(i).position);
                apples.removeIndex(i);
            }
        }

        for (Enemy enemy : level.getEnemys()){
            Rectangle enemyBounds = new Rectangle(
                    enemy.position.x - Constants.ENEMY_SIZE,
                    enemy.position.y - Constants.ENEMY_SIZE,
                    Constants.ENEMY_SIZE,
                    Constants.ENEMY_SIZE
            );
            if (akuBounds.overlaps(enemyBounds)) {
                if (position.x < enemy.position.x) {
                    recoilFromEnemy(0);
                } else {
                    recoilFromEnemy(1);
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            level.setGameOver(true);
        }

        if(position.y<Constants.SPIKE_SIZE){
            level.setGameOver(true);
        }

        /*
            Controlar si el usuario ha presionado la acción para el salto
         */
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) {
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

    /*
        Controla si puede realizar un doble salto si no lo ha realizado antes
     */
    private void doubleJump(){
        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()) && !jump) {
            startJump();
            jump =true;
        }
    }

    /*
        Mueve el personaje permanentemente hacia la derecha
     */
    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED) {
            startTime = TimeUtils.nanoTime();
        }
        position.x += delta * Constants.AKU_MOVE_SPEED;
    }

    /*
        Comienza la acción de un salto
     */
    private void startJump() {

        if(jump)
            jumpStartTime = TimeUtils.nanoTime()+TimeUtils.nanoTime();
        else
            jumpStartTime = TimeUtils.nanoTime();
        jumpState = JumpState.JUMPING;
        continueJump();
    }

    /*
        Continua el salto
     */
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

    /*
        Cambia el estado del salto a cayendo
     */
    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
    }
    /*
        Metodo encargado de comprobar si el personaje esta sobre la plataforma indicada
    */
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

        float runningSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        TextureRegion region = Assets.instance.akuAssets.runningAnimation.getKeyFrame(runningSeconds);

        /*
            Comprobación del estado del salto del personaje para dibujar la animación
         */
        if (jumpState == JumpState.JUMPING) {
            region = Assets.instance.akuAssets.jumpingRight;
        } else if (jumpState == JumpState.FALLING|| jumpState == JumpState.RECOILING) {
            region = Assets.instance.akuAssets.falling;
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

    private void recoilFromEnemy(int direction) {

        jumpState = JumpState.RECOILING;
        velocity.y = Constants.KNOCKBACK_VELOCITY.y;

        if (direction == 0) {
            velocity.x = -Constants.KNOCKBACK_VELOCITY.x;
        } else {
            velocity.x = Constants.KNOCKBACK_VELOCITY.x;
        }
    }

    enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }
}
