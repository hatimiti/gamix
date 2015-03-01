package com.github.hatimiti.gamix.app.game.field.entity.support.collision;

import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;

public class CollisionVector {

	/** 衝突方向 */
	protected FacingDirection direction;
	/** 衝突力 */
	protected float collisionForceX;
	/** 衝突力 */
	protected float collisionForceY;

	public CollisionVector (
			FacingDirection direction,
			float collisionForceX,
			float collisionForceY) {
		this.direction = direction;
		this.collisionForceX = collisionForceX;
		this.collisionForceY = collisionForceY;
	}

	public FacingDirection getDirection() {
		return direction;
	}

	public float getCollisionForceX() {
		return collisionForceX;
	}

	public float getCollisionForceY() {
		return collisionForceY;
	}

}