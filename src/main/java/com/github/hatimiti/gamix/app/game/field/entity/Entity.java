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
import com.github.hatimiti.gamix.base.util.Point;

public abstract class Entity
		implements Serializable, CollisionListener {

	private static final long serialVersionUID = 1L;
	protected EntityId entityId = new EntityId(RandomStringUtils.randomAlphanumeric(10));//EntityId.NONE;
	protected final Shape shape;
	protected FacingDirection direction;
	protected final List<Shape> collisionShapes;

	protected final DamageListenerList damageListeners;

	/*
	 * constructor
	 */

	public Entity(final Shape shape) {
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
	
	public void positionAt(Point p) {
		getShape().setX(p.getX());
		getShape().setY(p.getY());
	}

	public Point getPoint() {
		return Point.at(getShape());
	}

	public Point getCenterPoint() {
		return Point.atCenter(getShape());
	}

	@Override
	public Shape[] getCollisionShapes() {
		return new Shape[] { getShape() };
	}

	/*
	 * setter
	 */

	public synchronized void setEntityId(final EntityId entityId) {
		if (this.entityId != null) {
			return;
		}
		this.entityId = entityId;
	}

	public void faceTo(final FacingDirection direction) {
		this.direction = direction;
	}

	/*
	 * protected
	 */

	protected float getX() {
		return getPoint().getX();
	}
	
	protected float getY() {
		return getPoint().getY();
	}

	/*
	 * abstract
	 */

	public abstract void draw(Graphics g);
	public abstract void update(EntityContainer ec);
	/** false を返す場合、ゲーム内から削除される */
	public abstract boolean existsInGame();

}