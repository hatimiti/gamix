package com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon;

import org.newdawn.slick.Graphics;
import com.github.hatimiti.gamix.base.util.Point;
import org.newdawn.slick.geom.Shape;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.Character;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.BaseEquipment;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.AttackDirectionAnimation;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.AttackState;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.Attackable;

public abstract class Weapon
		extends BaseEquipment
		implements Attackable {

	protected AttackDirectionAnimation attackImage;

	protected int nowMotion;
	protected int nowDelay;
	protected AttackState attackState;

	public Weapon(
			Character owner) {

		super(owner);
		this.attackImage = new AttackDirectionAnimation(this, 32, 32);
		this.attackState = AttackState.STOP;
	}

	@Override
	public void update(EntityContainer ec) {

		if (!this.isAttacking()) {
			return;
		}

		this.getShape().setX(this.owner.getCenterPoint().getX());
		this.getShape().setY(this.owner.getCenterPoint().getY());

		if (getDelay() <= ++this.nowDelay) {
			this.nowDelay = 0;
			if (getMaxMotion() <= ++this.nowMotion) {
				this.nowMotion = 0;
				this.attackState = AttackState.STOP;
			}
		}
	}

	@Override
	public void draw(Graphics g) {

		if (!isAttacking()) {
			return;
		}

//		g.draw(getCollisionShapeByMotion());

		this.attackImage.draw(g, this.getDirection(), this.owner.getPoint());
	}

	@Override
	public final Shape[] getCollisionShapes() {
		return new Shape[] { getCollisionShapeByMotion() };
	}

	public final Shape getCollisionShapeByMotion() {
		return getCollisionShapesByMotion()[this.nowMotion];
	}

	public final Point getDrawPoint4AdjustByMotion() {
		return getDrawPoints4AdjustByMotion()[this.nowMotion];
	}

	public abstract int getWeaponNumber();
	/** 武器のモーション数 */
	public abstract int getMaxMotion();
	/** 武器を振る速度(少ないほうが速い) */
	public abstract int getDelay();

	public int getNowMotion() {
		return this.nowMotion;
	}

	@Override
	public void attack() {
		this.nowDelay = 0;
		this.nowMotion = 0;
		this.attackState = AttackState.ATTACK;
		this.direction = this.owner.getDirection();
	}

	@Override
	public AttackState getAttackState() {
		return this.attackState;
	}

	@Override
	public boolean isAttacking() {
		return AttackState.ATTACK == this.attackState;
	}

	/**
	 * モーション毎の当たり判定シェイプを取得
	 */
	protected abstract Shape[] getCollisionShapesByMotion();

	/**
	 * モーション毎の武器描画位置調整
	 */
	protected abstract Point[] getDrawPoints4AdjustByMotion();

	/**
	 * 攻撃力を取得
	 */
	public abstract int getAttack();
}