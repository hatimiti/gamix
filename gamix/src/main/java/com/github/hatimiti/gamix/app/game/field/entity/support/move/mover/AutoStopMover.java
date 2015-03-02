package com.github.hatimiti.gamix.app.game.field.entity.support.move.mover;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;

/**
 * 停止した状態の移動戦略
 * @author hatimiti
 *
 */
public class AutoStopMover
		implements AutoMover {

	@Override
	public void update(
			final AutoCharacter target,
			final EntityContainer ec) {

		// DO-NOTHING
	}

}