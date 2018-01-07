package com.github.hatimiti.gamix.base.network.chat;

public interface ChatMessageSender {

	/**
	 * サーバーへの送信
	 * @return
	 */
	public String send();

	/**
	 * サーバーからの返答
	 */
	public void receive(String message);

}
