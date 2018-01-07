package com.github.hatimiti.gamix.app.support;


public enum GameSceneState {

	LOAD(1),
	MENU(10),
	BATTLE(100),
	SERVER(9999),
	;

	private int id;

	private GameSceneState(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

}