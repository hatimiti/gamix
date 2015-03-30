package com.github.hatimiti.gamix.app.game.field.entity.character;

import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;

/**
 * 自分以外のオンライン上のプレイヤーを表現
 * @author hatimiti
 *
 */
public class OnlinePlayer
		extends Character {

	public OnlinePlayer(
			int characterNumber,
			Point defaultPoint) {
		super(characterNumber, defaultPoint);
	}

	@Override
	public void update(EntityContainer ec) {

		if (isStopping()) {
			return;
		}

		move();
	}

	public void setPoint(Point point) {
		this.shape.setX(point.getX());
		this.shape.setY(point.getY());
	}

}