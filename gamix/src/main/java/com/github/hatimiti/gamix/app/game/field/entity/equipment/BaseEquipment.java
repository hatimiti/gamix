package com.github.hatimiti.gamix.app.game.field.entity.equipment;

import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.move.character.BaseCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;

public abstract class BaseEquipment
		extends BaseEntity {

	/** この装備の持ち主 */
	protected BaseCharacter owner;

	public BaseEquipment(
			BaseCharacter owner) {

		super(new Rectangle(owner.getCenterX(), owner.getCenterY(), 0, 0));
		this.owner = owner;
	}

	public BaseCharacter getOwner() {
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