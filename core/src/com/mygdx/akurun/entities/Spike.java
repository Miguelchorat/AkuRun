package com.mygdx.akurun.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.akurun.util.Constants;

public class Spike {

    public Vector2 position; //Posicion de la spike
    private Texture imagen;

    public Spike(int x,int y){
        position = new Vector2(x,y);
        imagen = new Texture(Constants.SPIKE);
    }

    public void render(SpriteBatch batch) {
        batch.draw(imagen,position.x,0,16,16);
    }
}
