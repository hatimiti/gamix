package com.github.hatimiti.gamix.app.game.field;

import java.net.InetSocketAddress;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.hatimiti.gamix.app.game.field.damage.DamageEvent;
import com.github.hatimiti.gamix.app.game.field.damage.DamageListener;
import com.github.hatimiti.gamix.app.game.field.entity.character.AutoCharacter;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.entity.label.DamageLabel;
import com.github.hatimiti.gamix.app.game.field.gui.twl.HPBar;
import com.github.hatimiti.gamix.app.game.field.gui.twl.StatusButton;
import com.github.hatimiti.gamix.app.game.field.gui.twl.TextFrame;
import com.github.hatimiti.gamix.app.game.field.gui.twl.ability.AbilityDialog;
import com.github.hatimiti.gamix.app.util.ConstProperty;
import com.github.hatimiti.gamix.base.gui.swing.ChatDialog;
import com.github.hatimiti.gamix.base.gui.twl.RootPane;

import de.matthiasmann.twl.Button;

class BattleGUIManager implements DamageListener {

	ChatDialog chatDialog;

	Button statusButton;
	TextFrame textFrame;
	AbilityDialog abilityDialog;

	private HPBar playerHPBar;
	private HPBar targetHPBar;

	private final BattleState state;

	BattleGUIManager(final BattleState state) {
		this.state = state;
	}

	void init(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

		this.playerHPBar = new HPBar();
		this.playerHPBar.update(this.state.player.getStatus());
		this.targetHPBar = new HPBar();

		this.chatDialog = new ChatDialog(new InetSocketAddress(
				ConstProperty.getInstance().getString("network.server.ip"),
				ConstProperty.getInstance().getInt("network.server.port.chat")));
		this.abilityDialog = new AbilityDialog();
		this.abilityDialog.addListener(this.state);

		this.textFrame = new TextFrame();

		this.statusButton = new StatusButton(() ->
				this.textFrame.setVisible(!this.textFrame.isVisible()));

	}

	void enter(
			final GameContainer gc,
			final StateBasedGame game) throws SlickException {

	}

	void render(
			final GameContainer gc,
			final StateBasedGame game,
			final Graphics g) throws SlickException {

		this.playerHPBar.draw(g);
		this.targetHPBar.draw(g);
	}

	void update(
			final GameContainer gc,
			final StateBasedGame game,
			final int paramInt) throws SlickException {

		setChatDialogVisible(gc);
	}

	void setChatDialogVisible(final GameContainer gc) {
		this.chatDialog.setAlwaysOnTop(gc.hasFocus()
				? this.chatDialog.isVisible() : false);
	}

	RootPane createRootPane(RootPane rp) {
		rp.setTheme("gameui");
		rp.add(this.textFrame);
		rp.add(this.playerHPBar);
		rp.add(this.targetHPBar);
		rp.add(this.statusButton);
		rp.add(this.abilityDialog);
		return rp;
	}

	void layoutRootPane() {
		this.textFrame.setSize(200, 150);
		this.textFrame.setPosition(400, 20);
		this.textFrame.setVisible(false);

		this.playerHPBar.setPosition(30, 30);
		this.targetHPBar.setPosition(350, 30);
		this.statusButton.setPosition(670, 20);
	}

	void updatePlayerHPBar(final DamageEvent event) {
		this.playerHPBar.update(((Player) event.getTo()).getStatus());
	}

	void updateTargetHPBar(final DamageEvent event) {
		AutoCharacter c = (AutoCharacter) event.getTo();
		this.targetHPBar.update(c.getStatus());
		DamageLabel dl = new DamageLabel(event.getDamage(), event.getPoint());
		this.state.entityContainer.addTo(this.state.getNowTile(), dl);
	}

	@Override
	public void notifyDamage(final DamageEvent event) {
		if (event.getTo() instanceof Player) {
			this.updatePlayerHPBar(event);
		} else {
			this.updateTargetHPBar(event);
		}
	}

}
