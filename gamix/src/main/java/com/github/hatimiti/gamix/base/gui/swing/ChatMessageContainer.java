package com.github.hatimiti.gamix.base.gui.swing;

import java.util.HashMap;
import java.util.Map;

public class ChatMessageContainer {

	protected Map<Integer, SimpleContainer> container;

	public ChatMessageContainer() {
		this.container = new HashMap<>();
	}

	public synchronized void addMessageTo(int key, String message) {
		SimpleContainer sc = this.container.get(key);
		if (sc == null) {
			sc = new SimpleContainer(key);
			this.container.put(key, sc);
		}
		sc.add(message);
	}

	public synchronized String getMessageOf(int key) {
		SimpleContainer sc = this.container.get(key);
		if (sc == null) {
			return "";
		}
		return sc.toString();
	}

	protected class SimpleContainer {
		protected int key;
		protected StringBuilder messages;

		SimpleContainer(int key) {
			this.key = key;
			this.messages = new StringBuilder();
		}

		protected void add(String message) {
			this.messages.append(message).append(System.lineSeparator());
		}

		public String toString() {
			return this.messages.toString();
		}
	}

}