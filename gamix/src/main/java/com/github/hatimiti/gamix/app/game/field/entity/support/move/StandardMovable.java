package com.github.hatimiti.gamix.app.game.field.entity.support.move;

import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;


/**
 * 基本的な動作を実装したMovable
 * 画面上を移動することができる機能が追加される.
 */
public class StandardMovable implements Movable {

	protected BaseEntity origin;
	
	protected MovementState movementState;
	protected int dx;
	protected int dy;
	protected float speed = 2;
	
	protected int reboundDelay;

	public StandardMovable(
			BaseEntity entity) {
		this.origin = entity;
		this.movementState = MovementState.STOP;
	}

	@Override
	public boolean isMoving() {
		return MovementState.MOVE == this.movementState;
	}

	@Override
	public boolean isStopping() {
		return MovementState.STOP == this.movementState;
	}

	@Override
	public float getSpeed() {
		if (isStopping()) {
			return 0;
		}
		return this.speed;
	}

	@Override
	public void move() {

		if (origin.getDirection() == null) {
			stop();
			return;
		}
		
		if (MovementState.REBOUND.equals(this.movementState)) {
			if (--this.reboundDelay < 0) {
				return;
			}
		}

		this.movementState = MovementState.MOVE;
		update();
	}
	
	@Override
	public void rebound() {
		this.reboundDelay = 30;
		this.movementState = MovementState.REBOUND;
		float ts = getSpeed();
		this.setSpeed(ts * 2);
		update();
		this.setSpeed(ts);
	}

	@Override
	public void stop() {
		this.dx = 0;
		this.dy = 0;
		this.movementState = MovementState.STOP;
	}

	protected void update() {

		FacingDirection direction = origin.getDirection();
		float _speed = this.speed;
		float rspeed = (float) Math.sqrt(_speed);
		
		Shape shape = this.origin.getShape();

		switch (direction) {
		case UP:
			this.dx = 0;
			this.dy = (int) _speed;
			shape.setY(shape.getY() - this.dy);
			break;
		case UP_RIGHT:
			this.dx = (int) rspeed;
			this.dy = (int) rspeed;
			shape.setX(shape.getX() + this.dx);
			shape.setY(shape.getY() - this.dy);
			break;
		case UP_LEFT:
			this.dx = (int) rspeed;
			this.dy = (int) rspeed;
			shape.setX(shape.getX() - this.dx);
			shape.setY(shape.getY() - this.dy);
			break;
		case DOWN:
			this.dx = 0;
			this.dy = (int) _speed;
			shape.setY(shape.getY() + this.dy);
			break;
		case DOWN_RIGHT:
			this.dx = (int) rspeed;
			this.dy = (int) rspeed;
			shape.setX(shape.getX() + this.dx);
			shape.setY(shape.getY() + this.dy);
			break;
		case DOWN_LEFT:
			this.dx = (int) rspeed;
			this.dy = (int) rspeed;
			shape.setX(shape.getX() - this.dx);
			shape.setY(shape.getY() + this.dy);
			break;
		case RIGHT:
			this.dx = (int) _speed;
			this.dy = 0;
			shape.setX(shape.getX() + this.dx);
			break;
		case LEFT:
			this.dx = (int) _speed;
			this.dy = 0;
			shape.setX(shape.getX() - this.dx);
			break;
		default:
			break;
		}
	}

	@Override
	public void faceTo(FacingDirection direction) {
		this.origin.faceTo(direction);
	}

	@Override
	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
