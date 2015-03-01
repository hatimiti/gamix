package com.github.hatimiti.gamix.app.game.field.gui.twl;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.github.hatimiti.gamix.app.game.field.entity.support.status.Liveable;
import com.github.hatimiti.gamix.app.game.field.type.live.HP;

import de.matthiasmann.twl.ProgressBar;

public class HPBar
		extends ProgressBar {

	protected Liveable liveable;

	public HPBar() {
		init();
	}

	public void draw(final Graphics g) {
		g.setColor(Color.white);
		g.drawString("HP : " + this.getHP().getVal()
				+ " / " + this.getMaxHP().getVal(), this.getX(), this.getY() + 30);
	}

	public void update(final Liveable liveable) {
		this.liveable = liveable;
		this.setValue(1f * getHP().getVal() / getMaxHP().getVal());
	}

	protected HP getHP() {
		return this.liveable == null
				? HP.ZERO : this.liveable.getHP();
	}

	protected HP getMaxHP() {
		return this.liveable == null
				? HP.ZERO : this.liveable.getMaxHP();
	}

	protected void init() {
		this.setTheme("progressbar");
		this.setSize(250, 25);
		this.setVisible(true);
	}

}