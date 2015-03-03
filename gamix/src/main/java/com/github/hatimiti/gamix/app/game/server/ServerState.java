package com.github.hatimiti.gamix.app.game.server;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.network.server.ChatServer;
import com.github.hatimiti.gamix.app.game.field.network.server.EntityServer;
import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.BaseGameState;


public class ServerState extends BaseGameState {

	protected Thread entityThread;
	protected Thread chatThread;

	protected EntityContainer entityContainer;

	public ServerState() {
		super(GameSceneState.SERVER);
		this.entityContainer = EntityContainer.getInstance();
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

		this.entityContainer.clearEntities();

		this.entityThread = new Thread(new EntityServer(
				ConstProperty.getInstance().getInt("network.server.port.entity"), this.entityContainer));
		this.entityThread.start();
		this.chatThread = new Thread(new ChatServer(
				ConstProperty.getInstance().getInt("network.server.port.chat")));
		this.chatThread.start();
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