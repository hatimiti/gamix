package com.github.hatimiti.gamix.app.game.field.entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;

public class EmptyEntity extends Entity {

	public EmptyEntity() {
		super(new Rectangle(0, 0, 0, 0));
	}

	@Override
	public void onCollision(CollisionEvent event) {
		// Do Nothing
	}

	@Override
	public void onCollisionFree() {
		// Do Nothing
	}

	@Override
	public void draw(Graphics g) {
		// Do Nothing
	}

	@Override
	public void update(EntityContainer ec) {
		// Do Nothing
	}

	@Override
	public boolean existsInGame() {
		// Do Nothing
		return false;
	}

}