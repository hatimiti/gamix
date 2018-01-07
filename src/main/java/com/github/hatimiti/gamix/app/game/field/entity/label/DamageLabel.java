package com.github.hatimiti.gamix.app.game.field.entity.label;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.Movable;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.StandardMovable;


public class DamageLabel
		extends Entity
		implements Movable {

	protected int damage;
	protected int motion;
	protected boolean existsInGame;
	
	protected Movable movable = new StandardMovable(this);

	public DamageLabel(int damage, Point point) {
		super(new Rectangle(point.getX(), point.getY() - 25, 0, 0));
		this.damage = damage;
		this.existsInGame = true;
	}

	@Override
	public void onCollision(CollisionEvent event) {
	}

	@Override
	public void onCollisionFree() {
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawString("" + this.damage, getX(), getY());
	}

	@Override
	public void update(EntityContainer ec) {
		if (getMaxMotion() <= ++this.motion) {
			this.existsInGame = false;
			return;
		}
		getShape().setY(getY() - 1);
	}

	@Override
	public boolean existsInGame() {
		return this.existsInGame;
	}

	public int getMaxMotion() {
		return 10;
	}
	

	/*
	 * Movableの委譲処理
	 */
	
	@Override
	public boolean isMoving() {
		return this.movable.isMoving();
	}

	@Override
	public boolean isStopping() {
		return this.movable.isStopping();
	}

	@Override
	public void setSpeed(float speed) {
		this.movable.setSpeed(speed);
	}
	
	@Override
	public float getSpeed() {
		return this.movable.getSpeed();
	}

	@Override
	public void rebound() {
		this.movable.rebound();
	}

	@Override
	public void stop() {
		this.movable.stop();
	}

	@Override
	public void move() {
		this.movable.move();
	}

}