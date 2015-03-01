package com.github.hatimiti.gamix.app.game.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.github.hatimiti.gamix.app.support.InputHelpable;


public class MenuInputHelper implements InputHelpable {

	protected static MenuInputHelper instance;
	protected Input input;

	private MenuInputHelper(Input input) {
		this.input = input;
	}

	public InputHelpable createInstance(Input input) {
		if (instance == null) {
			instance = new MenuInputHelper(input);
		}
		return instance;
	}

	@Override
	public void input(GameContainer gc) throws SlickException {
		// TODO 自動生成されたメソッド・スタブ

	}

}