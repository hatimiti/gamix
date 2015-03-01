package com.github.hatimiti.gamix.app.game.field.entity.move.character;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.move.auto.AutoMover;

public class AutoCharacter
		extends BaseCharacter {

	protected AutoMover autoMover;

	public AutoCharacter(
			int characterNumber,
			AutoMover autoMover,
			Point defaultPoint) throws SlickException {

		super(characterNumber, defaultPoint);
		this.autoMover = autoMover;
	}

	@Override
	public void update(EntityContainer ec) {

		this.liveStatus.update();

		if (isDeadStatus()) {
			this.existsInGame = false;
		}

		if (isNotNormalStatus()) {
			stop();
		} else {
			this.autoMover.update(this, ec);
		}

	}

}