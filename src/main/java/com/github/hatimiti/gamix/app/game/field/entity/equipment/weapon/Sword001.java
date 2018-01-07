package com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon;

import org.newdawn.slick.SlickException;
import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.character.Character;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;

public class Sword001 extends Weapon {

	public Sword001(Character owner)
			throws SlickException {
		super(owner);
	}

	@Override
	public int getWeaponNumber() {
		return 1;
	}

	@Override
	public int getMaxMotion() {
		return 3;
	}

	@Override
	public int getDelay() {
		return 20;
	}

	@Override
	protected Shape[] getCollisionShapesByMotion() {
		int adjust = FacingDirection.LEFT == this.direction ? -30 : 20;
		return new Shape[] {
				new Rectangle(0, 0, 0, 0),
				new Rectangle(this.getCenterPoint().getX() + adjust, this.owner.getCenterPoint().getY() - 30, 10, 60),
				new Rectangle(0, 0, 0, 0),
		};
	}

	@Override
	protected Point[] getDrawPoints4AdjustByMotion() {
		return new Point[] {
				Point.at(14, 17),
				Point.at(16, 4),
				Point.at(21, -8),
		};
	}

	@Override
	public int getAttack() {
		return 20;
	}

}