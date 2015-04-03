package com.github.hatimiti.gamix.app.game.field.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.newdawn.slick.geom.Point;
import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.character.OnlinePlayer;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.collection.EntityList;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.util._Util;

public abstract class EntityContainer {

	private static final Logger LOG = _Util.getLogger();

	protected Map<MapTile, EntityContainerByTile> containerMap;

	protected EntityContainer() {
		this.containerMap = new HashMap<>();
	}

	public boolean addTo(final MapTile tile, final Entity entity) {
		return getEntityListIn(tile).add(entity);
	}

	public void update(final MapTile tile, final ExchangePlayer player) {

		EntityId eid = new EntityId(player.eid);

		//FIXME メインキャラ以外の更新も必要
		Optional<Entity> entity = getEntityListIn(tile).findEntity(eid);

		if (entity.isPresent()) {
			entity.get().position(player.x, player.y);
			entity.get().faceTo(FacingDirection.getBy(player.d));

		} else {
			OnlinePlayer c = new OnlinePlayer(101, new Point(player.x, player.y));
			c.entityId = eid;
			addTo(tile, c);

			LOG.info("{} さんが参加しました", player.eid);
		}
	}

	public EntityList getEntityListIn(final MapTile tile) {
		return get(tile).entities;
	}

	public void clearEntities() {
		this.containerMap.clear();
	}

	public void clearEntitiesIn(final MapTile tile) {
		this.get(tile).entities.clear();
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