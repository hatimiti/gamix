package com.github.hatimiti.gamix.app.game.field.entity;

import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;

public final class ClientEntityContainer extends EntityContainer {

	private static ClientEntityContainer instance;

	// プレイヤー自身の情報
	private Player player;

	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean addTo(MapTile tile, Entity entity) {
		if (entity instanceof Player) {
			this.player = (Player) entity;
		}
		return super.addTo(tile, entity);
	}

	@Override
	public void clearEntities() {
		this.player = null;
		super.clearEntities();
	}

	public static synchronized ClientEntityContainer clientEntityContainer() {
		if (instance == null) {
			instance = new ClientEntityContainer();
		}
		return instance;
	}
}