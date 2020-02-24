package com.mygdx.akurun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.akurun.AkuRunGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		//If I want to test windowed
		boolean fullscreen = false;
		if(!fullscreen ){
			config.fullscreen = false;
			config.width /= 1.2f;
			config.height /= 1.2f;
		}
		config.resizable = false;
		config.samples = 1;
		new LwjglApplication(new AkuRunGame(), config);
	}
}
