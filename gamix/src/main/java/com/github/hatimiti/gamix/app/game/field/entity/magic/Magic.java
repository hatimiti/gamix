package com.github.hatimiti.gamix.app.game.field.entity.magic;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.Movable;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.StandardMovable;

public class Magic
		extends Entity
		implements Movable {

	private Movable movable;
	
	public Magic(Point defaultPoint) throws SlickException {
		super(new Ellipse(
				defaultPoint.getX(), defaultPoint.getY(), 10, 10));
		setupMovable(new StandardMovable(this));
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.draw(this.shape);
	}

	@Override
	public void update(EntityContainer ec) {

		if (isStopping()) {
			return;
		}
		
		move();
	}
	
	protected void setupMovable(Movable movable) {
		this.movable = movable;
	}

	@Override
	public void onCollision(CollisionEvent event) {
	}

	@Override
	public void onCollisionFree() {
	}

	@Override
	public boolean existsInGame() {
		return true;
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