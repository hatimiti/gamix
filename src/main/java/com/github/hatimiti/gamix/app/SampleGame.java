package com.github.hatimiti.gamix.app;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import com.github.hatimiti.gamix.base.BaseGame;

public class SampleGame extends BaseGame {

	public static final int SCALE = 2;
	public static final int WIDTH = 800 / SCALE;
	public static final int HEIGHT = 600 / SCALE;

	private AppGameContainer gameContainer;
	
	public SampleGame() throws SlickException {
		this.gameContainer = new AppGameContainer(new GamePanel());
		gameContainer.setDisplayMode(WIDTH * SCALE, HEIGHT * SCALE, false);
		gameContainer.setTargetFrameRate(30);
		gameContainer.setAlwaysRender(true);
		gameContainer.setUpdateOnlyWhenVisible(false);
	}
	
	public void start() throws SlickException {
		this.gameContainer.start();
	}
	
}