package com.github.hatimiti.gamix.app.game.field.entity.move.character;

import static com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection.*;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import com.github.hatimiti.gamix.app.game.field.damage.DamageCalculator;
import com.github.hatimiti.gamix.app.game.field.damage.DamageEvent;
import com.github.hatimiti.gamix.app.game.field.entity.BaseEntity;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.EquipmentWearable;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.BaseWeapon;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.EmptyWeapon;
import com.github.hatimiti.gamix.app.game.field.entity.map.MapTile;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.BackWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.FrontWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.LeftWall;
import com.github.hatimiti.gamix.app.game.field.entity.map.shape.RightWall;
import com.github.hatimiti.gamix.app.game.field.entity.move.magic.Magic;
import com.github.hatimiti.gamix.app.game.field.entity.move.support.Movable;
import com.github.hatimiti.gamix.app.game.field.entity.move.support.StandardMovableImpl;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.AttackState;
import com.github.hatimiti.gamix.app.game.field.entity.support.attack.Attackable;
import com.github.hatimiti.gamix.app.game.field.entity.support.collision.CollisionEvent;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.MoveDirectionAnimation;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityParameter;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.AbilityStatus;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.LiveStatus;
import com.github.hatimiti.gamix.app.game.field.entity.support.status.Statusable;
import com.github.hatimiti.gamix.app.game.field.type.live.HP;

public abstract class BaseCharacter
		extends BaseEntity
		implements Attackable, EquipmentWearable, Statusable, Movable {

	protected int characterNumber;

	protected MoveDirectionAnimation moveImage;

	protected AttackState attackState;
	protected BaseWeapon weapon;

	protected LiveStatus liveStatus;
	protected AbilityStatus abilityStatus;

	protected boolean existsInGame;
	
	private Movable movable = new StandardMovableImpl(this);

	/*
	 * constructor
	 */

	public BaseCharacter(
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
	public void attack(final BaseEntity target) {
		if (isAttacking()) {
			return;
		}
		this.stop();
		this.direction = getAttackDirection(target);
		this.attackState = AttackState.ATTACK;
		this.weapon.attack(target);
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

		FacingDirection fd = NONE;
		float rebound = this.getSpeed() * 2;

		if (event.getTarget() instanceof MapTile) {
			fd = collisionMapTile(event);
			faceTo(fd);
			rebound();
			
			stop();

		} else if (event.getTarget() instanceof BaseWeapon) {

			BaseCharacter owner = ((BaseWeapon) event.getTarget()).getOwner();

			if (owner == this) {
				return;
			}

			BaseWeapon weapon = (BaseWeapon) event.getTarget();

			fd = collision(event);

			if (!isNotNormalStatus()) {
				int damage = new DamageCalculator()
					.attack(weapon.getAttack())
					.defence(this.getDefence())
					.self(owner.getAbilityStatus())
					.target(this.getAbilityStatus())
					.calcDamage();
				this.liveStatus.damage(damage);
				DamageEvent de = new DamageEvent(
						weapon, this, damage, event.getCollitionPoint());
				this.damageListeners.notifyDamage(de);
			}
		} else if (event.getTarget() instanceof Magic) {
			if (event.getSelf() instanceof Player) {
				return;
			} else {
				doCollision(event, rebound);
			}
		} else {

			doCollision(event, rebound);
		}

	}

	private void doCollision(final CollisionEvent event, float rebound) {
		this.direction = collision(event);
		faceTo(this.direction);
		move();
	}

	@Override
	public void onCollisionFree() {
		this.stop();
	}

	@Override
	public void equipWeapon(final BaseWeapon weapon) {
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
		AbilityStatus as = this.abilityStatus.create(ap);
		this.abilityStatus = as;
	}

	/*
	 * getter
	 */

	public int getCharacterNumber() {
		return this.characterNumber;
	}

	@Override
	public BaseWeapon getWeapon() {
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

	protected FacingDirection getAttackDirection(final BaseEntity target) {

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
			return target == null || target.getX() < this.getX()
				? LEFT : RIGHT;
		}
	}

	protected FacingDirection collisionMapTile(final CollisionEvent event) {
		if (event.getTargetShape() instanceof BackWall) {
			return FacingDirection.DOWN;
		} else if (event.getTargetShape() instanceof RightWall) {
			return FacingDirection.LEFT;
		} else if (event.getTargetShape() instanceof FrontWall) {
			return FacingDirection.UP;
		} else if (event.getTargetShape() instanceof LeftWall) {
			return FacingDirection.RIGHT;
		}
		return null;
	}

	protected FacingDirection collision(final CollisionEvent event) {
		float px = event.getCollitionPoint().getX();
		float sx = this.getShape().getCenterX();
		int dx = 0x0000;

		if (px < sx) {
			dx = FacingDirection.RIGHT.getValue();
		} else if (sx < px) {
			dx = FacingDirection.LEFT.getValue();
		}

		float py = event.getCollitionPoint().getY();
		float sy = this.getShape().getCenterY();
		int dy = 0x0000;

		if (py < sy) {
			dy = FacingDirection.DOWN.getValue();
		} else if (sy < py) {
			dy = FacingDirection.UP.getValue();
		}
		FacingDirection fd = FacingDirection.getBy(dx | dy);
		return fd;
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