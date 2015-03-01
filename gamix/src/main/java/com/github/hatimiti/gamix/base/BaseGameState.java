package com.github.hatimiti.gamix.base;

import com.github.hatimiti.gamix.app.support.GameSceneState;
import com.github.hatimiti.gamix.app.util.ImageFactory;
import com.github.hatimiti.gamix.base.gui.twl.BasicTWLGameState;
import com.github.hatimiti.gamix.base.util.MIDIPlayer;
import com.sun.javafx.font.FontFactory;

public abstract class BaseGameState extends BasicTWLGameState {

	protected GameSceneState gameSceneState;
	protected MIDIPlayer midiPlayer;
	protected static FontFactory fontFactory;
	protected static ImageFactory imageFactory;

	public BaseGameState(GameSceneState gameSceneState) {
		this.gameSceneState = gameSceneState;
		this.midiPlayer = MIDIPlayer.createInstance();
	}

	@Override
	public int getID() {
		return this.gameSceneState.getId();
	}

}