package com.github.hatimiti.gamix.app.game.field.entity.character;

import static com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection.*;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.damage.DamageCalculator;
import com.github.hatimiti.gamix.app.game.field.damage.DamageEvent;
import com.github.hatimiti.gamix.app.game.field.entity.Entity;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.EquipmentWearable;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.EmptyWeapon;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.Weapon;
import com.github.hatimiti.gamix.app.game.field.entity.magic.Magic;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.BackWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.FrontWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.LeftWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.RightWall;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.AttackState;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.Attackable;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.MoveDirectionAnimation;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.Movable;
import com.github.hatimiti.gamix.app.game.field.entity.support.move.StandardMovable;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityStatus;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.LiveStatus;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.Statusable;
import com.github.hatimiti.gamix.app.game.field.type.live.HP;

public abstract class Character
		extends Entity
		implements Attackable, EquipmentWearable, Statusable, Movable {

	protected int characterNumber;

	protected MoveDirectionAnimation moveImage;

	protected AttackState attackState;
	protected Weapon weapon;

	protected LiveStatus liveStatus;
	protected AbilityStatus abilityStatus;

	protected boolean existsInGame;
	
	private Movable movable = new StandardMovable(this);

	/*
	 * constructor
	 */

	public Character(
			final int characterNumber,
			final Point defaultPoint) throws SlickException {

		super(new Rectangle(
				defaultPoint.getX(), defaultPoint.getY(), 20, 32));

		this.characterNumber = characterNumber;
		this.liveStatus = new LiveStatus(
				characterNumber, new HP(100), new HP(100), 10, 10, 30, 30);
		this.abilityStatus = new AbilityStatus(10, 1, 1, 1, 1, 1);

		this.moveImage = new MoveDirectionAnimation(characterNumber, 32, 32);

		this.weapon = new EmptyWeapon(this);
		this.direction = FacingDirection.DOWN;
		this.attackState = AttackState.STOP;

		this.existsInGame = true;
		
		this.stop();
	}

	/*
	 * public
	 */

	@Override
	public void attack() {
		if (isAttacking()) {
			return;
		}
		this.stop();
		this.direction = getAttackDirection();
		this.attackState = AttackState.ATTACK;
		this.weapon.attack();
	}

	@Override
	public void draw(final Graphics g) {

//		g.setColor(Color.blue);
//		g.draw(this.shape);

		if (isNotNormalStatus()) {
			this.liveStatus.draw(g, this.direction, getX(), getY());
		} else if (isAttacking()) {
			this.weapon.draw(g);
		} else {
			this.moveImage.draw(g, this.direction, getX(), getY());
		}
	}

	@Override
	public void onCollision(final CollisionEvent event) {

		float rebound = this.getSpeed() * 2;

		if (event.getTarget() instanceof MapTile) {
			collisionWithMap(event);
		} else if (event.getTarget() instanceof Weapon) {
			collisionWithWeapon(event);
		} else if (event.getTarget() instanceof Magic) {
			collisionWithMagic(event, rebound);
		} else {
			doCollision(event, rebound);
		}

	}

	private void collisionWithMagic(final CollisionEvent event, float rebound) {
		
		if (event.getSelf() instanceof Player) {
			return;
		}
		
		doCollision(event, rebound);
	}

	private void collisionWithMap(final CollisionEvent event) {
		faceTo(faceByMapTile(event));
		rebound();
		stop();
	}

	private void collisionWithWeapon(final CollisionEvent event) {
		
		Weapon weapon = (Weapon) event.getTarget();
		Character owner = weapon.getOwner();

		if (owner == this || isNotNormalStatus()) {
			// 自分自身の武器との衝突、または通常ステータスで無い場合は無視
			return;
		}

		int amount = new DamageCalculator()
			.attack(weapon.getAttack())
			.defence(this.getDefence())
			.self(owner.getAbilityStatus())
			.target(this.getAbilityStatus())
			.calcDamage();
		
		this.liveStatus.damage(amount);
		this.damageListeners.notifyDamage(
				new DamageEvent(weapon, this, amount, event.getCollitionPoint()));
	}

	private void doCollision(final CollisionEvent event, float rebound) {
		faceTo(faceBy(event));
		move();
	}

	@Override
	public void onCollisionFree() {
		this.stop();
	}

	@Override
	public void equipWeapon(final Weapon weapon) {
		unequipWeapon();
		this.weapon = weapon;
	}

	@Override
	public void unequipWeapon() {
		this.weapon = new EmptyWeapon(this);
	}

	@Override
	public boolean existsInGame() {
		return this.existsInGame;
	}

	public void levelUp(final AbilityParameter ap) {
		this.abilityStatus = this.abilityStatus.of(ap);
	}

	/*
	 * getter
	 */

	public int getCharacterNumber() {
		return this.characterNumber;
	}

	@Override
	public Weapon getWeapon() {
		return this.weapon;
	}

	@Override
	public LiveStatus getStatus() {
		return this.liveStatus;
	}

	public AbilityStatus getAbilityStatus() {
		return this.abilityStatus;
	}

	/**
	 * 攻撃力を取得
	 */
	public int getAttack() {
		return this.weapon.getAttack();
	}

	public int getDefence() {
		return 1;
	}

	@Override
	public AttackState getAttackState() {
		return this.attackState;
	}

	@Override
	public boolean isAttacking() {
//		return AttackState.ATTACK == this.attackState;
		return this.weapon.isAttacking();
	}

	public boolean isNotNormalStatus() {
		return !this.liveStatus.isNormal();
	}

	public boolean isDeadStatus() {
		return this.liveStatus.isDead();
	}

	/*
	 * protected
	 */

	protected FacingDirection getAttackDirection() {

		switch (this.direction) {
		case LEFT:
		case UP_LEFT:
		case DOWN_LEFT:
			return LEFT;
		case RIGHT:
		case UP_RIGHT:
		case DOWN_RIGHT:
			return RIGHT;
		default:
			// TODO 上下を向いている場合はどちらに攻撃するか
			return LEFT;
		}
	}

	protected FacingDirection faceByMapTile(final CollisionEvent event) {
		if (event.getTargetShape() instanceof BackWall) {
			return DOWN;
		} else if (event.getTargetShape() instanceof RightWall) {
			return LEFT;
		} else if (event.getTargetShape() instanceof FrontWall) {
			return UP;
		} else if (event.getTargetShape() instanceof LeftWall) {
			return RIGHT;
		}
		return null;
	}

	protected FacingDirection faceBy(final CollisionEvent event) {
		return FacingDirection.getBy(
				faceXBy(event).getValue() | faceYBy(event).getValue());
	}

	private FacingDirection faceXBy(final CollisionEvent event) {
		int cx = event.getCenterX();
		int sx = this.getCenterX();
		return (cx < sx) ? RIGHT
			: (sx < cx) ? LEFT
			: NONE;
	}
	
	private FacingDirection faceYBy(final CollisionEvent event) {
		int cy = event.getCenterY();
		int sy = this.getCenterY();
		return (cy < sy) ? DOWN
			: (sy < cy) ? UP
			: NONE;
	}

	/*
	 * Movableの委譲処理
	 */
	
	@Override
	public boolean isMoving() {
		return this.movable.isMoving();
	}

	@Override
	public boolean isStopping() {
		return this.movable.isStopping();
	}

	@Override
	public void setSpeed(float speed) {
		this.movable.setSpeed(speed);
	}
	
	@Override
	public float getSpeed() {
		return this.movable.getSpeed();
	}

	@Override
	public void rebound() {
		this.movable.rebound();
	}

	@Override
	public void stop() {
		this.movable.stop();
	}
	
	@Override
	public void move() {
		this.movable.move();
		this.attackState = AttackState.STOP;
	}

}