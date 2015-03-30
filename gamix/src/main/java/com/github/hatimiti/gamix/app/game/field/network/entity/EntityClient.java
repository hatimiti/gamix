package com.github.hatimiti.gamix.app.game.field.network.entity;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

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
	
	public void update() {
		if (this.isStarted) {
			return;
		}
		new Thread(this).start();
	}
	
	public synchronized void run() {

		this.isStarted = true;
		
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioDatagramChannel.class)
				.option(ChannelOption.SO_BROADCAST, false)
				.handler(new EntityClientHandler());

			Channel ch = b.bind(0).sync().channel();

			Player player = EntityContainer.getInstance().getPlayer();
			if (EntityId.INIT.equals(player.getEntityId())) {
				return;
			}

			ExchangeEntityClientJson json = createJSON(player);

			if (EntityId.NONE.equals(player.getEntityId())) {
				player.setEntityId(EntityId.INIT);
			}

			LOG.debug("send json to server: " + JSON.encode(json));

			ch.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer(JSON.encode(json), CharsetUtil.UTF_8),
					this.serverAddress)
			).sync();

			ch.closeFuture().await(1000);

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
