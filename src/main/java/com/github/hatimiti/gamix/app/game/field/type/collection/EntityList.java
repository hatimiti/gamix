package com.github.hatimiti.gamix.app.game.field.type.collection;

import java.util.Objects;
import java.util.Optional;

import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionHandler;
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

		removeNonExistsEntities();

		this.parallelStream()
			.forEach(v -> v.update(entityContainer));

		this.collisionHandler.detectCollision(this);
	}

	public Optional<Entity> findEntity(final EntityId entityId) {
		return this.stream()
				.filter(e -> entityId.equals(e.getEntityId()))
				.findFirst();
	}
	
	@Override
	public void clear() {
		super.clear();
		this.collisionHandler.clear();
	}

	private void removeNonExistsEntities() {
		removeAllIf(v -> !v.existsInGame());
	}

	@Override
	public boolean add(final Entity entity) {
		Objects.requireNonNull(entity);
		return super.add(entity);
	}
}
