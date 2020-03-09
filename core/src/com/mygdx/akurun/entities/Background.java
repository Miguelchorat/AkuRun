package com.mygdx.akurun.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.akurun.util.Constants;

public class Background {

    private Texture background1;
    private Texture background2;
    private Texture background3;
    private Texture background4;
    private Texture sun;
    private Aku aku;
    int sourceX;
    int sourceX1;
    boolean comprobar;

    public Background(){
        init();
    }

    public Background(Aku aku){
        init();
        this.aku = aku;
        comprobar = false;
    }

    public void init(){
        background1 = new Texture(Constants.BACKGROUND);
        background1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background2 = new Texture(Constants.BACKGROUND2);
        background2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background3 = new Texture(Constants.BACKGROUND3);
        background3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background4 = new Texture(Constants.BACKGROUND4);
        background4.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        sun = new Texture(Constants.SUN);
    }

    public void render(SpriteBatch batch){
        if(comprobar) {
            sourceX++;
            comprobar = false;
        }
        else {
            sourceX1+=2;
            comprobar = true;
        }
        batch.begin();
        batch.draw(background1,aku.position.x-Constants.WORLD_SIZE/2,0,Constants.WORLD_SIZE*3,Constants.WORLD_SIZE,0,0, background1.getWidth(), background1.getHeight(),false,false);
        batch.draw(background2,aku.position.x-Constants.WORLD_SIZE/2,0,Constants.WORLD_SIZE*3,Constants.WORLD_SIZE,0,0, background2.getWidth()*4, background2.getHeight(),false,false);
        batch.draw(background3,aku.position.x-Constants.WORLD_SIZE/2,0,Constants.WORLD_SIZE*3,Constants.WORLD_SIZE,sourceX,0, background3.getWidth()*4, background3.getHeight(),false,false);
        batch.draw(background4,aku.position.x-Constants.WORLD_SIZE/2,0,Constants.WORLD_SIZE*3,Constants.WORLD_SIZE,sourceX1,0, background4.getWidth()*4, background4.getHeight(),false,false);

        batch.draw(sun,aku.position.x+Constants.WORLD_SIZE/1.5f,Constants.WORLD_SIZE/1.5f,Constants.WORLD_SIZE/4,Constants.WORLD_SIZE/4);
        batch.end();
    }

    public void renderMenu(SpriteBatch batch){

        sourceX++;
        sourceX1+=2;

        batch.begin();
        batch.draw(background1,0,0,Constants.WORLD_SIZE*10,Constants.WORLD_SIZE*6,0,0, background1.getWidth()*5, background1.getHeight(),false,false);
        batch.draw(background2,0,0,Constants.WORLD_SIZE*10,Constants.WORLD_SIZE*6,0,0, background2.getWidth()*5, background2.getHeight(),false,false);
        batch.draw(background3,0,0,Constants.WORLD_SIZE*10,Constants.WORLD_SIZE*6,sourceX,0, background3.getWidth()*5, background3.getHeight(),false,false);
        batch.draw(background4,0,0,Constants.WORLD_SIZE*10,Constants.WORLD_SIZE*6,sourceX1,0, background4.getWidth()*5, background4.getHeight(),false,false);

        batch.draw(sun,Constants.WORLD_SIZE,Constants.WORLD_SIZE*3,Constants.WORLD_SIZE,Constants.WORLD_SIZE);
        batch.end();
    }
}
