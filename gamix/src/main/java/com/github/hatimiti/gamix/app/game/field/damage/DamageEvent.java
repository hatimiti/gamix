package com.github.hatimiti.gamix.app.game.field.damage;

import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;


public class DamageEvent {

	protected BaseEntity from;
	protected BaseEntity to;
	protected int damage;
	protected Point point;

	/*
	 * constructor
	 */

	public DamageEvent(
			final BaseEntity from,
			final BaseEntity to,
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

	public BaseEntity getFrom() {
		return from;
	}

	public BaseEntity getTo() {
		return to;
	}

	public int getDamage() {
		return damage;
	}

	public Point getPoint() {
		return point;
	}

}