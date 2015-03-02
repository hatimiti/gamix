package com.github.hatimiti.gamix.app.game.field.type.collection;

import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.type.ListType;

public final class EntityList extends ListType<BaseEntity> {

	public void draw(final Graphics g) {
		getVal().parallelStream()
			.forEach(v -> v.draw(g));
	}

	public void update(final EntityContainer entityContainer) {
		getVal().parallelStream()
			.forEach(v -> v.update(entityContainer));
	}

	public boolean updatePlayer(final ExchangePlayer player) {

		for (BaseEntity e : getVal()) {
			if (e.getEntityId() == null
					|| !e.getEntityId().equals(new EntityId(player.eid))) {
				continue;
			}
			e.position(player.x, player.y);
			e.faceTo(FacingDirection.getBy(player.d));
			return true;
		}

		return false;
	}

	public void removeNonExsitsEntites() {
		removeAllIf(v -> !v.existsInGame());
	}

}
