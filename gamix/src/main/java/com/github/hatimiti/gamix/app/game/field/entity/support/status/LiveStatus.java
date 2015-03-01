package com.github.hatimiti.gamix.app.game.field.entity.support.status;

import static com.github.hatimiti.gamix.app.game.field.entity.support.status.LiveState.*;

import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.type.live.HP;

public class LiveStatus implements Liveable {

	protected HP maxHP;
	protected HP hp;
	protected int maxST;
	protected int st;

	protected int maxMP;
	protected int mp;

	protected LiveDirectionAnimation liveImage;
	protected LiveState liveState;
	protected int nowMotion;
	protected int nowDelay;

	/*
	 * constructor
	 */

	public LiveStatus(
			final int characterNumber,
			final HP maxHP, final HP hp, final int maxMP, final int mp, final int maxST, final int st) {

		this.maxHP = maxHP;
		this.hp = hp;
		this.maxMP = maxMP;
		this.mp = mp;
		this.maxST = maxST;
		this.st = st;

		this.liveState = LiveState.NORMAL;
		this.liveImage = new LiveDirectionAnimation(this, characterNumber, 32, 32);
	}

	/*
	 * public
	 */

	@Override
	public void damage(final int damage) {
		setHP(new HP(getHP().getVal() - damage));
		this.liveState = getHP().getVal() <= 0 ? NEARLY_DEAD : DAMAGING;
	}

	public void draw(final Graphics g, final FacingDirection direction, final float x, final float y) {
		this.liveImage.draw(g, direction, x, y);
	}

	public void update() {
		if (isNormal()) {
			return;
		}
		updateNearyDead();
		updateDamaging();
	}

	/*
	 * getter
	 */

	@Override
	public HP getMaxHP() {
		return this.maxHP;
	}
	@Override
	public HP getHP() {
		return this.hp;
	}
	@Override
	public int getMaxST() {
		return this.maxST;
	}

	@Override
	public int getST() {
		return this.st;
	}

	public int getMaxMP() {
		return this.maxMP;
	}

	public int getMP() {
		return this.mp;
	}

	@Override
	public boolean isNormal() {
		return NORMAL == this.liveState;
	}

	@Override
	public boolean isDamaging() {
		return DAMAGING == this.liveState;
	}

	@Override
	public boolean isNearlyDead() {
		return NEARLY_DEAD == this.liveState;
	}

	@Override
	public boolean isDead() {
		return DEAD == this.liveState;
	}

	@Override
	public int getNowMotion() {
		return this.nowMotion;
	}

	/*
	 * protected
	 */

	protected void updateNearyDead() {
		if (!isNearlyDead()) {
			return;
		}
		if (5 <= ++this.nowDelay) {
			this.nowDelay = 0;
			if (3 <= ++this.nowMotion) {
				this.nowMotion = 0;
				this.liveState = LiveState.DEAD;
			}
		}
	}

	protected void updateDamaging() {
		if (!isDamaging()) {
			return;
		}
		if (8 <= ++this.nowDelay) {
			this.nowDelay = 0;
			if (3 <= ++this.nowMotion) {
				this.nowMotion = 0;
				this.liveState = LiveState.NORMAL;
			}
		}
	}

	protected void setHP(final HP hp) {
		HP _hp = hp;
		if (hp.isMinus()) {
			_hp = new HP(0);
		} else if (this.maxHP.compareTo(hp) < 0) {
			_hp = this.maxHP;
		}
		this.hp = _hp;
	}

	protected void setMP(final int mp) {
		int _mp = mp;
		if (mp < 0) {
			_mp = 0;
		} else if (this.maxMP < mp) {
			_mp = this.maxMP;
		}
		this.mp = _mp;
	}

}
