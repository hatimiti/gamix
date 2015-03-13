package com.github.hatimiti.gamix.app.game.field.network.client;

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

import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.network.handler.EntityClientHandler;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.util._Util;

public class EntityClient implements Runnable {

	private static final Logger LOG = _Util.getLogger();

	private final InetSocketAddress serverAddress;
	private final int updateInterval;
	private final EntityUpdateListener entityUpdateListener;

	public EntityClient(
			final InetSocketAddress serverAddress,
			final int updateInterval,
			final EntityUpdateListener entityUpdateListener) {

		this.serverAddress = serverAddress;
		this.updateInterval = updateInterval;
		this.entityUpdateListener = entityUpdateListener;
	}

	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(this.updateInterval);
			} catch (InterruptedException e1) {
			}

			EventLoopGroup group = new NioEventLoopGroup();
			try {
				Bootstrap b = new Bootstrap();
				b.group(group)
					.channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, false)
					.handler(new EntityClientHandler());

				Channel ch = b.bind(0).sync().channel();

				Player p = this.entityUpdateListener.getLatestPlayer();

				if (EntityId.INIT.equals(p.getEntityId())) {
					continue;
				}

				ExchangeEntityClientJson json
					= this.entityUpdateListener.createLatestEntity();

				if (EntityId.NONE.equals(p.getEntityId())) {
					p.setEntityId(EntityId.INIT);
				}

				LOG.debug("send json to server: " + JSON.encode(json));

				ch.writeAndFlush(new DatagramPacket(
						Unpooled.copiedBuffer(JSON.encode(json), CharsetUtil.UTF_8),
						this.serverAddress)
				).sync();

				if (!ch.closeFuture().await(this.updateInterval)) {
	                System.err.println("request timed out.");
	            }

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				group.shutdownGracefully();
			}
		}
	}

	public static interface EntityUpdateListener {

		/**
		 * FIXME 最終的には、EntityIdはログイン時に決めるため、
		 * プレイヤーを取得する必要はない
		 */
		public Player getLatestPlayer();

		public ExchangeEntityClientJson createLatestEntity();
	}

}
