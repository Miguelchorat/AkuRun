package com.mygdx.akurun;

import com.badlogic.gdx.Game;

public class AkuRunGame extends Game {


	@Override
	public void create() {
		setScreen(new GameplayScreen());
	}
}
