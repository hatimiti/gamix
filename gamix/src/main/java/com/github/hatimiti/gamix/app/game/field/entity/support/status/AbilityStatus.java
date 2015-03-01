package com.github.hatimiti.gamix.app.game.field.entity.support.status;

/**
 * 腕力やすばやさなどのステータスを表す。
 * @author hatimiti
 *
 */
public class AbilityStatus {

	/** 物理攻撃力 */
	protected int physicalAttack;

	/** 物理防御力 */
	protected int physicalDefense;

	/** 魔法攻撃力 */
	protected int magicAttack;

	/** 魔法防御力 */
	protected int magicDefense;

	/** 回避力 */
	protected int avoid;

	/** 移動速度 */
	protected int moveSpeed;

	public AbilityStatus(
			int physicalAttack,
			int physicalDefense,
			int magicAttack,
			int magicDefense,
			int avoid,
			int moveSpeed) {

		this.physicalAttack = physicalAttack;
		this.physicalDefense = physicalDefense;
		this.magicAttack = magicAttack;
		this.magicDefense = magicDefense;
		this.avoid = avoid;
		this.moveSpeed = moveSpeed;
	}

	public AbilityStatus plus(AbilityStatus o) {
		return getAddedStatus(
				o.physicalAttack,
				o.physicalDefense,
				o.magicAttack,
				o.magicDefense,
				o.avoid,
				o.moveSpeed);
	}

	public AbilityStatus create(AbilityParameter ap) {
		return new AbilityStatus(
				ap.getPhysicalAttack(),
				this.physicalDefense,
				this.magicAttack,
				this.magicDefense,
				this.avoid,
				this.moveSpeed);
	}

	protected AbilityStatus getAddedStatus(
			int physicalAttack,
			int physicalDefense,
			int magicAttack,
			int magicDefense,
			int avoid,
			int moveSpeed) {

		return new AbilityStatus(
				getPhysicalAttack() + physicalAttack,
				getPhysicalDefense() + physicalDefense,
				getMagicAttack() + magicAttack,
				getMagicDefense() + magicDefense,
				getAvoid() + avoid,
				getMoveSpeed() + moveSpeed);
	}

	public int getPhysicalAttack() {
		return physicalAttack;
	}

	public int getPhysicalDefense() {
		return physicalDefense;
	}

	public int getMagicAttack() {
		return magicAttack;
	}

	public int getMagicDefense() {
		return magicDefense;
	}

	public int getAvoid() {
		return avoid;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

}
