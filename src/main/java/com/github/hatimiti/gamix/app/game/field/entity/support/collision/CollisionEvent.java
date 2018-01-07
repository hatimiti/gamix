package com.github.hatimiti.gamix.app.game.field.entity.support.collision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;


public class CollisionEvent {

	/** 衝突(自) */
	protected Entity self;
	/** 衝突(至) */
	protected Entity target;

	/** Shape(自) */
	protected Shape selfShape;
	/** Shape(至) */
	protected Shape targetShape;

	/** 衝突ポイント */
	protected Point point;

	/** 衝突ベクトル */
	protected CollisionVector vector;

	public CollisionEvent(
			final Entity self,
			final Entity target,
			final Shape selfShape,
			final Shape targetShape,
			final Point point) {

		this.self = self;
		this.target = target;
		this.selfShape = selfShape;
		this.targetShape = targetShape;
		this.point = point;

//		FacingDirection entity1Fd = entity1.getDirection();
//		FacingDirection entity2Fd = entity2.getDirection();
//
//		boolean isMovable1 = entity1 instanceof Movable;
//		boolean isMovable2 = entity2 instanceof Movable;
//
//		float entity1Dx = isMovable1 ? ((Movable) entity1).getSpeed() : -1;
//		float entity2Dx = isMovable2 ? ((Movable) entity2).getSpeed() : -1;
//
//		if (isMovable1 && isMovable2) {
//			this.vector = new CollisionVector(null, 0, 0);
//			return;
//		} else if (isMovable1) {
//			this.vector = new CollisionVector(
//					entity1.getDirection().reverse(), entity1Dx, entity1Dx);
//			return;
//		} else if (isMovable2) {
//			this.vector = new CollisionVector(
//					entity2.getDirection().reverse(), entity2Dx, entity2Dx);
//			return;
//		}
//
//		float subDx = entity1Dx - entity2Dx;
//
//		if (subDx == 0) {
//			this.vector = new CollisionVector(null, 0, 0);
//		} else if (subDx < 0) {
//			this.vector = new CollisionVector(entity1Fd, subDx, subDx);
//		} else {
//			this.vector = new CollisionVector(entity1Fd, subDx, subDx);
//		}

	}

	public Entity getSelf() {
		return this.self;
	}

	public Entity getTarget() {
		return this.target;
	}

	public Shape getSelfShape() {
		return selfShape;
	}

	public Shape getTargetShape() {
		return targetShape;
	}

	public Point getCollitionPoint() {
		return this.point;
	}

	public int getCenterX() {
		return (int) getCollitionPoint().getCenterX();
	}
	
	public int getCenterY() {
		return (int) getCollitionPoint().getCenterY();
	}

	public CollisionVector getVector() {
		return vector;
	}

	public CollisionEvent reverse() {
		CollisionEvent rev = new CollisionEvent(
				this.target,
				this.self,
				this.targetShape,
				this.selfShape,
				this.point);
		return rev;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CollisionEvent)) {
			return false;
		}
		CollisionEvent target = (CollisionEvent) o;

		boolean isEq1 = new EqualsBuilder()
			.append(this.self, target.self)
			.append(this.target, target.target)
			.isEquals();

		boolean isEq2 = new EqualsBuilder()
			.append(this.self, target.target)
			.append(this.target, target.self)
			.isEquals();

		return isEq1 || isEq2;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.self.hashCode() + this.target.hashCode())
			.hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}