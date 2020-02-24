package com.mygdx.akurun.util;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.akurun.entities.Aku;

public class ChaseCam {

    private Camera camera;
    private Aku aku;

    public ChaseCam(Camera camera,Aku aku){
        this.camera = camera;
        this.aku = aku;
    }

    public void update(float delta){

        camera.position.x=aku.position.x+115;

    }

    public Camera getCamera() {
        return camera;
    }

}
