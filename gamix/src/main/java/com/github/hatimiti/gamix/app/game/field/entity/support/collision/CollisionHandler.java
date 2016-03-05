package com.github.hatimiti.gamix.app.game.field.entity.support.collision;

import java.util.HashSet;
import java.util.List;
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

	public void judgeCollision(final EntityList entities) {

		Set<CollisionEvent> collision = new HashSet<>();

		for (Entity origin : entities) {
			for (Entity target : entities) {
				if (origin.equals(target)) {
					continue;
				}
				CollisionJudge judge = isCollisioning(origin, target);
				if (judge.result) {
					collision.add(new CollisionEvent(
							origin,
							target,
							judge.origin,
							judge.target,
							judge.point));
				}
			}
		}

		Set<Entity> nowCollisionEntities = new HashSet<>();

		for (CollisionEvent event : collision) {
			event.getSelf().onCollision(event);
			event.getTarget().onCollision(event.reverse());
			nowCollisionEntities.add(event.getSelf());
			nowCollisionEntities.add(event.getTarget());
		}

		List<Entity> freeEntities = this.preCollisionEntities.parallelStream()
			.filter(p -> _Util.contains(nowCollisionEntities, p))
			.collect(Collectors.toList());
		
		freeEntities.parallelStream()
			.forEach(e -> e.onCollisionFree());
		
		this.preCollisionEntities = nowCollisionEntities;
	}
	
	protected CollisionJudge isCollisioning(final Entity a, final Entity b) {
		for (Shape as : a.getCollisionShapes()) {
			for (Shape bs : b.getCollisionShapes()) {
				if (as.intersects(bs)) {
					return new CollisionJudge(true, as, bs);
				}
			}
		}
		return new CollisionJudge(false, null, null);
	}


	protected static class CollisionJudge {

		private boolean result;
		private Shape origin;
		private Shape target;
		private Point point;

		public CollisionJudge(
				final boolean result, final Shape origin, final Shape target) {
			this.result = result;
			this.origin = origin;
			this.target = target;

			if (origin == null || target == null) {
				return;
			}

			Line line = new Line(
					origin.getCenterX(),
					origin.getCenterY(),
					target.getCenterX(),
					target.getCenterY());

			this.point = Point.at(line.getCenterX(), line.getCenterY());
		}

	}
}