package com.github.hatimiti.gamix.app.game.field.entity.support.collision;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.newdawn.slick.geom.Line;
import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.type.collection.EntityList;
import com.github.hatimiti.gamix.base.util._Util;


public class CollisionHandler {

	/** 前回判定で衝突したエンティティリスト */
	protected Set<Entity> preCollisionEntities = new HashSet<>();

	public void clear() {
		this.preCollisionEntities.clear();
	}

	public void detectCollision(final EntityList entities) {

		final Set<CollisionEvent> collision = new HashSet<>();

		entities.forEach(origin -> entities.forEach(target -> {
			if (origin.equals(target)) {
				return;/*continue*/
			}
			final CollisionJudge result = detectCollision(origin, target);
			if (result.hasCollided) {
				collision.add(new CollisionEvent(
						origin,
						target,
						result.origin,
						result.target,
						result.point));
			}
		}));

		final Set<Entity> currentCollisionEntities = new HashSet<>();
		collision.forEach(event -> {
			event.getSelf().onCollision(event);
			event.getTarget().onCollision(event.reverse());
			currentCollisionEntities.add(event.getSelf());
			currentCollisionEntities.add(event.getTarget());
		});

		final List<Entity> freedEntities = this.preCollisionEntities.parallelStream()
			.filter(p -> _Util.contains(currentCollisionEntities, p))
			.collect(Collectors.toList());
		
		freedEntities.parallelStream()
			.forEach(e -> e.onCollisionFree());
		
		this.preCollisionEntities = currentCollisionEntities;
	}
	
	protected CollisionJudge detectCollision(final Entity a, final Entity b) {
		for (final Shape as : a.getCollisionShapes()) {
			for (final Shape bs : b.getCollisionShapes()) {
				if (as.intersects(bs)) {
					return CollisionJudge.ofCollided(as, bs);
				}
			}
		}
		return CollisionJudge.ofNotCollided();
	}


	protected static class CollisionJudge {

		private boolean hasCollided;
		private Shape origin;
		private Shape target;
		private Point point;

		private CollisionJudge(
				final boolean hasCollided, final Shape origin, final Shape target) {

			this.hasCollided = hasCollided;
			this.origin = origin;
			this.target = target;

			if (origin == null || target == null) {
				return;
			}

			final Line line = new Line(
					origin.getCenterX(),
					origin.getCenterY(),
					target.getCenterX(),
					target.getCenterY());

			this.point = Point.at(line.getCenterX(), line.getCenterY());
		}

		public static CollisionJudge ofCollided(final Shape origin, final Shape target) {
			Objects.requireNonNull(origin);
			Objects.requireNonNull(target);
			return new CollisionJudge(true, origin, target);
		}

		public static CollisionJudge ofNotCollided() {
			return new CollisionJudge(false, null, null);
		}

	}
}