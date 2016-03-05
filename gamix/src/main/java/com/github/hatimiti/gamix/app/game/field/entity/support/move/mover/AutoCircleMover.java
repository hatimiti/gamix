package com.github.hatimiti.gamix.app.game.field.entity.support.move.mover;

import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
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
			final Player player) {

		target.faceTo(FacingDirection.DOWN);
		target.move();

		int r = 70;

		double radian = Math.PI / 180 * this.degree;
		float x = (float) (player.getPoint().getX() + r * Math.cos(radian));
		float y = (float) (player.getPoint().getY() + r * Math.sin(radian));
		this.degree = this.degree + 3 % 360;

		target.getShape().setX(x);
		target.getShape().setY(y);

//		target.updateByDirection(d, 1);

	}

}