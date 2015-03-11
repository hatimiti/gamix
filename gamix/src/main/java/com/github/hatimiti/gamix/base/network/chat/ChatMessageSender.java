package com.github.hatimiti.gamix.base.network.chat;

public interface ChatMessageSender {

	/**
	 * サーバーへの送信
	 * @return
	 */
	public String sendMessage();

	/**
	 * サーバーからの返答
	 */
	public void receiveMessage(String message);

}
