package com.github.hatimiti.gamix.app.util;

import com.github.hatimiti.gamix.base.util.PropertyLoader;


public class ConstProperty extends PropertyLoader {

	private static ConstProperty instance;

	private ConstProperty() {
		super("const");
	}

	public static ConstProperty getInstance() {
		if (instance == null) {
			instance = new ConstProperty();
		}
		return instance;
	}

}
