package com.github.hatimiti.gamix.app.game.server;

import static com.github.hatimiti.gamix.app.game.field.entity.ServerEntityContainer.serverEntityContainer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.game.field.network.entity.EntityServer;
import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.BaseGameState;
import com.github.hatimiti.gamix.base.network.chat.ChatServer;


public class ServerState extends BaseGameState {

	public ServerState() {
		super(GameSceneState.SERVER);
	}

	@Override
	public void init(
			final GameContainer gc, final StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void enter(final GameContainer gc, final StateBasedGame game)
			throws SlickException {

		super.enter(gc, game);

		serverEntityContainer().clearEntities();

		new EntityServer(
				ConstProperty.getInstance().getInt("network.server.port.entity")
		).start();

		new ChatServer(
				ConstProperty.getInstance().getInt("network.server.port.chat")
		).start();
	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame game, final Graphics g)
			throws SlickException {
		g.drawString("ServerMode", 10, 50);
	}

	@Override
	public void update(final GameContainer gc, final StateBasedGame game, final int arg2)
			throws SlickException {
	}

}