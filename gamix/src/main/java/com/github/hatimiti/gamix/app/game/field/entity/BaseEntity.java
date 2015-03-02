package com.github.hatimiti.gamix.app.game.field.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.damage.DamageListener;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionListener;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.type.collection.DamageListenerList;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;

public abstract class BaseEntity
		implements Serializable, CollisionListener {

	protected EntityId entityId = new EntityId(RandomStringUtils.randomAlphanumeric(10));//EntityId.NONE;
	protected Shape shape;
	protected FacingDirection direction;
	protected List<Shape> collisionShapes;

	protected DamageListenerList damageListeners;

	/*
	 * constructor
	 */

	public BaseEntity(final Shape shape) {
		this.shape = shape;
		this.direction = FacingDirection.DOWN;
		this.collisionShapes = new ArrayList<>();
		this.damageListeners = new DamageListenerList();
	}

	/*
	 * public
	 */

	public boolean addDamageListener(final DamageListener listener) {
		return this.damageListeners.add(listener);
	}

	/*
	 * getter
	 */

	public EntityId getEntityId() {
		if (this.entityId == null) {
			return EntityId.NONE;
		}
		return this.entityId;
	}

	public FacingDirection getDirection() {
		return this.direction;
	}

	public Shape getShape() {
		return this.shape;
	}
	
	public void position(float x, float y) {
		getShape().setX(x);
		getShape().setY(y);
	}

	public int getX() {
		return (int) getShape().getX();
	}

	public int getY() {
		return (int) getShape().getY();
	}

	public int getCenterX() {
		return (int) getShape().getCenterX();
	}

	public int getCenterY() {
		return (int) getShape().getCenterY();
	}

	@Override
	public Shape[] getCollisionShapes() {
		return new Shape[] { getShape() };
	}

	/*
	 * setter
	 */

	public void setEntityId(final EntityId entityId) {
		this.entityId = entityId;
	}

	public void faceTo(final FacingDirection direction) {
		this.direction = direction;
	}

	/*
	 * protected
	 */

	/*
	 * abstract
	 */

	public abstract void draw(Graphics g);
	public abstract void update(EntityContainer ec);
	/** false を返す場合、ゲーム内から削除される */
	public abstract boolean existsInGame();

}