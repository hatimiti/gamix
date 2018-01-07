package com.github.hatimiti.gamix.base;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public abstract class BaseGame {

	public BaseGame() {
		initContainer();
	}
	
	protected void initContainer() {
		SingletonS2ContainerFactory.init();
	}

}
