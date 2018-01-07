package com.github.hatimiti.gamix.app.game.field.entity.character;

import static com.github.hatimiti.gamix.app.game.field.entity.ClientEntityContainer.clientEntityContainer;

import org.newdawn.slick.SlickException;
import com.github.hatimiti.gamix.base.util.Point;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.mover.AutoMover;

public class AutoCharacter
		extends Character {

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
			//FIXME EntityContainerの渡し方
			this.autoMover.update(this, clientEntityContainer().getPlayer());
		}

	}

}