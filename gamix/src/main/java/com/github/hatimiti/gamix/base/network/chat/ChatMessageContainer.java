package com.github.hatimiti.gamix.base.network.chat;

import java.util.HashMap;
import java.util.Map;

public class ChatMessageContainer {

	private static Map<ChatMessageType, ChatMessageContainer> containerMap = new HashMap<>();

	protected Map<String, SimpleContainer> simples = new HashMap<>();
	
	public static ChatMessageContainer getInstance(ChatMessageType type) {
		ChatMessageContainer c = containerMap.get(type);
		if (c == null) {
			c = new ChatMessageContainer();
			containerMap.put(type, new ChatMessageContainer());
		}
		return c;
	}
	
	public synchronized void addMessageTo(String key, String message) {
		SimpleContainer sc = this.simples.get(key);
		if (sc == null) {
			sc = new SimpleContainer(key);
			this.simples.put(key, sc);
		}
		sc.add(message);
	}

	public synchronized String getMessageOf(int key) {
		SimpleContainer sc = this.simples.get(key);
		if (sc == null) {
			return "";
		}
		return sc.toString();
	}

	protected static class SimpleContainer {
		protected String key;
		protected StringBuilder messages;

		protected SimpleContainer(String key) {
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