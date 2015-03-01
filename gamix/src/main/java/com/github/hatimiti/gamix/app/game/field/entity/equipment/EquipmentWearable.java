package com.github.hatimiti.gamix.app.game.field.entity.equipment;

import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.BaseWeapon;

public interface EquipmentWearable {

	/** 武器を装備する */
	void equipWeapon(BaseWeapon weapon);
	/** 武器を外す */
	void unequipWeapon();
	/** 装備中の武器を取得する */
	BaseWeapon getWeapon();

}