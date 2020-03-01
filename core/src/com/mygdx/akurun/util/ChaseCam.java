package com.mygdx.akurun.util;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.akurun.entities.Aku;

public class ChaseCam {

    public Camera camera;
    public Aku aku;

    public ChaseCam(){
    }

    public void setAku(Aku aku) {
        this.aku = aku;
    }

    public void update(float delta){

        camera.position.x=aku.position.x+115;

    }

}
