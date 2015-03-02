package com.github.hatimiti.gamix.app.game.field.entity.support.attack;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.Weapon;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.EmptyWeapon;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.BaseDirectionAnimation;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.util.ImageFactory;


public class AttackDirectionAnimation
		extends BaseDirectionAnimation {

	protected Animation leftOwnerAnimation;
	protected Animation rightOwnerAnimation;

	protected Animation leftWeaponAnimation;
	protected Animation rightWeaponAnimation;

	protected Animation leftEffectAnimation;
	protected Animation rightEffectAnimation;

	protected SpriteSheet weaponSpriteSheet;
	protected SpriteSheet effectSpriteSheet;

	protected Weapon weapon;

	/**
	 *
	 * @param wn 武器No.
	 * @param on オーナーNo.
	 * @param w width
	 * @param h height
	 * @param reboundDelay 武器速度
	 */
	public AttackDirectionAnimation(
			Weapon weapon, int w, int h) {

		super(ImageFactory.getInstance().getCharacterImage(weapon.getOwner().getCharacterNumber()), w, h);
		this.weapon = weapon;

		final int L = 15;
		final int R = 18;
		final int delay = weapon.getDelay();

		this.leftOwnerAnimation = new Animation(
				this.spriteSheet,
				new int[]{ L + 0, 0, L + 1, 0, L + 2, 0},
				new int[]{ delay, delay, delay });
		this.leftOwnerAnimation.setLooping(false);

		this.rightOwnerAnimation = new Animation(
				this.spriteSheet,
				new int[]{ R + 2, 0, R + 1, 0, R + 0, 0},
				new int[]{ delay, delay, delay });
		this.leftOwnerAnimation.setLooping(false);

		if (this.weapon instanceof EmptyWeapon) {
			return;
		}

		this.weaponSpriteSheet = createSpriteSheet(
				ImageFactory.getInstance().getWeaponImage(weapon.getWeaponNumber()), w, h);

		this.effectSpriteSheet = createSpriteSheet(
				ImageFactory.getInstance().getEffectImage(weapon.getWeaponNumber()), w, h);

		this.leftWeaponAnimation = new Animation(
				this.weaponSpriteSheet,
				new int[]{ 0, 0, 1, 0, 2, 0},
				new int[]{ delay, delay, delay });
		this.leftWeaponAnimation.setLooping(false);

		this.rightWeaponAnimation = new Animation(
				this.weaponSpriteSheet,
				new int[]{ 0, 1, 1, 1, 2, 1},
				new int[]{ delay, delay, delay });
		this.rightWeaponAnimation.setLooping(false);


		this.leftEffectAnimation = new Animation(
				this.effectSpriteSheet,
				new int[]{ 0, 0, 1, 0, 2, 0},
				new int[]{ delay, delay, delay });
		this.leftEffectAnimation.setLooping(false);

		this.rightEffectAnimation = new Animation(
				this.effectSpriteSheet,
				new int[]{ 0, 1, 1, 1, 2, 1},
				new int[]{ delay, delay, delay });
		this.rightEffectAnimation.setLooping(false);

	}

	@Override
	public void draw(Graphics g, FacingDirection direction, float x, float y) {

		int motion = this.weapon.getNowMotion();
		Point aj = this.weapon.getDrawPoint4AdjustByMotion();

		switch (direction) {
		case LEFT:

			this.leftOwnerAnimation.setCurrentFrame(motion);
			g.drawImage(this.leftOwnerAnimation.getCurrentFrame(), x, y);

			if (this.weapon instanceof EmptyWeapon) {
				return;
			}

			this.leftWeaponAnimation.setCurrentFrame(motion);
			this.leftEffectAnimation.setCurrentFrame(motion);
			g.drawImage(this.leftWeaponAnimation.getCurrentFrame(), x - aj.getX(), y -+ aj.getY());
			g.drawImage(this.leftEffectAnimation.getCurrentFrame(), x - aj.getX(), y -+ aj.getY());
			return;
		case RIGHT:

			this.rightOwnerAnimation.setCurrentFrame(motion);
			g.drawImage(this.rightOwnerAnimation.getCurrentFrame(), x, y);

			if (this.weapon instanceof EmptyWeapon) {
				return;
			}

			this.rightWeaponAnimation.setCurrentFrame(motion);
			this.rightEffectAnimation.setCurrentFrame(motion);
			g.drawImage(this.rightWeaponAnimation.getCurrentFrame(), x + aj.getX(), y - aj.getY());
			g.drawImage(this.rightEffectAnimation.getCurrentFrame(), x + aj.getX(), y - aj.getY());
			return;
		default:
		}
	}

}