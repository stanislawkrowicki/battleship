package com.put.battleship.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ContextManager {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ConcurrentHashMap<Channel, ChannelHandlerContext> channelContextMap = new ConcurrentHashMap<>();


    public static void addContext(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.add(channel);
        channelContextMap.put(channel, ctx);
    }

    public static void removeContext(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.remove(channel);
        channelContextMap.remove(channel);
    }

    public static ChannelHandlerContext getContext(Channel channel) {
        return channelContextMap.get(channel);
    }

    public static ChannelHandlerContext getContextFromPlayer(Player player) {
        String playerId = player.getId().toString();

        for (Channel channel : channels)
            if (channel.attr(Player.PLAYER_UUID_KEY).get().equals(playerId))
                return channelContextMap.get(channel);

        return null;
    }

    public static Player getPlayerFromContext(ChannelHandlerContext ctx) {
        var ch = ctx.channel();
        var attr = ch.attr(Player.PLAYER_UUID_KEY);
        // TODO: fail check if player is not found
        return PlayerManager.getPlayer(UUID.fromString(ctx.channel().attr(Player.PLAYER_UUID_KEY).get()));
    }
}
