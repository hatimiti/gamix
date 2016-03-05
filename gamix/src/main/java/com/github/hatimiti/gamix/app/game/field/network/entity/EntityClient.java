package com.github.hatimiti.gamix.app.game.field.network.entity;

import static com.github.hatimiti.gamix.app.game.field.entity.ClientEntityContainer.clientEntityContainer;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Optional;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;

import com.github.hatimiti.gamix.app.game.field.entity.character.Player;
import com.github.hatimiti.gamix.app.game.field.network.exchange.json.entity.ExchangeEntityClientJson;
import com.github.hatimiti.gamix.app.game.field.type.entity.EntityId;
import com.github.hatimiti.gamix.base.network.TCPClient;
import com.github.hatimiti.gamix.base.util._Util;

public class EntityClient extends TCPClient<ExchangeEntityClientJson> {

	private static final Logger LOG = _Util.getLogger();

	public EntityClient(
			final InetSocketAddress serverAddress,
			final int updateInterval) {

		super(serverAddress, updateInterval, new EntityClientInitializer());
	}

	@Override
	protected Optional<ExchangeEntityClientJson> execute(Channel ch) {

		Player player = clientEntityContainer().getPlayer();

		if (EntityId.INIT.equals(player.getEntityId())) {
			return Optional.empty();
		}

		ExchangeEntityClientJson json = createJSON(player);

		if (EntityId.NONE.equals(player.getEntityId())) {
			player.setEntityId(EntityId.INIT);
		}

		LOG.debug("send json to server: " + JSON.encode(json));

		return Optional.of(json);
	}

	@Override
	protected Object prepareWrite(ExchangeEntityClientJson value) {
		return JSON.encode(value) + "\r\n";
	}

	private ExchangeEntityClientJson createJSON(Player player) {
		ExchangeEntityClientJson json = new ExchangeEntityClientJson();
		// TODO 現在のマップ情報を取得する
		json.m.mid = "M0001";
		json.m.tx = 0;
		json.m.ty = 0;
		json.p.eid = player.getEntityId().getVal();
		json.p.x = player.getPoint().getX();
		json.p.y = player.getPoint().getY();
		json.p.d = player.getDirection().getValue();
		return json;
	}
}
