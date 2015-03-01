package com.github.hatimiti.gamix.app;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import com.github.hatimiti.gamix.app.game.field.BattleState;
import com.github.hatimiti.gamix.app.game.load.LoadState;
import com.github.hatimiti.gamix.app.game.menu.MenuState;
import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.base.gui.twl.TWLStateBasedGame;

public class GamePanel extends TWLStateBasedGame {

	public static final String TITLE = "My Game";

	public GamePanel() {
		super(TITLE);
		addStates();
		this.enterState(GameSceneState.LOAD.getId());
	}

	private void addStates() {
		this.addState(new LoadState());
		this.addState(new MenuState());
		this.addState(new BattleState());
//		this.addState(new ServerState());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
//		this.getState(GameSceneState.LOAD.getId()).init(gc, this);
//		this.getState(GameSceneState.MENU.getId()).init(gc, this);
//		this.getState(GameSceneState.BATTLE.getId()).init(gc, this);
//		this.getState(GameSceneState.SERVER.getId()).init(gc, this);
	}

	@Override
	protected URL getThemeURL() {
		return ResourceLoader.getResource("twl/gameui.xml");
	}

}