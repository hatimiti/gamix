package com.github.hatimiti.gamix.app.game.field.network.entity;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.EntityContainer;
import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.util._Util;

public class EntityClient implements Runnable {

	private static final Logger LOG = _Util.getLogger();

	private final InetSocketAddress serverAddress;
	private final int updateInterval;
	private boolean isStarted;

	public EntityClient(
			final InetSocketAddress serverAddress,
			final int updateInterval) {

		this.serverAddress = serverAddress;
		this.updateInterval = updateInterval;
	}
	
	public void start() {
		this.isStarted = true;
		new Thread(this).start();
	}
	
	public synchronized void run() {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new EntityClientInitializer());

			Channel ch = b.connect(this.serverAddress).sync().channel();

			for (;;) {
				
				Thread.sleep(20);
			
				Player player = EntityContainer.getInstance().getPlayer();
				if (EntityId.INIT.equals(player.getEntityId())) {
					return;
				}
	
				ExchangeEntityClientJson json = createJSON(player);
	
				if (EntityId.NONE.equals(player.getEntityId())) {
					player.setEntityId(EntityId.INIT);
				}
	
				LOG.debug("send json to server: " + JSON.encode(json));
	
				ChannelFuture lastWriteFuture = ch.writeAndFlush(JSON.encode(json) + "\r\n");
	
				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}

			}
			
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			this.isStarted = false;
			group.shutdownGracefully();
		}
	}

	private ExchangeEntityClientJson createJSON(Player player) {
		ExchangeEntityClientJson json = new ExchangeEntityClientJson();
		// TODO 現在のマップ情報を取得する
		json.m.mid = "M0001";
		json.m.tx = 0;
		json.m.ty = 0;
		json.p.eid = player.getEntityId().getVal();
		json.p.x = player.getX();
		json.p.y = player.getY();
		json.p.d = player.getDirection().getValue();
		return json;
	}

}
