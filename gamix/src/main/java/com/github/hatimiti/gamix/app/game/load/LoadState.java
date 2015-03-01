package com.github.hatimiti.gamix.app.game.load;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.base.BaseGameState;


public class LoadState extends BaseGameState {

	public LoadState() {
		super(GameSceneState.LOAD);
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void enter(
			GameContainer gc,
			StateBasedGame game) throws SlickException {
		super.enter(gc, game);
		game.enterState(GameSceneState.MENU.getId());
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}