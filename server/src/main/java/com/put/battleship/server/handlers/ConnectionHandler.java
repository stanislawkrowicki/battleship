package com.put.battleship.server.handlers;

import com.put.battleship.server.ContextManager;
import com.put.battleship.server.Player;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class ConnectionHandler {
    public static void mapPlayerToChannel(UUID playerUUID, ChannelHandlerContext ctx) {
        var ch = ctx.channel();
        var attr = ch.attr(Player.PLAYER_UUID_KEY);
        ch.attr(Player.PLAYER_UUID_KEY).set(playerUUID.toString());
        ContextManager.addContext(ctx);
    }
}
