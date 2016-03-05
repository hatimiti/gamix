package com.github.hatimiti.gamix.app.game.field.entity.equipment;

import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.character.Character;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;

public abstract class BaseEquipment
		extends Entity {

	/** この装備の持ち主 */
	protected Character owner;

	public BaseEquipment(
			Character owner) {

		super(new Rectangle(owner.getCenterPoint().getX(), owner.getCenterPoint().getY(), 0, 0));
		this.owner = owner;
	}

	public Character getOwner() {
		return this.owner;
	}

	@Override
	public boolean existsInGame() {
		return true;
	}

	@Override
	public void onCollision(CollisionEvent event) {
	}

	@Override
	public void onCollisionFree() {
	}

}