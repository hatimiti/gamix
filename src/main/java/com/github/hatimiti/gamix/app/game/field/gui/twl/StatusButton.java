package com.github.hatimiti.gamix.app.game.field.gui.twl;

import de.matthiasmann.twl.Button;

public class StatusButton extends Button {

	public StatusButton(Runnable r) {

		super("STATUS");
		setTheme("button");
		setSize(120, 30);
		addCallback(r);
	}

}