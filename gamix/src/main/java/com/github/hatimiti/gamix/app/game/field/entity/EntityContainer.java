package com.github.hatimiti.gamix.app.game.field.entity;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.OnlinePlayer;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.Player;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.collection.EntityList;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;

public class EntityContainer {

	private static EntityContainer instance;
	protected Player player;
	protected Map<MapTile, EntityContainerByTile> containerMap;

	private EntityContainer() {
		this.containerMap = new HashMap<>();
	}

	public boolean addTo(final MapTile tile, final BaseEntity entity) {
		if (entity instanceof Player) {
			this.player = (Player) entity;
		}
		return get(tile).entities.add(entity);
	}

	public void update(final MapTile tile, final ExchangePlayer player) throws SlickException {

		//FIXME メインキャラ以外の更新も必要
		if (getEntityListIn(tile).updatePlayer(player)) {
			return;
		}

		OnlinePlayer c = new OnlinePlayer(101, new Point(player.x, player.y));
		c.entityId = new EntityId(player.eid);
		addTo(tile, c);
		System.out.println(player.eid + "さんが参加しました");
	}

	public Player getPlayer() {
		return this.player;
	}

	public EntityList getEntityListIn(final MapTile tile) {
		return get(tile).entities;
	}

	public void clearEntities() {
		this.player = null;
		this.containerMap.clear();
	}

	public void clearEntitiesIn(final MapTile tile) {
		this.get(tile).entities.clear();
	}

	public static EntityContainer getInstance() {
		if (instance == null) {
			instance = new EntityContainer();
		}
		return instance;
	}

	protected EntityContainerByTile get(final MapTile tile) {
		EntityContainerByTile c = this.containerMap.get(tile);
		if (c == null) {
			c = new EntityContainerByTile(tile);
			this.containerMap.put(tile, c);
		}
		return c;
	}

	protected class EntityContainerByTile {
		protected MapTile tile;
		protected EntityList entities;

		public EntityContainerByTile(final MapTile tile) {
			this.tile = tile;
			this.entities = new EntityList();
		}
	}
}