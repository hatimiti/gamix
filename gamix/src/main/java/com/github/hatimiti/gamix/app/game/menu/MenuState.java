package com.github.hatimiti.gamix.app.game.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.base.BaseGameState;


public class MenuState extends BaseGameState {

	public MenuState() {
		super(GameSceneState.MENU);
	}

	@Override
	public void init(
			GameContainer gc,
			StateBasedGame game) throws SlickException {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);

//		midiPlayer.play(new File(
//				ResourceLoader.getResource("musicFiles/music01.mid").getPath()));
	}

	@Override
	public void render(
			GameContainer gc,
			StateBasedGame game,
			Graphics g) throws SlickException {

//		g.setFont(FontFactory.createInstance().getFont());
		g.drawString("Menu", 100, 100);
	}

	@Override
	public void update(
			GameContainer gc,
			StateBasedGame game,
			int paramInt) throws SlickException {

		Input input = gc.getInput();
		if (input.isKeyDown(Input.KEY_ENTER)
				|| input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			game.enterState(GameSceneState.BATTLE.getId());

		} else if (input.isKeyDown(Input.KEY_S)
				|| input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {

			game.enterState(GameSceneState.SERVER.getId());
		}
	}

}