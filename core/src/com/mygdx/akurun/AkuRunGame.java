package com.mygdx.akurun;

import com.badlogic.gdx.Game;

public class AkuRunGame extends Game {


	@Override
	public void create() {
		//showMenuScreen();
		showGameplayScreen();
	}

	public void showMenuScreen(){
		setScreen(new MenuScreen(this));
	}

	public void showGameplayScreen(){
		setScreen(new GameplayScreen());
	}
}
