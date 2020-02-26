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

    public Background(Aku aku){
        background1 = new Texture(Constants.BACKGROUND);
        background1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background2 = new Texture(Constants.BACKGROUND2);
        background2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background3 = new Texture(Constants.BACKGROUND3);
        background3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background4 = new Texture(Constants.BACKGROUND4);
        background4.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        sun = new Texture(Constants.SUN);
        this.aku = aku;
        comprobar = false;
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
        System.out.println("soruceX " +sourceX);
        System.out.println("sourceX1 "+sourceX1);
        batch.begin();
        batch.draw(background1,aku.position.x-64,0,512,192,0,0, background1.getWidth(), background1.getHeight(),false,false);
        batch.draw(background2,aku.position.x-64,0,512,192,0,0, background2.getWidth()*4, background2.getHeight(),false,false);
        batch.draw(background3,aku.position.x-64,0,512,192,sourceX,0, background3.getWidth()*4, background3.getHeight(),false,false);
        batch.draw(background4,aku.position.x-64,0,512,192,sourceX1,0, background4.getWidth()*4, background4.getHeight(),false,false);

        batch.draw(sun,aku.position.x+125,135,45,45);
        batch.end();
    }
}
