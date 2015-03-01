package com.github.hatimiti.gamix.app.game.field.entity.move.support;

import org.newdawn.slick.util.pathfinding.Mover;

import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;

/**
 * このインターフェイスを実装したクラスは、
 * 画面上を移動することができる機能が追加される.
 */
public interface Movable extends Mover {

	/** このオブジェクトが移動中かどうを返す. */
	public boolean isMoving();
	/** このオブジェクトが静止中かどうかを返す. */
	public boolean isStopping();

	/** このオブジェクトが進むスピード(速度)を返す */
	public void setSpeed(float speed);
	
	/** このオブジェクトが進むスピード(速度)を返す */
	public float getSpeed();

	public void faceTo(FacingDirection direction);
	
	public void move();
	public void rebound();
	public void stop();
}
