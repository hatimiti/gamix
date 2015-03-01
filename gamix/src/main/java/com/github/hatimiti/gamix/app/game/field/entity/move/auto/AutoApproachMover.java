package com.github.hatimiti.gamix.app.game.field.entity.move.auto;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;

/**
 * プレイヤーに対して自動で近づいてくる移動戦略
 * @author hatimiti
 *
 */
public class AutoApproachMover
		implements AutoMover {

	@Override
	public void update(
			final AutoCharacter target,
			final EntityContainer ec) {

		float px = ec.getPlayer().getShape().getCenterX();
		float sx = target.getShape().getCenterX();
		int dx = 0x0000;

		if (sx < px) {
			dx = FacingDirection.RIGHT.getValue();
		} else if (px < sx) {
			dx = FacingDirection.LEFT.getValue();
		}

		float py = ec.getPlayer().getShape().getCenterY();
		float sy = target.getShape().getCenterY();
		int dy = 0x0000;

		if (sy < py) {
			dy = FacingDirection.DOWN.getValue();
		} else if (py < sy) {
			dy = FacingDirection.UP.getValue();
		}

		FacingDirection d = FacingDirection.getBy(dx | dy);
		target.faceTo(d);
		target.move();

	}

}