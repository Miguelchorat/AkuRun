package com.mygdx.akurun.util;

import com.badlogic.gdx.graphics.Camera;

public class ChaseCam {

    private Camera camera;

    public ChaseCam(Camera camera){
        this.camera = camera;
    }

    public void update(float delta){
        camera.position.x+=delta * Constants.CAM_MOVE_SPEED;
    }

    public Camera getCamera() {
        return camera;
    }

}
