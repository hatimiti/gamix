package com.github.hatimiti.gamix.app.game.field.damage;

import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;


public class DamageEvent {

	protected Entity from;
	protected Entity to;
	protected int damage;
	protected Point point;

	/*
	 * constructor
	 */

	public DamageEvent(
			final Entity from,
			final Entity to,
			final int damage,
			final Point point) {
		this.from = from;
		this.to = to;
		this.damage = damage;
		this.point = point;
	}

	/*
	 * getter
	 */

	public Entity getFrom() {
		return from;
	}

	public Entity getTo() {
		return to;
	}

	public int getDamage() {
		return damage;
	}

	public Point getPoint() {
		return point;
	}

}