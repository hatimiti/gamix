package com.github.hatimiti.gamix.app.game.field.entity.move.auto;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;

/**
 * プレイヤーの周りを回る移動戦略
 * @author hatimiti
 *
 */
public class AutoCircleMover
		implements AutoMover {

	protected int degree;

	@Override
	public void update(
			final AutoCharacter target,
			final EntityContainer ec) {

		target.faceTo(FacingDirection.DOWN);
		target.move();

		int r = 70;

		double radian = Math.PI / 180 * this.degree;
		float x = (float) (ec.getPlayer().getX() + r * Math.cos(radian));
		float y = (float) (ec.getPlayer().getY() + r * Math.sin(radian));
		this.degree = this.degree + 3 % 360;

		target.getShape().setX(x);
		target.getShape().setY(y);

//		target.updateByDirection(d, 1);

	}

}