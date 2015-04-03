package com.github.hatimiti.gamix.app.game.field.entity.support.move.mover;

import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;

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
			final Player player) {

		// DO-NOTHING
	}

}