package com.github.hatimiti.gamix.app.game.field.type.collection;

import java.util.Optional;

import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionHandler;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.network.exchange.entity.ExchangePlayer;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.type.SyncListType;

public final class EntityList extends SyncListType<Entity> {

	/** Judge collision */
	private CollisionHandler collisionHandler;

	public EntityList() {
		this.collisionHandler = new CollisionHandler();
	}

	public void draw(final Graphics g) {
		this.stream()
			.forEach(v -> v.draw(g));
	}

	public void update(final EntityContainer entityContainer) {

		removeNonExsitsEntites();

		this.parallelStream()
			.forEach(v -> v.update(entityContainer));

		judgeCollision();
	}

	public boolean updatePlayer(final ExchangePlayer player) {

		Optional<Entity> target = this.stream()
			.filter(e -> new EntityId(player.eid).equals(e.getEntityId()))
			.findFirst();

		if (target.isPresent()) {
			target.get().position(player.x, player.y);
			target.get().faceTo(FacingDirection.getBy(player.d));
			return true;
		}

		return false;
	}

	@Override
	public void clear() {
		super.clear();
		this.collisionHandler.clear();
	}

	private void removeNonExsitsEntites() {
		removeAllIf(v -> !v.existsInGame());
	}

	private void judgeCollision() {
		this.collisionHandler.judgeCollision(this);
	}

}
