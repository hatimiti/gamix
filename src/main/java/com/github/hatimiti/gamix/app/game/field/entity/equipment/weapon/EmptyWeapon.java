package com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon;

import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.character.Character;

public class EmptyWeapon extends Weapon {

	public EmptyWeapon(Character owner) {
		super(owner);
	}

	@Override
	public int getWeaponNumber() {
		return 0;
	}

	@Override
	public int getMaxMotion() {
		return 1;
	}

	@Override
	public int getDelay() {
		return 10;
	}

	@Override
	protected Shape[] getCollisionShapesByMotion() {
		return new Shape[] {
				new Rectangle(0, 0, 0, 0),
		};
	}

	@Override
	protected Point[] getDrawPoints4AdjustByMotion() {
		return new Point[] {
			Point.at(0, 0),
		};
	}

	@Override
	public int getAttack() {
		return 0;
	}

}