package com.github.hatimiti.gamix.app.game.field;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.Sword001;
import com.github.hatimiti.gamix.app.game.field.entity.equipment.weapon.Weapon;
import com.github.hatimiti.gamix.app.game.field.entity.magic.Magic;
import com.github.hatimiti.gamix.app.game.field.entity.support.direction.FacingDirection;
import com.github.hatimiti.gamix.app.support.InputHelpable;


class BattleInputHelper implements InputHelpable {

	private final BattleState state;

	BattleInputHelper(final BattleState state) {
		this.state = state;
	}

	@Override
	public void input(final GameContainer gc) throws SlickException {
		inputMove(gc);
		inputOther(gc);
	}

	private void inputMove(final GameContainer gc) throws SlickException {

		if (this.state.player.isNotNormalStatus()
				|| this.state.player.isAttacking()) {
			return;
		}

		Input input = gc.getInput();
		int keysByte = 0;

		if (input.isKeyDown(Input.KEY_W)) {
			keysByte |= FacingDirection.UP.getValue();
		}
		if (input.isKeyDown(Input.KEY_D)) {
			keysByte |= FacingDirection.RIGHT.getValue();
		}
		if (input.isKeyDown(Input.KEY_S)) {
			keysByte |= FacingDirection.DOWN.getValue();
		}
		if (input.isKeyDown(Input.KEY_A)) {
			keysByte |= FacingDirection.LEFT.getValue();
		}

		if (keysByte == 0 || !gc.hasFocus()) {
			this.state.player.stop();
		} else {
			this.state.player.faceTo(FacingDirection.getBy(keysByte));
			this.state.player.move();
		}
	}

	private void inputOther(final GameContainer gc) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_R)) {
			invokeMagic();
		}
		if (input.isKeyPressed(Input.KEY_E)) {
			equipWeapon();
		}
		if (this.state.player.isStopping()
				&& !this.state.player.isNotNormalStatus()
				&& input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			attack();
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			viewChatDialog();
		}
		if (input.isKeyPressed(Input.KEY_F1)) {
			viewAbilityDialog();
		}
	}

	private void viewAbilityDialog() {
		// アビリティダイアログ表示／非表示
		if (this.state.guiManager.abilityDialog.isVisible()) {
			this.state.guiManager.abilityDialog.hide();
		} else {
			this.state.guiManager.abilityDialog.show(this.state.player.getAbilityParameter());
		}
	}

	private void viewChatDialog() {
		// チャットウィンドウ表示／非表示
		this.state.guiManager.chatDialog.setVisible(
				!this.state.guiManager.chatDialog.isVisible());
	}

	private void attack() {
		// 攻撃
		this.state.player.attack();
	}

	private void equipWeapon() throws SlickException {
		// 武器装備
		Weapon weapon = new Sword001(this.state.player);
		this.state.player.equipWeapon(weapon);
		this.state.clientEntityContainer.addTo(this.state.getNowTile(), weapon);
	}

	private void invokeMagic() throws SlickException {
		// 魔法
		Magic magic = new Magic(this.state.player.getCenterPoint());
		this.state.clientEntityContainer.addTo(this.state.getNowTile(), magic);
		magic.faceTo(this.state.player.getDirection());
		magic.move();
	}

}